package com.dbc.vemcv.controller.curriculo;

import com.dbc.vemcv.dto.curriculo.CurriculoDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface CurriculoAPI {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curriculo cadastrado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Faz upload do curriculo")
    ResponseEntity<CurriculoDTO> uploadCurriculo(@RequestPart("curriculo") MultipartFile curriculo, @PathVariable("idCandidato") Integer idCandidato) throws RegraDeNegocioException;



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curriculo recuperado"),
            @ApiResponse(code = 400, message = "candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Recupera o curriculo")
    public ResponseEntity<Resource> downloadCurriculo(@PathVariable Integer idCandidato);


}
