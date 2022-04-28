package com.dbc.vemcv.controller.experiencia;

import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
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
public interface ExperienciaAPI {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista experiencias dos candidatos ou de um candidato por id"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista experiencias dos candidatos ou de um candidato por id")
    ResponseEntity<List<ExperienciasDTO>> listar(@RequestParam(value = "idCandidato", required = false) Integer idCandidato);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Adiciona uma experiencia para o candidato"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Adiciona uma experiencia para o candidato por id")
    ResponseEntity<ExperienciasDTO> adicionar(@RequestParam(value = "idCandidato") Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualiza uma experiencia para o candidato"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("atualiza uma experiencia para o candidato por id")
    ResponseEntity<ExperienciasDTO> atualizar(@RequestParam(value = "idCandidato") Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Remove uma experiencia para o candidato"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Remove uma experiencia para o candidato por id")
    ResponseEntity<String> remover(@RequestParam(value = "idExperiencia") Integer idExperiencia) throws RegraDeNegocioException;

}
