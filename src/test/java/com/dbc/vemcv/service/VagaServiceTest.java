package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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

    @Test
    public void listarVagasEmAbertoDeveRetornarUmaPagina(){
        VagaEntity vaga = new VagaEntity(1,"","Em Aberto","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                null);
        Pageable pageable = PageRequest.of(0,10);
        Page<VagaEntity> paginaDeVagas = new PageImpl<VagaEntity>(Arrays.asList(vaga),pageable,1);

        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findByStatusIn(anyList(),any(Pageable.class))).thenReturn(paginaDeVagas);

            PaginaVagasCompleoReduzidaDTO resultado = vagaService.listarVagasEmAberto(1,1);

            assertEquals(1,resultado.getVagas().size());
            assertEquals(1,resultado.getTotal());
            assertEquals(1,resultado.getPaginas());
            assertEquals(1,resultado.getPagina());
            assertEquals(1,resultado.getQuantidade());

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void vincularCandidatoVagaInexistenteDeveDevolverExcessao(){
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setIdCandidato(1);

        VagaEntity vaga = null;



        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(vaga));
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidato);

            verify(vagaRepository,times(0)).save(any(VagaEntity.class));

            Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.vagaService.vincularCandidato(1,1));

            assertTrue(exception.getMessage().contains("Vaga inexistente"));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vincularCandidatoVagaFechadaDeveDevolverExcessao(){
        Set<CandidatoEntity> candidatos = new HashSet<>();
        CandidatoEntity candidato = new CandidatoEntity();
        CandidatoEntity candidato2 = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato2.setIdCandidato(2);
        candidatos.add(candidato);
        candidatos.add(candidato2);

        VagaEntity vaga = new VagaEntity(1,"","Fechada","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                candidatos);



        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidato);

            verify(vagaRepository,times(0)).save(any(VagaEntity.class));

            Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.vagaService.vincularCandidato(1,1));

            assertTrue(exception.getMessage().contains("Vaga não está em andamento"));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vincularCandidatoVagaSemCandidatosDeveCriarListaESalvar(){
        CandidatoEntity candidatoNovo = new CandidatoEntity();

        VagaEntity vaga = new VagaEntity(1,"","Em Andamento","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                null);


        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidatoNovo);

            vagaService.vincularCandidato(1,1);

            verify(vagaRepository,times(1)).save(any(VagaEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vincularCandidatosJaVinculadoDeveDevolverExcessao(){
        Set<CandidatoEntity> candidatos = new HashSet<>();
        CandidatoEntity candidato = new CandidatoEntity();
        CandidatoEntity candidato2 = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato2.setIdCandidato(2);
        candidatos.add(candidato);
        candidatos.add(candidato2);

        VagaEntity vaga = new VagaEntity(1,"","Em Andamento","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                candidatos);



        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidato);

            verify(vagaRepository,times(0)).save(any(VagaEntity.class));

            Exception exception = assertThrows(RegraDeNegocioException.class, ()->this.vagaService.vincularCandidato(1,1));

            assertTrue(exception.getMessage().contains("Candidato já vinculado à vaga"));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vincularCandidatosVagaJaPossuiCandidatosDeveAdicionarESalvar(){
        Set<CandidatoEntity> candidatos = new HashSet<>();
        CandidatoEntity candidato = new CandidatoEntity();
        CandidatoEntity candidato2 = new CandidatoEntity();
        candidato.setIdCandidato(1);
        candidato2.setIdCandidato(2);
        candidatos.add(candidato);
        candidatos.add(candidato2);

        CandidatoEntity candidatoNovo = new CandidatoEntity();

        VagaEntity vaga = new VagaEntity(1,"","Em Andamento","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                candidatos);


        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));
            when(candidatoService.findById(any(Integer.class))).thenReturn(candidatoNovo);

            vagaService.vincularCandidato(1,1);

            verify(vagaRepository,times(1)).save(any(VagaEntity.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

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

        VagaEntity vaga = null;

//        List<VagaEntity> vagaEntityList = new ArrayList<>();


        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
//            when(vagaRepository.findAll()).thenReturn(vagaEntityList);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(vaga));

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

        VagaEntity vaga = new VagaEntity(1,"","Em Andamento","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(1999,1,1,0,0),
                null);

//        List<VagaEntity> vagaEntityList = new ArrayList<>();
//        vagaEntityList.add(vaga);

        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
//            when(vagaRepository.findAll()).thenReturn(vagaEntityList);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));

            when(vagasCompleoService.listar(any(),any())).thenReturn(paginaVagasCompleo);
            when(vagasCompleoService.listarAlteracoes(any(),any())).thenReturn(paginaVagasCompleo);

            when(vagaRepository.save(any(VagaEntity.class))).thenReturn(vaga);

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

        VagaEntity vaga = new VagaEntity(1,"","Em Andamento","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2001,1,1,0,0),
                null);

//        List<VagaEntity> vagaEntityList = new ArrayList<>();
//        vagaEntityList.add(vaga);

        try {
            when(serverPropertiesService.getServerStatus()).thenReturn(ServerStatus.ATUALIZADO);
            doNothing().when(serverPropertiesService).setStatusAtualizando();
//            when(vagaRepository.findAll()).thenReturn(vagaEntityList);
            when(vagaRepository.findById(any(Integer.class))).thenReturn(Optional.of(vaga));

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
