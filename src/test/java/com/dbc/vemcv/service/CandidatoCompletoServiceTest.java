package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoCreateDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CandidatoCompletoServiceTest {



    @Mock
    private ExperienciasService experienciasService;
    @Mock
    private DadosEscolaresService dadosEscolaresService;
    @Mock
    private CandidatoRepository candidatoRepository;
    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoCompletoService candidatoCompletoService;

    private ObjectMapper objectMapper = new ObjectMapper();


//    @Before
//    public void BeforeEach() {
//        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
//        ReflectionTestUtils.setField(candidatoCompletoService,"objectMapper",objectMapper);
//    }



    @Test
    void listPaginado() {


    }

    @Test
    void testeCriacaoDeUsuarioCompleto() throws RegraDeNegocioException {

        DadosEscolaresCreateDTO dadosEscolaresCreateDTO =
                DadosEscolaresCreateDTO.builder()
                .instituicao("instituicao")
                .dataInicio(LocalDate.now())
                .dataFim(LocalDate.of(2020, 1, 1))
                .descricao("descricao").build();

        List<DadosEscolaresCreateDTO> dadosEscolaresCreateDTOList = new ArrayList<>();
        dadosEscolaresCreateDTOList.add(dadosEscolaresCreateDTO);

        ExperienciasCreateDTO experienciasCreateDTO = ExperienciasCreateDTO.builder()
                .nomeEmpresa("nomeEmpresa")
                .dataInicio(LocalDate.now())
                .dataFim(LocalDate.of(2020, 1, 1))
                .descricao("descricao").build();

        List<ExperienciasCreateDTO> experienciasCreateDTOList = new ArrayList<>();
        experienciasCreateDTOList.add(experienciasCreateDTO);

        CandidatoCompletoCreateDTO candidatoCompletoCreateDTO = CandidatoCompletoCreateDTO.builder()
                .nome("nome")
                .cpf("012458785669")
                .dataNascimento(LocalDate.of(2020, 1, 1))
                .logradouro("logradouro")
                .numero(112)
                .bairro("bairro")
                .cidade("cidade")
                .telefone("(11) 99999-9999")
                .cargo("cargo")
                .senioridade("senioridade")
                .dadosEscolares(dadosEscolaresCreateDTOList)
                .experiencias(experienciasCreateDTOList)
                .build();


//        when(objectMapper.convertValue(any(CandidatoCompletoCreateDTO.class), CandidatoCompletoDTO.class))
        CandidatoCompletoDTO candidatoCompletoDTO = CandidatoCompletoDTO.builder().build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoCompletoDTO);
        candidatoCompletoDTO.setIdCandidato(1);

        ExperienciasDTO experienciasDTO = ExperienciasDTO.builder().build();
        BeanUtils.copyProperties(experienciasCreateDTO, experienciasDTO);

        CandidatoDTO candidatoDTO = CandidatoDTO.builder().build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoDTO);

        DadosEscolaresDTO dadosEscolaresDTO = DadosEscolaresDTO.builder().build();
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, dadosEscolaresDTO);

        when(candidatoService.create(any(CandidatoCreateDTO.class))).thenReturn(candidatoDTO);

        when(dadosEscolaresService.create(any(Integer.class), any(DadosEscolaresCreateDTO.class))).thenReturn(dadosEscolaresDTO);

        when(experienciasService.create(any(),any(ExperienciasCreateDTO.class))).thenReturn(experienciasDTO);

        candidatoCompletoService.create(candidatoCompletoCreateDTO);

        verify(candidatoCompletoService).create(any(CandidatoCompletoCreateDTO.class));

    }

    @Test
    void update() {
    }
}