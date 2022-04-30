package com.dbc.vemcv;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.VagaRepository;
import com.dbc.vemcv.service.CandidatoService;
import com.dbc.vemcv.service.ServerPropertiesService;
import com.dbc.vemcv.service.VagaService;
import com.dbc.vemcv.service.VagasCompleoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VagaServiceTest {
    @Mock
    private VagaRepository vagaRepository;
    @Mock
    private VagasCompleoService vagasCompleoService;
    @Mock
    private CandidatoService candidatoService;
    @Mock
    private ServerPropertiesService serverPropertiesService;

    @InjectMocks
    private VagaService vagaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        objectMapper.registerModule(new JavaTimeModule());//corrigir LocalDateTime no mapper
        ReflectionTestUtils.setField(vagaService,"objectMapper",objectMapper);
    }

    /*public void vincularCandidatoVagaInexistente(){}

    public void */





    @Test
    public void testarAtualizacaoDeVagasComVagaLocalNulaDeveApenasSalvar(){
        PaginaVagasCompleoReduzidaDTO paginaVagasCompleo = PaginaVagasCompleoReduzidaDTO.builder()
                .vagas(Arrays.asList(VagaCompleoReduzidaDTO.builder()
                        .id(1)
                        .build()))
                .pagina(0)
                .paginas(1)
                .quantidade(1)
                .total(1)
                .build();

        List<VagaEntity> vagaEntityList = new ArrayList<>();


        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
            when(vagaRepository.findAll()).thenReturn(vagaEntityList);

            when(vagasCompleoService.listar(any(),any())).thenReturn(paginaVagasCompleo);
            when(vagasCompleoService.listarAlteracoes(any(),any())).thenReturn(paginaVagasCompleo);

            vagaService.atualizarTodasVagas();

            verify(vagaRepository,times(1)).save(any(VagaEntity.class));
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testarAtualizacaoDeVagasSalvandoAtualizacaoDaVaga(){
        PaginaVagasCompleoReduzidaDTO paginaVagasCompleo = PaginaVagasCompleoReduzidaDTO.builder()
                .vagas(Arrays.asList(VagaCompleoReduzidaDTO.builder()
                                .id(1)
                                .ultimaAtualizacao(LocalDateTime.of(2000,1,1,0,0))
                        .build()))
                .pagina(0)
                .paginas(1)
                .quantidade(1)
                .total(1)
                .build();

        List<VagaEntity> vagaEntityList = new ArrayList<>();
        vagaEntityList.add(new VagaEntity(1,"","","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(1999,1,1,0,0),
                null));

        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
            when(vagaRepository.findAll()).thenReturn(vagaEntityList);

            when(vagasCompleoService.listar(any(),any())).thenReturn(paginaVagasCompleo);
            when(vagasCompleoService.listarAlteracoes(any(),any())).thenReturn(paginaVagasCompleo);

            when(vagaRepository.save(any(VagaEntity.class))).thenReturn(vagaEntityList.get(0));

            vagaService.atualizarTodasVagas();

            verify(vagaRepository,times(1)).save(any(VagaEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testarAtualizacaoDeVagasJaAtualizadaDeveApenasSair(){
        PaginaVagasCompleoReduzidaDTO paginaVagasCompleo = PaginaVagasCompleoReduzidaDTO.builder()
                .vagas(Arrays.asList(VagaCompleoReduzidaDTO.builder()
                        .id(1)
                        .ultimaAtualizacao(LocalDateTime.of(2000,1,1,0,0))
                        .build()))
                .pagina(0)
                .paginas(1)
                .quantidade(1)
                .total(1)
                .build();

        List<VagaEntity> vagaEntityList = new ArrayList<>();
        vagaEntityList.add(new VagaEntity(1,"","","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2001,1,1,0,0),
                null));

        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
            when(vagaRepository.findAll()).thenReturn(vagaEntityList);

            when(vagasCompleoService.listar(any(),any())).thenReturn(paginaVagasCompleo);
            when(vagasCompleoService.listarAlteracoes(any(),any())).thenReturn(paginaVagasCompleo);

            vagaService.atualizarTodasVagas();

            verify(vagaRepository,times(0)).save(any(VagaEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testarGetUltimaAtualizacao(){
        try {
            when(serverPropertiesService.getUltimaAtualizacao()).thenReturn(LocalDateTime.of(2000,1,1,0,0));

            assertEquals(LocalDateTime.of(2000,1,1,0,0), vagaService.getDataUltimaAtualizacao());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testaExcessaoVerificarAcesso(){
        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZANDO);

            Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.vagaService.verificarAcesso());

            assertTrue(exception.getMessage().contains("Servidor atualizando, não é possível realizar esta operação"));
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void retornaListaDeCandidatosPorVaga(){
        Set<CandidatoEntity> candidatos = new HashSet<>();
        CandidatoEntity candidato = new CandidatoEntity();
        CandidatoEntity candidato2 = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato2.setIdCandidato(2);
        candidatos.add(candidato);
        candidatos.add(candidato2);
        VagaEntity vagaEntity = new VagaEntity();
        vagaEntity.setCandidatos(candidatos);


        try {
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vagaEntity));


            assertEquals(2,this.vagaService.listarCandidatosPorVaga(1).size());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }

    }
}
