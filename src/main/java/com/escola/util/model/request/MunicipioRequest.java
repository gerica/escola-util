package com.escola.util.model.request;

import java.io.Serializable;

public record MunicipioRequest(String codigo,
                               String descricao,
                               EstadoRequest estado) implements Serializable {
}

