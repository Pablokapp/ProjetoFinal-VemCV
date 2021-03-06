package com.dbc.vemcv.service;

import com.dbc.vemcv.entity.CargoEntity;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.model.ServerProperties;
import com.dbc.vemcv.repository.CargoRepository;
import com.dbc.vemcv.repository.ServerPropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServerPropertiesService {
    private final ServerPropertiesRepository serverPropertiesRepository;
    private final CargoRepository cargoRepository;

    @PostConstruct//inicializa os dados do servidor
    public void ServerInit() throws RegraDeNegocioException {
        if(!cargoRepository.existsById(1)){
            cargoRepository.save(CargoEntity.builder().nome("ROLE_CADASTRADOR").build());
        }

        if(serverPropertiesRepository.existsById(1)){
            ServerProperties serverProperties = this.getServerProperties();
            serverProperties.setStatus(ServerStatus.INICIALIZADO);
            serverPropertiesRepository.save(serverProperties);
        }else{
            ServerProperties serverProperties = new ServerProperties(1,null, ServerStatus.INICIALIZADO);
            serverPropertiesRepository.save(serverProperties);
        }
    }

    public LocalDateTime getUltimaAtualizacao() throws RegraDeNegocioException {
        return this.getServerProperties().getUltimaAtualizacao();
    }

    public void setUltimaAtualizacao(LocalDateTime dataAtualizacao) throws RegraDeNegocioException{
        if(serverPropertiesRepository.existsById(1)){
            ServerProperties serverProperties = this.getServerProperties();
            serverProperties.setUltimaAtualizacao(dataAtualizacao);
            serverProperties.setStatus(ServerStatus.ATUALIZADO);
            serverPropertiesRepository.save(serverProperties);
        }else{
            ServerProperties serverProperties = new ServerProperties(1,dataAtualizacao, ServerStatus.ATUALIZADO);
            serverPropertiesRepository.save(serverProperties);
        }
    }

    public ServerStatus getServerStatus() throws RegraDeNegocioException {
        return this.getServerProperties().getStatus();
    }

    public void setStatusAtualizando() throws RegraDeNegocioException {
        ServerProperties serverProperties = this.getServerProperties();
        serverProperties.setStatus(ServerStatus.ATUALIZANDO);
        serverPropertiesRepository.save(serverProperties);
    }

    private ServerProperties getServerProperties() throws RegraDeNegocioException {
        return serverPropertiesRepository.findById(1)
                .orElseThrow(()->new RegraDeNegocioException("Servidor n??o inicializado"));
    }

}
