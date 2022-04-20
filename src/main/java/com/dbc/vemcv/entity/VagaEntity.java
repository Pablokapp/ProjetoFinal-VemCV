package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "VAGA")
public class VagaEntity {
    @Id
    @Column(name = "id_vaga")
    private Integer id;

    @JsonProperty("Titulo")
    @Column(name = "titulo")
    private String titulo;

    @JsonProperty("Status")
    @Column(name = "status")
    private String status;

    @JsonProperty("Responsavel")
    @Column(name = "responsavel")
    private String responsavel;

    @JsonProperty("Estado")
    @Column(name = "estado")
    private String estado;

    @JsonProperty("DataAbertura")
    @Column(name = "data_abertura")
    private Date dataAbertura;

    @JsonProperty("Cliente")
    @Column(name = "cliente")
    private String cliente;

    @JsonProperty("Cidade")
    @Column(name = "cidade")
    private String cidade;

    @JsonProperty("Analista")
    @Column(name = "analista")
    private String analista;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "candidato_vaga",
            joinColumns = @JoinColumn(name = "fk_vaga"),
            inverseJoinColumns = @JoinColumn(name = "fk_candidato")
    )
    private Set<CandidatoEntity> candidatos;
}
