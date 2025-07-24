package com.escola.util.repository;

import com.escola.util.model.entity.Municipio;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MunicipioRepository extends CrudRepository<Municipio, Integer> {

    Optional<Municipio> findByCodigo(String codigo);

    @Query("SELECT e FROM Municipio e " +
            " WHERE (:criteria IS NULL OR :criteria = '') OR " +
            " (LOWER(e.descricao) LIKE LOWER(CONCAT('%', :criteria, '%')) ) ")
    @QueryHints(value = {
            @QueryHint(name = "javax.persistence.query.timeout", value = "5000"),
            @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "USE"), //USE, BYPASS, REFRESH
            @QueryHint(name = "jakarta.persistence.cache.storeMode", value = "USE")
    })
    Optional<Page<Municipio>> findByFiltro(@Param("criteria") String filtro, Pageable pageable);
}