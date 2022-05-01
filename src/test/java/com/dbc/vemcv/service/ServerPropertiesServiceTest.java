package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.usuario.UsuarioCreateDTO;
import com.dbc.vemcv.entity.CargoEntity;
import com.dbc.vemcv.entity.UsuarioEntity;
import com.dbc.vemcv.enums.Cargo;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.model.ServerProperties;
import com.dbc.vemcv.repository.CargoRepository;
import com.dbc.vemcv.repository.ServerPropertiesRepository;
import com.dbc.vemcv.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServerPropertiesServiceTest {
    @Mock
    private ServerPropertiesRepository serverPropertiesRepository;

    @InjectMocks
    private ServerPropertiesService serverPropertiesService;


    @Test
    public void serverInitNaoInicializado() {
        when(serverPropertiesRepository.existsById(any(Integer.class))).thenReturn(false);
        when(serverPropertiesRepository.save(any(ServerProperties.class))).thenReturn(new ServerProperties());

        try {
            serverPropertiesService.ServerInit();

            verify(serverPropertiesRepository,times(1)).save(any(ServerProperties.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serverInitJaInicializado() {
        ServerProperties serverProperties = new ServerProperties(1, LocalDateTime.of(2000,1,1,0,0), ServerStatus.ATUALIZANDO);

        when(serverPropertiesRepository.existsById(any(Integer.class))).thenReturn(true);
        when(serverPropertiesRepository.save(any(ServerProperties.class))).thenReturn(new ServerProperties());

        when(serverPropertiesRepository.findById(any(Integer.class))).thenReturn(Optional.of(serverProperties));

        try {
            serverPropertiesService.ServerInit();

            verify(serverPropertiesRepository,times(1)).save(any(ServerProperties.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUltimaAtualizacao() {
        LocalDateTime data = LocalDateTime.of(2000,1,1,0,0);
        ServerProperties serverProperties = new ServerProperties(1,data, ServerStatus.ATUALIZANDO);
        when(serverPropertiesRepository.findById(any(Integer.class))).thenReturn(Optional.of(serverProperties));

        try {
            assertEquals(data,serverPropertiesService.getUltimaAtualizacao());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setUltimaAtualizacaoInicializado() {
        LocalDateTime data = LocalDateTime.of(2000,1,1,0,0);
        ServerProperties serverProperties = new ServerProperties(1, LocalDateTime.of(2000,1,1,0,0), ServerStatus.ATUALIZANDO);

        when(serverPropertiesRepository.existsById(any(Integer.class))).thenReturn(true);
        when(serverPropertiesRepository.save(any(ServerProperties.class))).thenReturn(new ServerProperties());

        when(serverPropertiesRepository.findById(any(Integer.class))).thenReturn(Optional.of(serverProperties));

        try {
            serverPropertiesService.setUltimaAtualizacao(data);

            verify(serverPropertiesRepository,times(1)).save(any(ServerProperties.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setUltimaAtualizacaoNaoInicializado() {
        LocalDateTime data = LocalDateTime.of(2000,1,1,0,0);
        when(serverPropertiesRepository.existsById(any(Integer.class))).thenReturn(false);
        when(serverPropertiesRepository.save(any(ServerProperties.class))).thenReturn(new ServerProperties());

        try {
            serverPropertiesService.setUltimaAtualizacao(data);

            verify(serverPropertiesRepository,times(1)).save(any(ServerProperties.class));

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getServerStatus() {
    }

    @Test
    public void setStatusAtualizando() {
    }
}