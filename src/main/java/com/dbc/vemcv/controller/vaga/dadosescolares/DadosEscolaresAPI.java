package com.dbc.vemcv.controller.vaga.dadosescolares;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@Validated
public interface DadosEscolaresAPI {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "lista de dados escolares"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os dados escolares cadastrados ou apenas um, por id")
    ResponseEntity<List<DadosEscolaresDTO>> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados escolares criados"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria dados escolares para um id")
    ResponseEntity<DadosEscolaresDTO> create(@RequestParam("id") Integer idCandidato, @RequestBody DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados escolares atualizados"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza dados escolares para um id")
    ResponseEntity<DadosEscolaresDTO> update(@RequestParam("id") Integer idCandidato, @RequestBody DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados escolares deletados"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Deleta os dados escolares de um candidato por id")
    ResponseEntity<String> delete(@RequestParam("id") Integer idDadosEscolares) throws RegraDeNegocioException;


}
