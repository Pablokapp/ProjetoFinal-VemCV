package com.dbc.vemcv.dto.candidato;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDadosExperienciasDTO {
    @ApiModelProperty("Dados principais do candidato")
    private CandidatoDTO candidato;

    @ApiModelProperty("Dados escolares do candidato")
    private List<DadosEscolaresDTO> dadosEscolares;

    @ApiModelProperty("Experiências do candidato")
    private List<ExperienciasDTO> experiencias;
}
