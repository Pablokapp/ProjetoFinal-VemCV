package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.usuario.UsuarioCreateDTO;
import com.dbc.vemcv.entity.CargoEntity;
import com.dbc.vemcv.entity.UsuarioEntity;
import com.dbc.vemcv.enums.Cargo;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CargoRepository;
import com.dbc.vemcv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(usuarioService,"objectMapper",objectMapper);
    }

    @Test
    public void findByEmail() {
        CargoEntity cargo = new CargoEntity();
        UsuarioEntity usuario = new UsuarioEntity(1,"","","",cargo);

        when(usuarioRepository.findByEmail(any(String.class))).thenReturn(Optional.of(usuario));

        assertEquals(usuario,usuarioService.findByEmail("").get());
    }

    @Test
    public void createOk() {
        CargoEntity cargo = new CargoEntity();
        UsuarioEntity usuario = new UsuarioEntity(1,"","","",cargo);
        UsuarioCreateDTO usuarioDTO = UsuarioCreateDTO.builder()
                .nome("")
                .email("")
                .senha("")
                .build();

        when(usuarioRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(cargoRepository.findById(any(Integer.class))).thenReturn(Optional.of(cargo));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        try {
            assertEquals("",usuarioService.create(usuarioDTO, Cargo.CADASTRADOR).getEmail());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCargoInexistente(){
        UsuarioCreateDTO usuarioDTO = UsuarioCreateDTO.builder()
                .nome("")
                .email("")
                .senha("")
                .build();


        when(usuarioRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(cargoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.usuarioService.create(usuarioDTO, Cargo.CADASTRADOR));

        assertTrue(exception.getMessage().contains("Cargo não encontrado"));
    }

    @Test
    public void createEmailJaCadastrado() {

        when(usuarioRepository.existsByEmail(any(String.class))).thenReturn(true);

        Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.usuarioService.create(UsuarioCreateDTO.builder().email("").build(), null));

        assertTrue(exception.getMessage().contains("Email já cadastrado"));
    }

}