package com.escola.util.model.request;

import jakarta.validation.constraints.NotBlank;

public record FileRequest(
        @NotBlank(message = "O conteúdo do arquivo em Base64 é obrigatório.")
        String contentFileBase64
) {
}
