package com.dbc.vemcv.dto.candidatocompleto;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CandidatoCompletoCreateDTO {
    @NotEmpty
    @ApiModelProperty("Nome completo do candidato")
    private String nome;

    @NotEmpty
//    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 números")
//    @Pattern(regexp = "^[0-9]+$", message = "O CPF deve conter apenas números")
    @CPF
    @ApiModelProperty("CPF do candidato, deve conter 11 números")
    private String cpf;

    @NotNull
    @Past
    @ApiModelProperty("Data de nascimento do candidato, deve ser uma data passada")
    private LocalDate dataNascimento;

    @NotEmpty
    @ApiModelProperty("Logradouro do endereço do candidato. (Ex. Rua Assis Brasil) ")
    private String logradouro;

    @NotNull
    @Positive
    @ApiModelProperty("Número do endereço do candidato")
    private Integer numero;

    @NotEmpty
    @ApiModelProperty("Bairro do endereço do candidato")
    private String bairro;

    @NotEmpty
    @ApiModelProperty("Cidade do endereço do candidato")
    private String cidade;

    @NotEmpty
    @ApiModelProperty("Telefone de contato do candidato")
    private String telefone;

    @NotEmpty
    @ApiModelProperty("Cargo do candidato")
    private String cargo;

    @NotEmpty
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;

    @Valid
    @ApiModelProperty("Dados Escolares do candidato")
    private List<DadosEscolaresCreateDTO> dadosEscolares;

    @Valid
    @ApiModelProperty("Experiências do candidato")
    private List<ExperienciasCreateDTO> experiencias;

}