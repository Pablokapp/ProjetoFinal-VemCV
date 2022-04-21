package com.dbc.vemcv.controller.auth;

import com.dbc.vemcv.dto.usuario.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api
@Validated
public interface AuthAPI {

    @ApiOperation(value = "Logar no usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um token de autenticação"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO);


}
