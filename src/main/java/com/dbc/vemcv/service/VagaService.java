package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.VagaEntity;
import com.dbc.vemcv.enums.ServerStatus;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagaService {
    private final VagaRepository vagaRepository;
    private final VagasCompleoService vagasCompleoService;
    private final CandidatoService candidatoService;
    private final ServerPropertiesService serverPropertiesService;

    private final ObjectMapper objectMapper;
    private static final Integer QUANTIDADE_POR_PAGINA = 100;
    private static final List<String> ABERTAS = Arrays.asList("Em Andamento", "Aberta");

    public PaginaVagasCompleoReduzidaDTO listarVagasEmAberto(Integer pagina, Integer quantidadePorPagina) throws RegraDeNegocioException {
        this.verificarAcesso();

        Pageable pageable = PageRequest.of(pagina, quantidadePorPagina);
        Page<VagaEntity> vagasPaginadas = vagaRepository.findByStatusIn(ABERTAS, pageable);

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
        this.verificarAcesso();

        VagaEntity vaga = this.findById(idVaga);
        CandidatoEntity candidato = candidatoService.findById(idCandidato);
        if(vaga==null){//vaga nula, propaga excessão
            throw new RegraDeNegocioException("Vaga inexistente");
        }else if(vaga.getStatus().equalsIgnoreCase(ABERTAS.get(0))||vaga.getStatus().equalsIgnoreCase(ABERTAS.get(1))){
            throw new RegraDeNegocioException("Vaga não está em andamento");
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
//    @Scheduled(cron = "* * 4 * * *")
    public void atualizarTodasVagas() throws RegraDeNegocioException {
        this.verificarAcesso();

        serverPropertiesService.setStatusAtualizando();

        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listar(1,1);

        for(int paginaAtual=1; paginaAtual*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listarAlteracoes(paginaAtual,QUANTIDADE_POR_PAGINA);

            for(VagaCompleoReduzidaDTO vaga: paginaCompleo.getVagas()){

                VagaEntity vagaLocal = this.findById(vaga.getId());//todo testar uma consulta só
                if(vagaLocal==null){
                    log.info("salvando nova vaga de id: "+vaga.getId()+
                            "\ndata: "+vaga.getUltimaAtualizacao());
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
        serverPropertiesService.setUltimaAtualizacao(LocalDateTime.now());
    }

    private VagaEntity findById(Integer idVaga){
        return vagaRepository.findById(idVaga)
                .orElse(null);
    }

    public LocalDateTime getDataUltimaAtualizacao() throws RegraDeNegocioException {
        return serverPropertiesService.getUltimaAtualizacao();
    }

    private void verificarAcesso() throws RegraDeNegocioException {
        if(serverPropertiesService.getServerStatus()==ServerStatus.ATUALIZANDO){
            throw new RegraDeNegocioException("Servidor atualizando, não é possível realizar esta operação");
        }
    }

}