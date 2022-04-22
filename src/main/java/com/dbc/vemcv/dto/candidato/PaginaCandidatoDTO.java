package com.dbc.vemcv.dto.candidato;

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
    private List<CandidatoDTO> candidatos;
    private Long totalDeElementos;
    private Integer totalDePaginas;
}