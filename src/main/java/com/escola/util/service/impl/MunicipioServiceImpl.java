package com.escola.util.service.impl;

import com.escola.util.model.entity.Municipio;
import com.escola.util.model.mapper.MunicipioMapper;
import com.escola.util.model.request.MunicipioRequest;
import com.escola.util.repository.MunicipioRepository;
import com.escola.util.service.MunicipioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MunicipioServiceImpl implements MunicipioService {

    final MunicipioRepository repository;
    final MunicipioMapper mapper;
    @Value("classpath:cidades.json")
    Resource cidadeResource;

    public MunicipioServiceImpl(MunicipioRepository repository, MunicipioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void salvar(MunicipioRequest request) {
        Optional<Municipio> optional = findByCodigo(request.codigo());
        if (optional.isEmpty()) {
            repository.save(mapper.toEntity(request));
        }
    }

    @Override
    public Optional<List<Municipio>> findAll() {
        return Optional.of((List<Municipio>) repository.findAll());
    }

    @Override
    public Optional<List<MunicipioRequest>> findAllJason() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//       // Read the JSON file from the resources directory and map it to a list of Cidade objects
        return Optional.of(objectMapper.readValue(cidadeResource.getInputStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, MunicipioRequest.class)));
    }

    @Override
    public Optional<Municipio> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }

    @Override
    public Optional<Page<Municipio>> findByFiltro(String filtro, Pageable pageable) {
        return repository.findByFiltro(filtro, pageable);
    }

}
