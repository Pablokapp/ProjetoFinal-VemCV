package com.dbc.vemcv.dto.candidatocompleto;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CandidatoCompletoCreateDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;

    private String telefone;
    private String cargo;
    private String senioridade;

    private List<DadosEscolaresCreateDTO> dadosEscolares;

    private List<ExperienciasCreateDTO> experiencias;

}