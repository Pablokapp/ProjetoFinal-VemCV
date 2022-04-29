package com.dbc.vemcv.controller.usuario;

import com.dbc.vemcv.dto.usuario.UsuarioCreateDTO;
import com.dbc.vemcv.dto.usuario.UsuarioDTO;
import com.dbc.vemcv.enums.Cargo;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api
@Validated
public interface UsuarioAPI{

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cadastrador criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um usuário cadastrador")
    UsuarioDTO create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO, @RequestParam Cargo cargo) throws RegraDeNegocioException;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recupera cadastrador"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Recupera o usuário")
    UsuarioDTO retrieveUser();
}
