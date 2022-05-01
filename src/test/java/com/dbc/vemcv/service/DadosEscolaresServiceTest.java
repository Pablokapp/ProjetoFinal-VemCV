package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.DadosEscolaresEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.DadosEscolaresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DadosEscolaresServiceTest {
    @Mock
    private DadosEscolaresRepository dadosEscolaresRepository;
    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private DadosEscolaresService dadosEscolaresService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
        ReflectionTestUtils.setField(dadosEscolaresService,"objectMapper",objectMapper);
    }

    @Test
    public void list() {
        List<DadosEscolaresEntity> dadosEscolaresEntityList = Arrays.asList(
                DadosEscolaresEntity.builder().build()
        );

        when(dadosEscolaresRepository.findAll()).thenReturn(dadosEscolaresEntityList);

        assertEquals(1,dadosEscolaresService.list(null).size());
    }

    @Test
    public void create() {
        LocalDate data = LocalDate.of(2000,1,1);
        DadosEscolaresCreateDTO dadosEscolaresCreateDTO = DadosEscolaresCreateDTO.builder().dataInicio(data).dataFim(data).build();
        DadosEscolaresEntity dadosEscolares = DadosEscolaresEntity.builder().build();
        CandidatoEntity candidato = CandidatoEntity.builder().build();

        try {
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidato);
            when(dadosEscolaresRepository.save(any(DadosEscolaresEntity.class))).thenReturn(dadosEscolares);

            dadosEscolaresService.create(1,dadosEscolaresCreateDTO);

            verify(dadosEscolaresRepository,times(1)).save(any(DadosEscolaresEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        LocalDate data = LocalDate.of(2000,1,1);
        DadosEscolaresCreateDTO dadosEscolaresCreateDTO = DadosEscolaresCreateDTO.builder().dataInicio(data).dataFim(data).build();
        DadosEscolaresEntity dadosEscolares = DadosEscolaresEntity.builder().build();
        CandidatoEntity candidato = CandidatoEntity.builder().build();

        try {
            when(dadosEscolaresRepository.findById(any(Integer.class))).thenReturn(Optional.of(dadosEscolares));
            when(dadosEscolaresRepository.save(any(DadosEscolaresEntity.class))).thenReturn(dadosEscolares);

            dadosEscolaresService.update(1,dadosEscolaresCreateDTO);

            verify(dadosEscolaresRepository,times(1)).save(any(DadosEscolaresEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        DadosEscolaresEntity dadosEscolares = DadosEscolaresEntity.builder().build();
        try {
            when(dadosEscolaresRepository.findById(any(Integer.class))).thenReturn(Optional.of(dadosEscolares));
            doNothing().when(dadosEscolaresRepository).delete(any(DadosEscolaresEntity.class));

            dadosEscolaresService.delete(1);

            verify(dadosEscolaresRepository,times(1)).delete(any(DadosEscolaresEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIdCandidato() {
        List<DadosEscolaresEntity> dadosEscolaresEntityList = Arrays.asList(
                DadosEscolaresEntity.builder().build()
        );

        when(dadosEscolaresRepository.findByIdCandidato(any(Integer.class))).thenReturn(dadosEscolaresEntityList);

        assertEquals(1,dadosEscolaresService.findByIdCandidato(1).size());
    }
}