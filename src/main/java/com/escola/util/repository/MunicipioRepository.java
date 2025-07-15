package com.escola.util.repository;

import com.escola.util.model.entity.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MunicipioRepository extends CrudRepository<Municipio, Integer> {

    Optional<Municipio> findByCodigo(String codigo);

    @Query("SELECT e FROM Municipio e " +
            " WHERE (:criteria IS NULL OR :criteria = '') OR " +
            " (LOWER(e.descricao) LIKE LOWER(CONCAT('%', :criteria, '%')) ) ")
    Optional<Page<Municipio>> findByFiltro(@Param("criteria") String filtro, Pageable pageable);
}