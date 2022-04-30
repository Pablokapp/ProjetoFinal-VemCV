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
        if(vaga==null){//vaga nula, propaga excessao
            throw new RegraDeNegocioException("Vaga inexistente");
        }else if(!vaga.getStatus().equalsIgnoreCase(ABERTAS.get(0))&&!vaga.getStatus().equalsIgnoreCase(ABERTAS.get(1))){
            throw new RegraDeNegocioException("Vaga não está em andamento");
        }else if(vaga.getCandidatos()==null){//lista de candidatos nula, cria lista
            Set<CandidatoEntity> candidatos = new HashSet<>();
            candidatos.add(candidato);
            vaga.setCandidatos(candidatos);
        }else if(vaga.getCandidatos().contains(candidato)){
            throw new RegraDeNegocioException("Candidato já vinculado à vaga");
        } else{//lista existente, adiciona candidato
            Set<CandidatoEntity> candidatos = vaga.getCandidatos();
            candidatos.add(candidato);
//            vaga.setCandidatos(candidatos);
        }
        this.save(vaga);
    }



//    @PostConstruct
//    @Scheduled(cron = "* * 4 * * *")
    public void atualizarTodasVagas() throws RegraDeNegocioException {
        this.verificarAcesso();

        serverPropertiesService.setStatusAtualizando();

        PaginaVagasCompleoReduzidaDTO paginaCompleo = vagasCompleoService.listar(1,1);

        //busca vagas locais em uma consulta só
//        Map<Integer,VagaEntity> vagaEntityList = vagaRepository.findAll().stream().collect(Collectors.toMap(VagaEntity::getId,v->v));
        VagaEntity vagaLocal;

        for(int paginaAtual=1; (paginaAtual-1)*QUANTIDADE_POR_PAGINA < paginaCompleo.getTotal(); paginaAtual++){
            paginaCompleo = vagasCompleoService.listarAlteracoes(paginaAtual,QUANTIDADE_POR_PAGINA);

            for(VagaCompleoReduzidaDTO vaga: paginaCompleo.getVagas()){

                vagaLocal = this.findById(vaga.getId());//vagaEntityList.getOrDefault(vaga.getId(), null);////todo testar uma consulta só

                if(vagaLocal==null){
                    log.info("salvando nova vaga de id: "+vaga.getId()+
                            "\ndata: "+vaga.getUltimaAtualizacao());
                    this.save(objectMapper.convertValue(vaga,VagaEntity.class));
                }else if(vaga.getUltimaAtualizacao().isAfter(vagaLocal.getUltimaAtualizacao())){
                    log.info("salvando atualizacao na vaga de id: "+vaga.getId()+
                            "\ndatas: "+vaga.getUltimaAtualizacao()+" || "+vagaLocal.getUltimaAtualizacao());
                    //salvar lista de candidatos na vaga atualizada antes de salvar
                    VagaEntity vagaExterna = objectMapper.convertValue(vaga, VagaEntity.class);
                    Set<CandidatoEntity> candidatos = vagaLocal.getCandidatos();
                    vagaExterna.setCandidatos(candidatos);
                    this.save(vagaExterna);
                }else{
                    log.info("sem atualizações na vaga de id: "+vaga.getId());
                }
            }
        }
        serverPropertiesService.setUltimaAtualizacao(LocalDateTime.now());
    }

    private void save(VagaEntity vaga){
        vagaRepository.save(vaga);
    }

    private VagaEntity findById(Integer idVaga){
        return vagaRepository.findById(idVaga)
                .orElse(null);
    }

    public LocalDateTime getDataUltimaAtualizacao() throws RegraDeNegocioException {
        return serverPropertiesService.getUltimaAtualizacao();
    }

    public void verificarAcesso() throws RegraDeNegocioException {
        if(serverPropertiesService.getServerStatus()==ServerStatus.ATUALIZANDO){
            throw new RegraDeNegocioException("Servidor atualizando, não é possível realizar esta operação");
        }
    }

    @Transactional
    public List<Integer> listarCandidatosPorVaga(Integer idVaga) throws RegraDeNegocioException {
        return vagaRepository.findById(idVaga).orElseThrow(()->new RegraDeNegocioException("Vaga não encontrada"))
                .getCandidatos()
                .stream()
                .map(CandidatoEntity::getIdCandidato)
                .collect(Collectors.toList());
    }

}