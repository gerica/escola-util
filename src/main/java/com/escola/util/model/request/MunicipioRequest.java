package com.escola.util.model.request;

import java.io.Serializable;

public record MunicipioRequest(String codigo,
                               String descricao,
                               String uf) implements Serializable {
}

