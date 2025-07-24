package com.escola.util.service.impl;

import com.escola.util.model.request.EmailRequest;
import com.escola.util.security.EmailSendingException;
import com.escola.util.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Service("sendGridService")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class SendGridServiceImpl implements EmailService {

    // Injetado via @Bean, em vez de criar uma nova instância a cada chamada
    final SendGrid sendGrid;

    @Value("${server.email.from}")
    String fromEmail;

    @Override // Adicione a anotação @Override para garantir a conformidade com a interface
    public Mono<Void> sendEmail(EmailRequest request) {
        // 1. Construa os parâmetros a partir do request
        Email from = new Email(fromEmail);
        Email to = new Email(request.to());
        Content content = new Content("text/html", "temp");
        Mail mail = new Mail(from, request.subject(), to, content);

        // 2. Encapsule a chamada bloqueante em um Mono
        return Mono.fromRunnable(() -> {
                    Request sendGridRequest = new Request();

                    try {
                        sendGridRequest.setMethod(Method.POST);
                        sendGridRequest.setEndpoint("mail/send");
                        sendGridRequest.setBody(mail.build());
                        Response response = sendGrid.api(sendGridRequest);
                        System.out.println(response.getStatusCode());
                        System.out.println(response.getBody());
                        System.out.println(response.getHeaders());

                        log.info("E-mail enviado com sucesso via sendGrid. response: {}", response.getStatusCode());
                    } catch (IOException e) {
                        // Lança uma exceção para ser capturada pelo pipeline do Mono
                        throw new EmailSendingException("Falha ao enviar e-mail via sendGrid", e);
                    }
                })
                // 3. Agende a execução em um thread pool apropriado para I/O
                .subscribeOn(Schedulers.boundedElastic())
                // 4. Como o Mono.fromRunnable não emite valor, o .then() o transforma em Mono<Void>
                .then();
    }
}
