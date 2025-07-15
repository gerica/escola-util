package com.escola.util.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_municipio")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String codigo;
    String descricao;
    String uf;
    String estado;
}
