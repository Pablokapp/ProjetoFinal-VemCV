package com.dbc.vemcv.dto.candidato;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class CandidatoCompletoPostDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 números")
    @Pattern(regexp = "^[0-9]+$", message = "O CPF deve conter apenas números")
    @ApiModelProperty("CPF do candidato, deve conter 11 números")
    private String cpf;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Nome completo do candidato")
    private String nome;


    @NotNull
    @Past
    @ApiModelProperty("Data de nascimento do candidato, deve ser uma data passada")
    private LocalDate dataNascimento;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Logradouro do endereço do candidato. (Ex. Rua Assis Brasil) ")
    private String logradouro; // rua

    @NotEmpty
    @ApiModelProperty("Cidade do endereço do candidato")
    private String cidade;


    @NotEmpty
    @ApiModelProperty("Bairro do endereço do candidato")
    private String bairro;


    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Telefone de contato do candidato")
    private String telefone;


    @NotNull
    @Positive
    @ApiModelProperty("Número do endereço do candidato")
    private Integer numero; // numero da casa


    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Nome da Instituição/Escola/Universidade")
    private String instituicao;

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Descrição de Curso/Semestre/Conclusão/Habilidades")
    private String descricaoDoCurso;

    @NotNull
    @Past
    @ApiModelProperty("Data de início, deve ser uma data passada")
    private LocalDate dataInicioCurso; // data de inicio do curso


    @Past
    @ApiModelProperty("Data de encerramento, deve ser uma data passada, pode ser nulo")
    private LocalDate dataFimCurso;

    @NotNull
    @NotEmpty
    @ApiModelProperty("Nome da empresa")
    private String nomeEmpresa;

    @NotNull
    @NotEmpty
    @ApiModelProperty("Descrição de Tarefas/Cargo/Habilidades")
    private String cargo; // experiencia


    @NotNull
    @Past
    @ApiModelProperty("Data de início, deve ser uma data passada")
    private LocalDate dataInicioExperiencia; // data de inicio do curso


    @Past
    @ApiModelProperty("Data de encerramento, deve ser uma data passada, pode ser nulo")
    private LocalDate dataFimExperiencia; //

    @NotBlank
    @NotNull
    @NotEmpty
    @ApiModelProperty("Descrição de Cargo da experiencia")
    private String descricaoDoCargo;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;



}




