package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.JwsHeader;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CandidatoServiceTest {


    @Mock
    private CandidatoRepository candidatoRepository;


    @InjectMocks
    private CandidatoService candidatoService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void BeforeEach() {
        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
        ReflectionTestUtils.setField(candidatoService,"objectMapper",objectMapper);
    }

    @Test
    void list() {
    }

    @Test
    void create() throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = CandidatoCreateDTO.builder()
                .nome("nome")
                .cpf("02064759687")
                .dataNascimento(LocalDate.of(2000,1,1))
                .logradouro("logradouro")
                .numero(2)
                .bairro("bairro")
                .cidade("cidade")
                .telefone("telefone")
                .cargo("cargo")
                .senioridade("master")
                .build();

        CandidatoDTO candidatoDTO = CandidatoDTO.builder().build();
        BeanUtils.copyProperties(candidatoCreateDTO,candidatoDTO);


        CandidatoEntity candidatoEntity = new CandidatoEntity();
        BeanUtils.copyProperties(candidatoDTO,candidatoEntity);

//        when(candidatoService.create(any(CandidatoCreateDTO.class))).thenReturn(candidatoDTO);

//        when(objectMapper.convertValue(any(CandidatoCreateDTO.class), CandidatoEntity.class)).thenReturn(candidatoEntity);
//        when(objectMapper.convertValue(any(CandidatoEntity.class), CandidatoDTO.class)).thenReturn(candidatoDTO);


        when(candidatoRepository.save(any(CandidatoEntity.class))).thenReturn(candidatoEntity);


        candidatoService.create(candidatoCreateDTO);

        verify(candidatoRepository).save(any(CandidatoEntity.class));
        verify(objectMapper).convertValue(any(CandidatoCreateDTO.class), CandidatoEntity.class);
        verify(candidatoService).create(any(CandidatoCreateDTO.class));
        verify(candidatoService).create(candidatoCreateDTO);
        verify(candidatoRepository, times(1)).save(candidatoEntity);




    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void listPaginado() {
    }

    @Test
    void findById() {
    }
}