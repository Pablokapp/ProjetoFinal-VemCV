package com.dbc.vemcv.dto.candidatocompleto;

import com.dbc.vemcv.dto.candidato.CandidatoDTO;
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
    private List<CandidatoCompletoDTO> candidatosCompletos;
    private Long totalDeElementos;
    private Integer totalDePaginas;
}
