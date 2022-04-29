package com.dbc.vemcv.dto.vagas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class VagaCompleoReduzidaDTO {
    @ApiModelProperty("ID da vaga")
    private Integer id;
    @ApiModelProperty("Título da vaga")
    private String titulo;
    @ApiModelProperty("Status da vaga")
    private String status;
    @ApiModelProperty("Responsável pela vaga")
    private String responsavel;
    @ApiModelProperty("Estado da vaga")
    private String estado;
    @ApiModelProperty("Data de abertura da vaga")
    private LocalDate dataAbertura;
    @ApiModelProperty("Cliente responsável pela vaga")
    private String cliente;
    @ApiModelProperty("Cidade da vaga")
    private String cidade;
    @ApiModelProperty("Analista responsável pela vaga")
    private String analista;
    @ApiModelProperty("Vaga para PCD?")
    private Boolean pcd;
    @ApiModelProperty("Última atualização recebida no sistema para a vaga")
    private LocalDateTime ultimaAtualizacao;
}
