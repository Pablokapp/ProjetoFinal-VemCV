package com.dbc.vemcv.dto.vagas;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class VagaCompleoReduzidaDTO {
    private Integer id;
    private String titulo;
    private String status;
    private String responsavel;
    private String estado;
    private Date dataAbertura;
    private String cliente;
    private String cidade;
    private String analista;
}