package com.escola.util.model.request;

import jakarta.validation.constraints.NotBlank;

public record KeyValueRequest(
        @NotBlank String key,
        @NotBlank String value
) {
}
