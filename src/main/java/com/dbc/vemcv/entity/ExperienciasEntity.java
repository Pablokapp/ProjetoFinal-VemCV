package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EXPERIENCIAS")
public class ExperienciasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPERIENCIAS_SEQUENCIA")
    @SequenceGenerator(name = "EXPERIENCIAS_SEQUENCIA", sequenceName = "seq_experiencias", allocationSize = 1)
    @Column(name = "id_experiencia")
    private Integer idExperiencia;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_candidato_id_candidato", referencedColumnName = "id_candidato")
    private CandidatoEntity candidato;
}
