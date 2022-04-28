package com.dbc.vemcv.dto.candidato;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginaCandidatoDTO {
    @ApiModelProperty("Candidatos")
    private List<CandidatoDTO> candidatos;
    @ApiModelProperty("Total de elementos")
    private Long totalDeElementos;
    @ApiModelProperty("Total de páginas")
    private Integer totalDePaginas;
}