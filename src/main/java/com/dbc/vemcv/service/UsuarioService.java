package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.usuario.UsuarioCreateDTO;
import com.dbc.vemcv.dto.usuario.UsuarioDTO;
import com.dbc.vemcv.entity.UsuarioEntity;
import com.dbc.vemcv.enums.Cargo;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CargoRepository;
import com.dbc.vemcv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;


    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }



    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO, Cargo cargo) throws RegraDeNegocioException {
        if (usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())) {
            throw new RegraDeNegocioException("Email já cadastrado");
        }
        UsuarioEntity entity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));


        entity.setCargo(cargoRepository.findById(cargo.getCargo()).orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado")));


        UsuarioEntity save = usuarioRepository.save(entity);
        return new UsuarioDTO(save.getIdUsuario(), save.getNome(), save.getEmail());
    }

    public UsuarioDTO retrieveUser() {
        int idUsuario = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }
}