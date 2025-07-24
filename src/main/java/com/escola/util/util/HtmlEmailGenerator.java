package com.escola.util.util;


import com.escola.util.model.request.EmailRequest;
import com.escola.util.model.request.KeyValueRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HtmlEmailGenerator {

    SpringTemplateEngine emailTemplateEngine; // Inject the specific email template engine

    public String getBodyPrimeiroAcesso(EmailRequest emailRequest) {
        Map<String, Object> variaveisDoTemplate = converterParaMap(emailRequest.variaveis());

        Context context = new Context(new Locale("pt", "BR"));

        context.setVariable("nomeUsuario", variaveisDoTemplate.get("nomeUsuario"));
        context.setVariable("senhaTemporaria", variaveisDoTemplate.get("senhaTemporaria")); // Already formatted
        context.setVariable("urlLogin", variaveisDoTemplate.get("urlLogin")); // Already formatted
        context.setVariable("nomeSistema", variaveisDoTemplate.get("nomeSistema")); // Already formatted

        return emailTemplateEngine.process("onboarding", context);
    }

    public String getBodyDefault() {
        Context context = new Context(new Locale("pt", "BR"));

        context.setVariable("nomeSistema", "Escola");
        context.setVariable("info", "informações de teste"); // Already formatted

        return emailTemplateEngine.process("default", context);
    }

    public String getBody(EmailRequest emailRequest) {
        switch (emailRequest.tipo()) {
            case ONBOARDING -> {
                return getBodyPrimeiroAcesso(emailRequest);
            }
            default -> {
                return getBodyDefault();
            }
        }
    }

    private Map<String, Object> converterParaMap(List<KeyValueRequest> lista) {
        if (lista == null || lista.isEmpty()) {
            return Map.of(); // Retorna um mapa vazio
        }
        return lista.stream()
                .collect(Collectors.toMap(KeyValueRequest::key, KeyValueRequest::value));
    }
}