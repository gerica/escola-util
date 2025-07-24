package com.escola.util.service.impl;

import com.escola.util.model.request.EmailRequest;
import com.escola.util.security.EmailSendingException;
import com.escola.util.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service("emailService")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class LocalEmailServiceImpl implements EmailService {

    final JavaMailSender javaMailSender;

    @Value("${server.email.from}")
    String fromEmail;

    @Override
    public Mono<Void> sendEmail(EmailRequest request) {
        if (javaMailSender == null) {
            log.error("JavaMailSender is not initialized");
            throw new IllegalStateException("JavaMailSender is not initialized");
        }
        return Mono.fromRunnable(() -> {
                    MimeMessage msg = this.javaMailSender.createMimeMessage();
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

                        helper.setSubject(request.subject());
                        helper.setText(request.body(), true);
                        helper.setFrom(fromEmail);
                        helper.setTo(request.to());
                        this.javaMailSender.send(msg);

                    } catch (MessagingException e) {
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
