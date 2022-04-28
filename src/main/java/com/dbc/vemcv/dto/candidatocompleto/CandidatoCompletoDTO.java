package com.dbc.vemcv.dto.candidatocompleto;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CandidatoCompletoDTO{
    private Integer idCandidato;
    @ApiModelProperty("Nome completo do candidato")
    private String nome;
    @ApiModelProperty("CPF do candidato, deve conter 11 números")
    private String cpf;
    @ApiModelProperty("Data de nascimento do candidato, deve ser uma data passada")
    private LocalDate dataNascimento;

    @ApiModelProperty("Logradouro do endereço do candidato. (Ex. Rua Assis Brasil) ")
    private String logradouro;
    @ApiModelProperty("Número do endereço do candidato")
    private Integer numero;
    @ApiModelProperty("Bairro do endereço do candidato")
    private String bairro;
    @ApiModelProperty("Cidade do endereço do candidato")
    private String cidade;

    @ApiModelProperty("Telefone de contato do candidato")
    private String telefone;
    @ApiModelProperty("Cargo do candidato")
    private String cargo;
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;

    @ApiModelProperty("Dados Escolares do candidato")
    private List<DadosEscolaresDTO> dadosEscolares;

    @ApiModelProperty("Experiências do candidato")
    private List<ExperienciasDTO> experiencias;
}
