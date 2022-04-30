package com.dbc.vemcv;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        ReflectionTestUtils.setField(vagaService,"objectMapper",objectMapper);
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
//            when(vagaRepository.findById(any(Integer.class)).orElseThrow(()->new RegraDeNegocioException("Vaga não encontrada")).getCandidatos()).thenReturn(candidatos);

            assertEquals(1,this.vagaService.listarCandidatosPorVaga(1).get(0));
            assertEquals(2,this.vagaService.listarCandidatosPorVaga(1).get(1));
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }

    }

    /*when(vagaRepository.findById(any()).orElseThrow(any())).thenReturn(new VagaEntity(1,"","","","",
                LocalDate.of(2000,1,1),"",
                "","",true,
                LocalDateTime.of(2000,1,1,0,0),
                null));*/

    /* new VagaEntity(1,"","","","",
                                LocalDate.of(2000,1,1),"",
                                "","",true,
                                LocalDateTime.of(2000,1,1,0,0),
                                null),
                        new VagaEntity(2,"","","","",
                                LocalDate.of(2000,1,1),"",
                                "","",true,
                                LocalDateTime.of(2000,1,1,0,0),
                                null)*/
}
