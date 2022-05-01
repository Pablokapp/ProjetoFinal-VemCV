package com.dbc.vemcv.controller.usuario;

import com.dbc.vemcv.dto.usuario.UsuarioCreateDTO;
import com.dbc.vemcv.dto.usuario.UsuarioDTO;
import com.dbc.vemcv.enums.Cargo;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UsuarioController implements UsuarioAPI{


    private final UsuarioService usuarioService;

    @PostMapping("/create-cadastrador")
    public UsuarioDTO create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO, @RequestParam Cargo cargo) throws RegraDeNegocioException {
        return usuarioService.create(usuarioCreateDTO, cargo);
    }

    @GetMapping
    public UsuarioDTO retrieveUser(){
        return usuarioService.retrieveUser();
    }



}