package com.dbc.vemcv.service;



import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagaService {
    private final VagaRepository vagaRepository;
    private final VagasCompleoService vagasCompleoService;
    private final CandidatoService candidatoService;
    private final ObjectMapper objectMapper;
    private static final Integer QUANTIDADE_POR_PAGINA = 100;
    private static LocalDateTime ultimaAtualizacao;

/*
//    @Scheduled(cron = "* * 5 * * *")
    public void atualizarVagasUltimoDia(){
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
    }*/

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

    @Transactional
    public void vincularCandidato(Integer idVaga, Integer idCandidato) throws RegraDeNegocioException {
        VagaEntity vaga = this.findById(idVaga);
        CandidatoEntity candidato = candidatoService.findById(idCandidato);
        if(vaga==null){//vaga nula, propaga excessao
            throw new RegraDeNegocioException("Vaga inexistente");
        }else if(vaga.getCandidatos()==null){//lista de candidatos nula, cria lista
            Set<CandidatoEntity> candidatos = new HashSet<>();
            candidatos.add(candidato);
            vaga.setCandidatos(candidatos);
        }else{//lista existente, adiciona candidato
            Set<CandidatoEntity> candidatos = vaga.getCandidatos();
            candidatos.add(candidato);
            vaga.setCandidatos(candidatos);
        }
        vagaRepository.save(vaga);
    }

//    @PostConstruct
    public void atualizarTodasVagas(){
        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listar(1,1);


        for(int paginaAtual=1; paginaAtual*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listarAlteracoes(paginaAtual,QUANTIDADE_POR_PAGINA);

            for(VagaCompleoReduzidaDTO vaga: paginaCompleo.getVagas()){

                VagaEntity vagaLocal = this.findById(vaga.getId());
                if(vagaLocal==null||vagaLocal.getUltimaAtualizacao()==null){
                    log.info("salvando nova vaga de id: "+vaga.getId()+
                            "data: "+vaga.getUltimaAtualizacao());
                    vagaRepository.save(objectMapper.convertValue(vaga,VagaEntity.class));
                }else if(vaga.getUltimaAtualizacao().isAfter(vagaLocal.getUltimaAtualizacao())){
                    log.info("salvando atualizacao na vaga de id: "+vaga.getId()+
                            "datas: "+vaga.getUltimaAtualizacao()+" || "+vagaLocal.getUltimaAtualizacao());
                    //salvar lista de candidatos na vaga atualizada antes de salvar
                    VagaEntity vagaExterna = objectMapper.convertValue(vaga, VagaEntity.class);
                    Set<CandidatoEntity> candidatos = vagaLocal.getCandidatos();
                    vagaExterna.setCandidatos(candidatos);
                    vagaRepository.save(vagaExterna);
                }else{
                    log.info("sem atualizações na vaga de id: "+vaga.getId());
                }
            }
        }
        ultimaAtualizacao = LocalDateTime.now();
    }

    private VagaEntity findById(Integer idVaga){
        return vagaRepository.findById(idVaga)
                .orElse(null);
    }

    public LocalDateTime getDataUltimaAtualizacao(){
        return ultimaAtualizacao;
    }

}