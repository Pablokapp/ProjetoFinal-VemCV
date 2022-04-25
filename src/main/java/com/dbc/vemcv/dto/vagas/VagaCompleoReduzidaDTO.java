package com.dbc.vemcv.dto.vagas;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VagaCompleoReduzidaDTO {
    private Integer id;
    private String titulo;
    private String status;
    private String responsavel;
    private String estado;
    private LocalDate dataAbertura;
    private String cliente;
    private String cidade;
    private String analista;
    private Boolean pcd;
}
