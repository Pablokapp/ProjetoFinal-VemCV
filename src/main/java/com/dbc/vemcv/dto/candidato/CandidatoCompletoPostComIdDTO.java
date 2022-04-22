package com.dbc.vemcv.dto.candidato;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
public class CandidatoCompletoPostComIdDTO{
    private Integer idCandidato;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String telefone;
    private Integer numero; // numero da casa
    private String instituicao;
    private String descricao;
    private LocalDate dataInicioCurso; // data de inicio do curso
    private LocalDate dataFimCurso;
    private String nomeEmpresa;
    private String cargo; // experiencia
    private LocalDate dataInicioExperiencia; // data de inicio do curso
    private LocalDate dataFimExperiencia; //
    private String senioridade;
}
