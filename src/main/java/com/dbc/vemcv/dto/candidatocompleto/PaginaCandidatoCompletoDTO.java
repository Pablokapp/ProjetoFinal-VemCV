package com.dbc.vemcv.dto.candidatocompleto;

import com.dbc.vemcv.dto.candidato.CandidatoDTO;
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
public class PaginaCandidatoCompletoDTO {
    @ApiModelProperty("Lista de candidatos completos")
    private List<CandidatoCompletoDTO> candidatosCompletos;
    @ApiModelProperty("Total de elementos no sistema conforme a busca")
    private Long totalDeElementos;
    @ApiModelProperty("Total de paginas conforme a busca")
    private Integer totalDePaginas;
}
