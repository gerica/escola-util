package com.escola.util.controller;

import com.escola.util.controller.help.PageableHelp;
import com.escola.util.controller.help.SortInput;
import com.escola.util.model.entity.Municipio;
import com.escola.util.model.mapper.MunicipioMapper;
import com.escola.util.model.response.MunicipioResponse;
import com.escola.util.security.BaseException;
import com.escola.util.service.MunicipioService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @PreAuthorize("isAuthenticated()"): O usuário precisa estar logado
 * @PreAuthorize("hasRole('ADMIN')"): O usuário precisa ter a role "ADMIN".
 * (O Spring adiciona o prefixo ROLE_ automaticamente).
 * @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')"): Precisa ter uma das roles.
 * @PreAuthorize("hasAuthority('contrato:read')"): O usuário precisa ter a permissão específica
 * "contrato:read". Esta é uma prática ainda melhor do que usar roles,
 * pois desacopla a lógica de negócio das funções dos usuários.
 */

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilController {

    MunicipioService municipioService;
    MunicipioMapper municipioMapper;
    PageableHelp pageableHelp;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Optional<List<MunicipioResponse>> getMunicipios(Authentication authentication) throws BaseException, IOException {
        return municipioService.findAll().map(municipioMapper::toOutputList);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Page<MunicipioResponse> fetchByFiltro(
            @Argument String filtro,
            @Argument Optional<Integer> page, // Optional to handle default values from schema
            @Argument Optional<Integer> size, // Optional to handle default values from schema
            @Argument Optional<List<SortInput>> sort // Optional for sorting
    ) {

        Page<Municipio> entities = municipioService.findByFiltro(filtro, pageableHelp.getPageable(page, size, sort)).orElse(Page.empty());
        return entities.map(municipioMapper::toOutput);
    }

}
