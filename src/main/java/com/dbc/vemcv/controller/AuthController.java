package com.dbc.vemcv.controller;


import com.dbc.vemcv.dto.LoginDTO;
import com.dbc.vemcv.entity.UsuarioEntity;
import com.dbc.vemcv.security.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário autenticado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Autentica o usuário cadastrador no sistema")
    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usuario =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsuario(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(usuario);

        return tokenService.getToken(authenticate);
    }

}
