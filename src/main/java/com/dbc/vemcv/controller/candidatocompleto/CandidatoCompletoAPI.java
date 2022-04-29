package com.dbc.vemcv.controller.candidatocompleto;

import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoCreateDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.candidatocompleto.PaginaCandidatoCompletoDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api
@Validated
public interface CandidatoCompletoAPI {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cria um candidato completo"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um candidato completo")
    ResponseEntity<CandidatoCompletoDTO> create(@Valid @RequestBody CandidatoCompletoCreateDTO candidato) throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista os candidatos completos por pagina"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os candidatos completos por pagina")
    ResponseEntity<PaginaCandidatoCompletoDTO> listPaginado(@RequestParam(value = "id-candidato", required = false) Integer idCandidato,
                                                                   @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
                                                                   @RequestParam(value = "quantidade-por-pagina", required = false, defaultValue = "10") Integer quantidadePorPagina) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualiza um candidato completo"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza um candidato completo")
    ResponseEntity<CandidatoCompletoDTO> update(@RequestParam(value = "id-candidato") Integer idCandidato, @Valid @RequestBody CandidatoCompletoCreateDTO candidato) throws RegraDeNegocioException;



}
