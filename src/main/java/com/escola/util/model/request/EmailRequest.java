package com.escola.util.model.request;

import com.escola.util.model.entity.TipoEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

// Adicione os campos necessários para enviar um e-mail
public record EmailRequest(
        @NotBlank @Email String to,
        @NotBlank String subject,
        @NotBlank TipoEmail tipo,
        List<KeyValueRequest> variaveis
) {
}