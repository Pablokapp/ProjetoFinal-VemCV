package com.dbc.vemcv.dto.candidato;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidatoReduzidoDTO {
    private Integer idCandidato;
    private String nome;
    private String cargo;
    private LocalDate dataNascimento;
    private String senioridade;
}
