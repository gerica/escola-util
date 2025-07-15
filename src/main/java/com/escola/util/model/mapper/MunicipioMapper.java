package com.escola.util.model.mapper;

import com.escola.util.model.entity.Municipio;
import com.escola.util.model.request.MunicipioRequest;
import com.escola.util.model.response.MunicipioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MunicipioMapper {

    MunicipioResponse toOutput(Municipio entity);

    List<MunicipioResponse> toOutputList(List<Municipio> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uf", source = "estado.sigla")
    @Mapping(target = "estado", source = "estado.descricao")
    Municipio toEntity(MunicipioRequest request);

}
