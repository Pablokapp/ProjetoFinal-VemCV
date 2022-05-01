package com.dbc.vemcv.controller.vaga;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Api
@Validated
public interface VagaAPI {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vagas listadas com paginação"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista vagas em aberto paginadas")
    ResponseEntity<PaginaVagasCompleoReduzidaDTO> buscarVagasEmAberto(@RequestParam("pagina") Integer pagina,
                                                                      @RequestParam("quantidade-por-pagina") Integer quantidadePorPagina)
            throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato vinculado à vaga com sucesso"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Vincula um candidato à uma vaga")
    void vincularCandidato(Integer idVaga, Integer idCandidato) throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vagas locais atualizadas com sucesso"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Força a atualização das vagas locais com as vagas da API compleo")
    void forcarAtualizacao() throws RegraDeNegocioException;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a data da última atualização das vagas no servidor local"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Data da última atualização das vagas no servidor local")
    ResponseEntity<LocalDateTime> getDataUltimaAtualizacao() throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acesso livre ao banco de dados das cagas"),
            @ApiResponse(code = 400, message = "Servidor ocupado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Retorna o status do acesso ao banco de vagas")
    void getServerStatus() throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
            @ApiResponse(code = 400, message = "Sem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Retorna a lista de IDs dos candidatos vinculados à vaga")
    ResponseEntity<List<Integer>> getListaCandidatos(@RequestParam("id-vaga") Integer idVaga) throws RegraDeNegocioException;
}
