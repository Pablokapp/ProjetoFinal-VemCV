package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.PaginaCandidatoDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

    private CandidatoCreateDTO candidatoCreateDTO;
    private CandidatoEntity candidatoEntity;
    private CandidatoDTO candidatoDTO;
    private DadosEscolaresDTO dadosEscolaresDTO;
    private DadosEscolaresCreateDTO dadosEscolaresCreateDTO;

    @Before
    public void BeforeEach() {
        dadosEscolaresCreateDTO =
                DadosEscolaresCreateDTO.builder()
                        .instituicao("instituicao")
                        .dataInicio(LocalDate.now())
                        .dataFim(LocalDate.of(2020, 1, 1))
                        .descricao("descricao").build();

        candidatoCreateDTO = CandidatoCreateDTO.builder()
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

        candidatoDTO = CandidatoDTO.builder().idCandidato(1).build();
        BeanUtils.copyProperties(candidatoCreateDTO,candidatoDTO);

        candidatoEntity = CandidatoEntity.builder().build();
        BeanUtils.copyProperties(candidatoDTO,candidatoEntity);

        dadosEscolaresDTO = DadosEscolaresDTO.builder().build();
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, dadosEscolaresDTO);
    }

    @Test
    public void list() {
        List<CandidatoEntity> candidatoEntities = new ArrayList<>();
        candidatoEntities.add(candidatoEntity);
        when(candidatoRepository.findAll()).thenReturn(candidatoEntities);

        try {
            candidatoService.list(null);
            assertEquals(1, candidatoEntities.size());

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }

        verify(candidatoRepository, times(1)).findAll();

    }

    @Test
    public void create() throws RegraDeNegocioException {

        when(candidatoRepository.save(any(CandidatoEntity.class))).thenReturn(candidatoEntity);
        when(objectMapper.convertValue(candidatoCreateDTO, CandidatoEntity.class)).thenReturn(candidatoEntity);
        when(candidatoRepository.existsByCpf(anyString())).thenReturn(false);
        candidatoService.create(candidatoCreateDTO);
        verify(candidatoRepository, times(1)).save(any(CandidatoEntity.class));
    }

    @Test
    public void update() throws RegraDeNegocioException {

        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));
        when(candidatoRepository.save(any(CandidatoEntity.class))).thenReturn(candidatoEntity);

        candidatoService.update(candidatoDTO.getIdCandidato(), candidatoCreateDTO);
        verify(candidatoRepository, times(1)).save(any(CandidatoEntity.class));
    }

    @Test
    public void delete() throws RegraDeNegocioException {

        doNothing().when(candidatoRepository).delete(any(CandidatoEntity.class));
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));
        candidatoService.delete(candidatoDTO.getIdCandidato());
        verify(candidatoRepository, times(1)).delete(any(CandidatoEntity.class));
    }

    @Test
    public void listPaginado() {

        Pageable pageable = PageRequest.of(0, 10);
        Page<CandidatoEntity> page = new PageImpl<>(new ArrayList<>());
        List<CandidatoDTO> candidatoDTOList = new ArrayList<>();
        candidatoDTOList.add(candidatoDTO);

        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        dadosEscolaresDTOList.add(dadosEscolaresDTO);
        PaginaCandidatoDTO paginaCandidatoDTO = PaginaCandidatoDTO.builder().candidatos(candidatoDTOList).totalDeElementos(1L).totalDePaginas(1).build();
        paginaCandidatoDTO.setCandidatos(candidatoDTOList);

        try {

            when(objectMapper.convertValue(candidatoEntity, CandidatoDTO.class)).thenReturn(candidatoDTO);
            when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));
            PaginaCandidatoDTO paginaCandidatoDTORetorno = candidatoService.listPaginado(1,1, 1);

            assertEquals(paginaCandidatoDTO.getCandidatos().size(), paginaCandidatoDTORetorno.getCandidatos().size());
            assertEquals(paginaCandidatoDTO.getTotalDeElementos(), paginaCandidatoDTORetorno.getTotalDeElementos());
            assertEquals(paginaCandidatoDTO.getTotalDePaginas(), paginaCandidatoDTORetorno.getTotalDePaginas());

        } catch (RegraDeNegocioException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void findById() {
        when(candidatoRepository.findById(anyInt())).thenReturn(Optional.of(candidatoEntity));
        try {
            candidatoService.findById(candidatoDTO.getIdCandidato());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
        verify(candidatoRepository, times(1)).findById(anyInt());
    }
}