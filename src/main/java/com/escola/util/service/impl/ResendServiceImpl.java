package com.escola.util.service.impl;

import com.escola.util.model.request.EmailRequest;
import com.escola.util.security.EmailSendingException;
import com.escola.util.service.EmailService;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service("resendService")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ResendServiceImpl implements EmailService {

    // Injetado via @Bean, em vez de criar uma nova instância a cada chamada
    final Resend resend;

    @Value("${server.email.from}")
    String fromEmail;

    @Override // Adicione a anotação @Override para garantir a conformidade com a interface
    public Mono<Void> sendEmail(EmailRequest request) {
        // 1. Construa os parâmetros a partir do request
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(fromEmail) // Use um e-mail configurado
                .to(request.to())
                .subject(request.subject())
                .html(request.body())
                .build();

        // 2. Encapsule a chamada bloqueante em um Mono
        return Mono.fromRunnable(() -> {
                    try {
                        // A chamada bloqueante acontece aqui
                        var data = resend.emails().send(params);
                        log.info("E-mail enviado com sucesso via Resend. ID: {}", data.getId());
                    } catch (ResendException e) {
                        // Lança uma exceção para ser capturada pelo pipeline do Mono
                        throw new EmailSendingException("Falha ao enviar e-mail via Resend", e);
                    }
                })
                // 3. Agende a execução em um thread pool apropriado para I/O
                .subscribeOn(Schedulers.boundedElastic())
                // 4. Como o Mono.fromRunnable não emite valor, o .then() o transforma em Mono<Void>
                .then();
    }
}
