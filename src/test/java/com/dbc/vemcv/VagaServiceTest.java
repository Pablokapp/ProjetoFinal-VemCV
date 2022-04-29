package com.dbc.vemcv;

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
       /* when(vagaRepository.findById(any())).thenReturn(
                Arrays.asList(
                       1,2));*/
    }

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
