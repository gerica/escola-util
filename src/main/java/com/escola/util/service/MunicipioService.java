package com.escola.util.service;


import com.escola.util.model.entity.Municipio;
import com.escola.util.model.request.MunicipioRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MunicipioService {

    void salvar(MunicipioRequest request);

    Optional<List<Municipio>> findAll();

    Optional<List<MunicipioRequest>> findAllJason() throws IOException;

    Optional<Municipio> findByCodigo(String codigo);

    Optional<Page<Municipio>> findByFiltro(String filtro, Pageable pageable);

}
