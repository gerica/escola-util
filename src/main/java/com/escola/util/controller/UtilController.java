package com.escola.util.controller;

import com.escola.util.model.response.MunicipioResponse;
import com.escola.util.security.BaseException;
import com.escola.util.service.MunicipioService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

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

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<MunicipioResponse> getMunicipios(Authentication authentication) throws BaseException, IOException {
        return municipioService.findAll();
    }

//    @QueryMapping
//    @PreAuthorize("isAuthenticated()")
//    public String getOutroContrato(Authentication authentication) { // 1. Injete o objeto Authentication
//        // 2. O 'principal' é o seu objeto User, pois ele implementa UserDetails.
//        //    Fazemos um cast para poder acessar seus métodos (getFirstName, etc.).
//        User user = (User) authentication.getPrincipal();
//
//        // 3. Agora você pode usar o objeto 'user' como quiser!
//        String username = user.getUsername();
//        String nomeCompleto = user.getFirstname() + " " + user.getLastname();
//
//        return "getOutroContrato executado por um usuário autenticado: " + nomeCompleto + " (" + username + ")";
//    }
}
