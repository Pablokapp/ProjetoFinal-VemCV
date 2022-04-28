package com.dbc.vemcv.dto.candidato;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("Identificador do candidato")
    private Integer idCandidato;
    @ApiModelProperty("Nome completo do candidato")
    private String nome;
    @ApiModelProperty("Cargo do candidato")
    private String cargo;
    @ApiModelProperty("Data de nascimento do candidato, deve ser uma data passada")
    private LocalDate dataNascimento;
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;
}
