package com.dbc.vemcv.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurriculoDTO {
    @ApiModelProperty("Identificador do curriculo")
    private Integer idCurriculo;

    @ApiModelProperty("Nome do arquivo")
    private String fileName;

    @ApiModelProperty("URI do arquivo")
    private String fileDownloadUri;

    @ApiModelProperty("Tipo do arquivo")
    private String fileType;

    @ApiModelProperty("Tamanho do arquivo")
    private long size;
}
