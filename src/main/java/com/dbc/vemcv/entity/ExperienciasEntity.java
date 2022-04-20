package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
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
    private Date dataInicio;

    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_candidato_id_candidato", referencedColumnName = "id_candidato")
    private CandidatoEntity candidato;
}
