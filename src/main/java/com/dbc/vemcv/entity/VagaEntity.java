package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "VAGA")
public class VagaEntity {
    @Id
    @Column(name = "id_vaga")
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "status")
    private String status;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "estado")
    private String estado;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "analista")
    private String analista;

    @Column(name = "pcd")
    private Boolean pcd;

    @Column(name = "ultima_atualizacao_local")//guarda a atualizacao local para controle
    private LocalDateTime ultimaAtualizacao;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "candidato_vaga",
            joinColumns = @JoinColumn(name = "fk_vaga"),
            inverseJoinColumns = @JoinColumn(name = "fk_candidato")
    )
    private Set<CandidatoEntity> candidatos;
}
