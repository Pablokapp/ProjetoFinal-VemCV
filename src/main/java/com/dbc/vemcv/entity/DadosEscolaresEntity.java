package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "DADOS_ESCOLARES")
public class DadosEscolaresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DADOS_ESCOLARES_SEQUENCIA")
    @SequenceGenerator(name = "DADOS_ESCOLARES_SEQUENCIA", sequenceName = "seq_dados_escolares", allocationSize = 1)
    @Column(name = "id_dados_escolares")
    private Integer idDadosEscolares;

    @Column(name = "instituicao")
    private String instituicao;

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
