package com.dbc.vemcv.service;

import com.dbc.vemcv.config.client.CompleoClient;
import com.dbc.vemcv.dto.vagas.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VagasCompleoServiceTest {
    @Mock
    private CompleoClient compleoClient;

    @InjectMocks
    private VagasCompleoService vagasCompleoService;

    @Test
    public void listar() {
        LocalDateTime dateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(dateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());
        List<VagasCompleoCompletaDTO> vagasList = Arrays.asList(
                VagasCompleoCompletaDTO.builder().dataAbertura(date).build(),
                VagasCompleoCompletaDTO.builder().dataAbertura(date).build()
        );
        PaginaVagasCompleoCompletaDTO paginaVagas = PaginaVagasCompleoCompletaDTO.builder()
                .vagaGeralList(vagasList)
                .pagina(1)
                .paginas(1)
                .quantidade(2)
                .total(2)
                .build();

        when(compleoClient.listar(any(Integer.class),any(Integer.class),any(Boolean.class))).thenReturn(paginaVagas);

        PaginaVagasCompleoReduzidaDTO paginaListada = vagasCompleoService.listar(0, 0);

        assertEquals(dateTime,paginaListada.getVagas().get(0).getUltimaAtualizacao());
        assertEquals(2,paginaListada.getVagas().size());

        verify(compleoClient,times(1)).listar(any(Integer.class),any(Integer.class),any(Boolean.class));
    }

    @Test
    public void listarAlteracoes() {
        LocalDateTime dateTime = LocalDateTime.of(2000,1,1,0,0);
        Date date = Date.from(dateTime.atZone(ZoneId.of("UTC-03:00")).toInstant());
        HistoricoMudancaStatusVagaDTO historico = HistoricoMudancaStatusVagaDTO.builder()
                .data(dateTime.plusYears(1))
                .build();
        List<VagasCompleoCompletaDTO> vagasList = Arrays.asList(
                VagasCompleoCompletaDTO.builder().dataAbertura(date).historicoMudancaStatus(Arrays.asList(historico)).build(),
                VagasCompleoCompletaDTO.builder().dataAbertura(date).historicoMudancaStatus(new ArrayList<>()).build()
        );
        PaginaVagasCompleoCompletaDTO paginaVagas = PaginaVagasCompleoCompletaDTO.builder()
                .vagaGeralList(vagasList)
                .pagina(1)
                .paginas(1)
                .quantidade(2)
                .total(2)
                .build();

        when(compleoClient.listar(any(Integer.class),any(Integer.class),any(Boolean.class))).thenReturn(paginaVagas);

        PaginaVagasCompleoReduzidaDTO paginaListada = vagasCompleoService.listarAlteracoes(0, 0);

        assertEquals(dateTime.plusYears(1),paginaListada.getVagas().get(0).getUltimaAtualizacao());
        assertEquals(dateTime,paginaListada.getVagas().get(1).getUltimaAtualizacao());
        assertEquals(2,paginaListada.getVagas().size());

        verify(compleoClient,times(1)).listar(any(Integer.class),any(Integer.class),any(Boolean.class));
    }
}