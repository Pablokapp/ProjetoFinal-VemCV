package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.PaginaCandidatoDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoCreateDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.candidatocompleto.PaginaCandidatoCompletoDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.DadosEscolaresEntity;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoCompletoServiceTest {



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
//
//    @Mock
//    private ObjectMapper objectMapper;

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();


    private DadosEscolaresCreateDTO dadosEscolaresCreateDTO;
    private ExperienciasCreateDTO experienciasCreateDTO;
    private CandidatoCreateDTO candidatoCreateDTO;
    private CandidatoCompletoCreateDTO candidatoCompletoCreateDTO;
    private CandidatoDTO candidatoDTO;
    private CandidatoCompletoDTO candidatoCompletoDTO;
    private ExperienciasDTO experienciasDTO;
    private DadosEscolaresDTO dadosEscolaresDTO;
    private DadosEscolaresEntity dadosEscolaresEntity;

    @Before
    public void BeforeEach() {
        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
        ReflectionTestUtils.setField(candidatoCompletoService,"objectMapper",objectMapper);


        dadosEscolaresCreateDTO =
                DadosEscolaresCreateDTO.builder()
                        .instituicao("instituicao")
                        .dataInicio(LocalDate.now())
                        .dataFim(LocalDate.of(2020, 1, 1))
                        .descricao("descricao").build();

        List<DadosEscolaresCreateDTO> dadosEscolaresCreateDTOList = new ArrayList<>();
        dadosEscolaresCreateDTOList.add(dadosEscolaresCreateDTO);

        experienciasCreateDTO = ExperienciasCreateDTO.builder()
                .nomeEmpresa("nomeEmpresa")
                .dataInicio(LocalDate.now())
                .dataFim(LocalDate.of(2020, 1, 1))
                .descricao("descricao").build();

        List<ExperienciasCreateDTO> experienciasCreateDTOList = new ArrayList<>();
        experienciasCreateDTOList.add(experienciasCreateDTO);

        candidatoCompletoCreateDTO = CandidatoCompletoCreateDTO.builder()
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

        candidatoCompletoCreateDTO.setDadosEscolares(dadosEscolaresCreateDTOList);
        candidatoCompletoCreateDTO.setExperiencias(experienciasCreateDTOList);

//        when(objectMapper.convertValue(any(CandidatoCompletoCreateDTO.class), CandidatoCompletoDTO.class))
        candidatoCompletoDTO = CandidatoCompletoDTO.builder().build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoCompletoDTO);
        candidatoCompletoDTO.setIdCandidato(1);

        experienciasDTO = ExperienciasDTO.builder().build();
        BeanUtils.copyProperties(experienciasCreateDTO, experienciasDTO);

        candidatoDTO = CandidatoDTO.builder().idCandidato(1).build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoDTO);


        candidatoCreateDTO = CandidatoCreateDTO.builder().build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoCreateDTO);

        dadosEscolaresDTO = DadosEscolaresDTO.builder().build();
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, dadosEscolaresDTO);

        dadosEscolaresEntity = DadosEscolaresEntity.builder().build();
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, dadosEscolaresEntity);

    }

    @Test
    public void listPaginado() {
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<CandidatoEntity> page = new PageImpl<>(new ArrayList<>());
//        when(candidatoRepository.findAll(pageable)).thenReturn(page);

        List<CandidatoDTO> candidatoDTOList = new ArrayList<>();
        candidatoDTOList.add(candidatoDTO);

        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        dadosEscolaresDTOList.add(dadosEscolaresDTO);

        PaginaCandidatoDTO paginaCandidatoDTO = PaginaCandidatoDTO.builder().candidatos(candidatoDTOList).totalDeElementos(1L).totalDePaginas(1).build();
        paginaCandidatoDTO.setCandidatos(candidatoDTOList);
//
        try {

            when(candidatoService.listPaginado(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(paginaCandidatoDTO);
            when(objectMapper.convertValue(candidatoDTO, CandidatoCompletoDTO.class)).thenReturn(candidatoCompletoDTO);

            when(dadosEscolaresService.findByIdCandidato(any(Integer.class))).thenReturn(dadosEscolaresDTOList);


            PaginaCandidatoCompletoDTO paginaCandidatoCompletoDTO = candidatoCompletoService.listPaginado(1, 0, 10);

            assertEquals(1,paginaCandidatoCompletoDTO.getCandidatosCompletos().size());
            assertEquals(1,paginaCandidatoCompletoDTO.getTotalDePaginas());
            assertEquals(1,paginaCandidatoCompletoDTO.getTotalDeElementos());


        } catch (RegraDeNegocioException e) {
            assertEquals(e.getMessage(), "Erro ao listar candidatos");
        }
    }

    @Test
    public void testeCriacaoDeUsuarioCompleto() throws RegraDeNegocioException {

        CandidatoCreateDTO candidatoCreateDTO = CandidatoCreateDTO.builder().build();
        BeanUtils.copyProperties(candidatoCompletoCreateDTO, candidatoCreateDTO);

        DadosEscolaresDTO dadosEscolaresDTO = DadosEscolaresDTO.builder().build();
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, dadosEscolaresDTO);

        when(candidatoService.create(any(CandidatoCreateDTO.class))).thenReturn(candidatoDTO);

        when(dadosEscolaresService.create(any(Integer.class), any(DadosEscolaresCreateDTO.class))).thenReturn(dadosEscolaresDTO);

        when(experienciasService.create(any(),any(ExperienciasCreateDTO.class))).thenReturn(experienciasDTO);

        when(objectMapper.convertValue(candidatoCompletoCreateDTO, CandidatoCreateDTO.class)).thenReturn(candidatoCreateDTO);
        when(objectMapper.convertValue(candidatoDTO, CandidatoCompletoDTO.class)).thenReturn(candidatoCompletoDTO);


        candidatoCompletoService.create(candidatoCompletoCreateDTO);

        verify(candidatoService, times(1)).create(any(CandidatoCreateDTO.class));
        verify(dadosEscolaresService, times(1)).create(any(Integer.class), any(DadosEscolaresCreateDTO.class));
        verify(experienciasService, times(1)).create(any(), any(ExperienciasCreateDTO.class));

        assertEquals(candidatoCompletoDTO, candidatoCompletoService.create(candidatoCompletoCreateDTO));
    }

    @Test
    public void update() throws RegraDeNegocioException {

        when(objectMapper.convertValue(candidatoCompletoCreateDTO, CandidatoCreateDTO.class)).thenReturn(candidatoCreateDTO);

        when(candidatoService.update(any(Integer.class), any(CandidatoCreateDTO.class))).thenReturn(candidatoDTO);

        when(objectMapper.convertValue(candidatoDTO, CandidatoCompletoDTO.class)).thenReturn(candidatoCompletoDTO);

        candidatoCompletoService.update(1, candidatoCompletoCreateDTO);

        verify(candidatoService, times(1)).update(any(Integer.class), any(CandidatoCreateDTO.class));

    }
}