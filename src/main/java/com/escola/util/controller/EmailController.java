package com.escola.util.controller;

import com.escola.util.model.request.EmailRequest;
import com.escola.util.service.EmailService;
import graphql.GraphQLException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

    EmailService emailService;

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<String> sendEmail(@Argument EmailRequest request) {
        return emailService.sendEmail(request)
                // O método do serviço retorna Mono<Void>.
                // O operador .then() aguarda a conclusão e o substitui por um novo Mono.
                .then(Mono.just("E-mail enviado com sucesso."))
                // Se o serviço retornar um erro (ex: usuário não encontrado), ele será propagado.
                .onErrorResume(e -> Mono.error(new GraphQLException("Falha ao tentar enviar e-mail: " + e.getMessage())));
    }
}
