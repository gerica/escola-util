package com.escola.util.model.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class MunicipioResponse {
    String codigo;
    String descricao;
    String uf;
    String estado;

}
