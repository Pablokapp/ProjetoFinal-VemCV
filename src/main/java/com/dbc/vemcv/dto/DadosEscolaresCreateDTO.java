package com.dbc.vemcv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosEscolaresCreateDTO {
    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Nome da Instituição/Escola/Universidade")
    private String instituicao;

    @NotNull
    @Past
    @ApiModelProperty("Data de início, deve ser uma data passada")
    private Date dataInicio;

    @Past
    @ApiModelProperty("Data de encerramento, deve ser uma data passada, pode ser nulo")
    private Date dataFim;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Descrição de Curso/Semestre/Conclusão/Habilidades")
    private String descricao;
}
