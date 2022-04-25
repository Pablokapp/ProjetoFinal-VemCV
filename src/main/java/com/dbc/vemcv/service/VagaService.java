package com.dbc.vemcv.service;



import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagaService {
    private final VagaRepository vagaRepository;
    private final VagasCompleoService vagasCompleoService;
    private final ObjectMapper objectMapper;
    private static final Integer QUANTIDADE_POR_PAGINA = 100;
    private static LocalDateTime ultimaAtualizacao;

    public void buscarNovasVagas(){
        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listarNovos(1,1);//paginaCompleo.total = quantidade total de elementos

        for(int paginaAtual=1; paginaAtual*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listar(paginaAtual,QUANTIDADE_POR_PAGINA);

            vagaRepository.saveAll(paginaCompleo.getVagas().stream()
                    .map(v->objectMapper.convertValue(v, VagaEntity.class))
                    .collect(Collectors.toList()));
        }
    }

//    @Scheduled(cron = "* * 5 * * *")
    public void atualizarVagas(){
        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listarNovos(1,1);//paginaCompleo.total = quantidade total de elementos

        for(int paginaAtual=1; paginaAtual*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listarAlteracoes(paginaAtual,QUANTIDADE_POR_PAGINA);

            vagaRepository.saveAll(paginaCompleo.getVagas().stream()
                    .map(v->objectMapper.convertValue(v, VagaEntity.class))
                    .collect(Collectors.toList()));
        }
        ultimaAtualizacao = LocalDateTime.now();
    }

//    @PostConstruct
    public void preencherVagas(){
        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listar(1,1);
        log.info("Requisição de vagas do compleo: " +
                "\npagina: "+ paginaCompleo.getPagina()+
                "\npaginas: "+ paginaCompleo.getPaginas()+
                "\ntotal: "+ paginaCompleo.getTotal()+
                "\nquantidade: "+ paginaCompleo.getQuantidade()
        );
        for(int paginaAtual=1; paginaAtual*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listar(paginaAtual,QUANTIDADE_POR_PAGINA);

            vagaRepository.saveAll(paginaCompleo.getVagas().stream()
                    .map(v->objectMapper.convertValue(v, VagaEntity.class))
                    .collect(Collectors.toList()));
        }
        ultimaAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime ultimaAtualizacao(){
        return ultimaAtualizacao;
    }

    public PaginaVagasCompleoReduzidaDTO listarVagasEmAberto(Integer pagina, Integer quantidadePorPagina){
        Pageable pageable = PageRequest.of(pagina, quantidadePorPagina);
        Page<VagaEntity> vagasPaginadas = vagaRepository.findByStatus1OrStatus2("Em Andamento", "Aberto", pageable);

        List<VagaEntity> vagas = new ArrayList<>(vagasPaginadas.stream().collect(Collectors.toList()));


        return PaginaVagasCompleoReduzidaDTO.builder()
                .vagas(vagas.stream()
                        .map(v->objectMapper.convertValue(v,VagaCompleoReduzidaDTO.class))
                        .collect(Collectors.toList()))
                .total(Long.valueOf(vagasPaginadas.getTotalElements()).intValue())
                .paginas(vagasPaginadas.getTotalPages())
                .pagina(pagina)
                .quantidade(quantidadePorPagina)
                .build();
    }


}