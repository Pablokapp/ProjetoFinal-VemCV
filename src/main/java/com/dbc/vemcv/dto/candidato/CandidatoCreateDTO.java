package com.dbc.vemcv.dto.candidato;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoCreateDTO {
    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Nome completo do candidato")
    private String nome;

//    @NotBlank
//    @NotNull
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

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Logradouro do endereço do candidato. (Ex. Rua Assis Brasil) ")
    private String logradouro;

    @NotNull
    @Positive
    @ApiModelProperty("Número do endereço do candidato")
    private Integer numero;

//    @ApiModelProperty("Complemento do endereço do candidato, pode ser nulo")
//    private String complemento;

    @NotEmpty
    @ApiModelProperty("Bairro do endereço do candidato")
    private String bairro;

    @NotEmpty
    @ApiModelProperty("Cidade do endereço do candidato")
    private String cidade;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Telefone de contato do candidato")
    private String telefone;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Cargo do candidato")
    private String cargo;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;
}
