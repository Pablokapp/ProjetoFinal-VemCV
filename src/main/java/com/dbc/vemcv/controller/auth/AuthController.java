package com.dbc.vemcv.controller.auth;


import com.dbc.vemcv.dto.LoginDTO;
import com.dbc.vemcv.security.TokenService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Api(value = "0 - Login API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"0 - Login API"})
public class AuthController implements AuthAPI{
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usuario =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsuario(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(usuario);
        String token = tokenService.getToken(authenticate);

        return ResponseEntity.ok(token);
    }

}
