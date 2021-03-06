package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity(name = "CANDIDATO")
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CANDIDATO_SEQUENCIA")
    @SequenceGenerator(name = "CANDIDATO_SEQUENCIA", sequenceName = "seq_candidato", allocationSize = 1)
    @Column(name = "id_candidato")
    private Integer idCandidato;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "senioridade")
    private String senioridade;

    @JsonIgnore
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.REMOVE)
    private Set<DadosEscolaresEntity> dadosEscolares;

    @JsonIgnore
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.REMOVE)
    private Set<ExperienciasEntity> experiencias;

    @JsonIgnore
    @ManyToMany(mappedBy = "candidatos")
    private Set<VagaEntity> vagas;

    @JsonIgnore
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.REMOVE)
    private CurriculoEntity curriculoEntity;


}