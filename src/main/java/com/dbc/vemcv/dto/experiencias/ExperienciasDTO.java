package com.dbc.vemcv.dto.experiencias;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienciasDTO {
    @ApiModelProperty("Identificador da experiência")
    private Integer idExperiencia;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Nome da empresa")
    private String nomeEmpresa;

    @NotNull
    @Past
    @ApiModelProperty("Data de início, deve ser uma data passada")
    private LocalDate dataInicio;


    @Past
    @ApiModelProperty("Data de encerramento, deve ser uma data passada, pode ser nulo")
    private LocalDate dataFim;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Descrição de Tarefas/Cargo/Habilidades")
    private String descricao;
}
