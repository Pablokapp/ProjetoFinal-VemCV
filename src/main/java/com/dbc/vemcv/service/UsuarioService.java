package com.dbc.vemcv.service;


import com.dbc.vemcv.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    public Optional<UsuarioEntity> findByUsername(String username) {
        return null;//todo implementar metodo
    }
}