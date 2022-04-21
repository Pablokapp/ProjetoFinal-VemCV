package com.dbc.vemcv.controller.cadidato;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDadosExperienciasDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@Validated
public interface CandidatoAPI {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "lista de candidatos"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os candidatos cadastrados ou apenas um, por id")
    ResponseEntity<List<CandidatoDTO>> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "lista de candidatos com dados e experiencias"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista todos os candidatos cadastrados com os dados completos ou apenas um, por id")
    ResponseEntity<List<CandidatoDadosExperienciasDTO>> listCandidatosDadosExperiencias(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException;



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um candidato")
    ResponseEntity<CandidatoDTO> create(@RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException;



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato atualizado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza um candidato")
    ResponseEntity<CandidatoDTO> update(@RequestParam Integer idCandidato, @RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato deletado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Deleta um candidato")
    ResponseEntity<String> delete(@RequestParam Integer idCandidato) throws RegraDeNegocioException;







}
