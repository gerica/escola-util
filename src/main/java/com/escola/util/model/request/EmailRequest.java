package com.escola.util.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Adicione os campos necessários para enviar um e-mail
public record EmailRequest(
        @NotBlank @Email String to,
        @NotBlank String subject,
        @NotBlank String body
) {
}