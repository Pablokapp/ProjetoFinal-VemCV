package com.dbc.vemcv.security;


import com.dbc.vemcv.entity.UsuarioEntity;
import com.dbc.vemcv.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UsuarioEntity> optionalUsuario = usuarioService.findByUsername(login);//busca usuario
        if(optionalUsuario.isPresent()){//se existe
            return optionalUsuario.get();//retorna o usuario encontrado
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
