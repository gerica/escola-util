package com.escola.util.config;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendConfig {

    @Value("${server.api.send.email}")
    private String resendApiKey;

    @Bean
    public Resend resend() {
        if (resendApiKey == null || resendApiKey.isBlank()) {
            throw new IllegalStateException("A chave da API do Resend (server.api.send.email) não está configurada.");
        }
        return new Resend(resendApiKey);
    }
}