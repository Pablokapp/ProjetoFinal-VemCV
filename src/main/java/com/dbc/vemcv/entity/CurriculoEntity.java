package com.dbc.vemcv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CURRICULO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurriculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRICULO_SEQUENCIA")
    @SequenceGenerator(name = "CURRICULO_SEQUENCIA", sequenceName = "seq_curriculo", allocationSize = 1)
    @Column(name = "id_curriculo")
    private Integer idCurriculo;

    @Column(name = "nome")
    private String fileName;

    @Column(name = "content_type")
    private String fileType;

    @Column(name = "size")
    private long size;

    @Lob
    private byte[] data;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_candidato", referencedColumnName = "id_candidato")
    private CandidatoEntity candidato;

}
