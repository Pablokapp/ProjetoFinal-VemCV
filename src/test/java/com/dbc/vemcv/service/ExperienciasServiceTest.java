package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.ExperienciasEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.ExperienciasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class ExperienciasServiceTest {
    @Mock
    private ExperienciasRepository experienciasRepository;
    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private ExperienciasService experienciasService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
        ReflectionTestUtils.setField(experienciasService,"objectMapper",objectMapper);
    }

    @Test
    public void list() {
        List<ExperienciasEntity> experienciasEntityList = Arrays.asList(
                ExperienciasEntity.builder().build()
        );

        when(experienciasRepository.findAll()).thenReturn(experienciasEntityList);

        assertEquals(1,experienciasService.list(null).size());
    }

    @Test
    public void create() {
        ExperienciasCreateDTO experienciaDTO = ExperienciasCreateDTO.builder().build();
        ExperienciasEntity experiencia = ExperienciasEntity.builder().build();
        CandidatoEntity candidato = CandidatoEntity.builder().build();

        try {
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidato);
            when(experienciasRepository.save(any(ExperienciasEntity.class))).thenReturn(experiencia);

            experienciasService.create(1,experienciaDTO);

            verify(experienciasRepository,times(1)).save(any(ExperienciasEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        ExperienciasCreateDTO experienciaDTO = ExperienciasCreateDTO.builder().build();
        ExperienciasEntity experiencia = ExperienciasEntity.builder().build();
        CandidatoEntity candidato = CandidatoEntity.builder().build();

        try {
            when(experienciasRepository.findById(any(Integer.class))).thenReturn(Optional.of(experiencia));
            when(experienciasRepository.save(any(ExperienciasEntity.class))).thenReturn(experiencia);

            experienciasService.update(1,experienciaDTO);

            verify(experienciasRepository,times(1)).save(any(ExperienciasEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        ExperienciasEntity experiencia = ExperienciasEntity.builder().build();
        try {
            when(experienciasRepository.findById(any(Integer.class))).thenReturn(Optional.of(experiencia));
            doNothing().when(experienciasRepository).delete(any(ExperienciasEntity.class));

            experienciasService.delete(1);

            verify(experienciasRepository,times(1)).delete(any(ExperienciasEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByIdCandidato() {
        List<ExperienciasEntity> experienciasEntityList = Arrays.asList(
                ExperienciasEntity.builder().build()
        );

        when(experienciasRepository.findByIdCandidato(any(Integer.class))).thenReturn(experienciasEntityList);

        assertEquals(1,experienciasService.findByIdCandidato(1).size());
    }
}