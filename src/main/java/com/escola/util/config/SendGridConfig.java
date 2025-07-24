package com.escola.util.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Value("${sendgrid.api.key}")
    private String resendApiKey;

    @Bean
    public SendGrid sendGrid() {
        if (resendApiKey == null || resendApiKey.isBlank()) {
            throw new IllegalStateException("A chave da API do SendGrid (sendgrid._api._key) não está configurada.");
        }
        // sg.setDataResidency("eu");
        return new SendGrid(resendApiKey);
    }
}