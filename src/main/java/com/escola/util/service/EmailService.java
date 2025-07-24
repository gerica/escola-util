package com.escola.util.service;

import com.escola.util.model.request.EmailRequest;
import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<Void> sendEmail(EmailRequest request);
}
