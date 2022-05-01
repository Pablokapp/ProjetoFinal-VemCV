package com.dbc.vemcv.service;

import com.dbc.vemcv.config.client.CompleoClient;
import com.dbc.vemcv.dto.vagas.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagasCompleoService {
    private final CompleoClient compleoClient;

    public PaginaVagasCompleoReduzidaDTO listar(Integer pagina, Integer quantidadePorPagina){
        PaginaVagasCompleoCompletaDTO paginaCompleo = compleoClient.listar(pagina, quantidadePorPagina, false);

        List<VagaCompleoReduzidaDTO> vagasReduzidasDTO = paginaCompleo.getVagaGeralList().stream()
                .map(v -> {
                    log.info("Titulo da vaga buscada: " + v.getTitulo() + " | status: "+v.getStatus());
                    return this.mapVagaCompleo(v);
                })
                .collect(Collectors.toList());

        return this.montarPagina(vagasReduzidasDTO, paginaCompleo);
    }

    public PaginaVagasCompleoReduzidaDTO listarAlteracoes(Integer pagina, Integer quantidadePorPagina){
        PaginaVagasCompleoCompletaDTO paginaCompleo = compleoClient.listar(pagina, quantidadePorPagina, true);

        List<VagaCompleoReduzidaDTO> vagasReduzidasDTO = paginaCompleo.getVagaGeralList().stream()
                .map(v -> {
                    log.info("Titulo da vaga buscada: " + v.getTitulo() + " | status: "+v.getStatus());
                    VagaCompleoReduzidaDTO vagaCompleoReduzidaDTO = this.mapVagaCompleo(v);
                    vagaCompleoReduzidaDTO.setUltimaAtualizacao(v.getHistoricoMudancaStatus().stream()
                            .map(HistoricoMudancaStatusVagaDTO::getData)
                            .max(LocalDateTime::compareTo)
                            .orElse(v.getDataAbertura().toInstant().atZone(ZoneId.of("UTC-03:00")).toLocalDateTime()));//se nao tiver atualizacoes na vaga, usa a data de criacao como ultima atualizacao
                    return vagaCompleoReduzidaDTO;
                })
                .collect(Collectors.toList());

        return this.montarPagina(vagasReduzidasDTO, paginaCompleo);
    }

    private PaginaVagasCompleoReduzidaDTO montarPagina(List<VagaCompleoReduzidaDTO> vagasReduzidasDTO, PaginaVagasCompleoCompletaDTO pagina){
        return PaginaVagasCompleoReduzidaDTO.builder()
                .vagas(vagasReduzidasDTO)
                .pagina(pagina.getPagina())
                .paginas(pagina.getPaginas())
                .quantidade(pagina.getQuantidade())
                .total(pagina.getTotal())
                .build();
    }

    private VagaCompleoReduzidaDTO mapVagaCompleo(VagasCompleoCompletaDTO vagaCompleta){
        return VagaCompleoReduzidaDTO.builder()
                .id(vagaCompleta.getId())
                .titulo(vagaCompleta.getTitulo())
                .status(vagaCompleta.getStatus())
                .responsavel(vagaCompleta.getResponsavel())
                .estado(vagaCompleta.getEstado())
                .dataAbertura(vagaCompleta.getDataAbertura().toInstant().atZone(ZoneId.of("UTC-03:00")).toLocalDate())
                .cliente(vagaCompleta.getCliente())
                .cidade(vagaCompleta.getCidade())
                .analista(vagaCompleta.getAnalista())
                .pcd(vagaCompleta.getPcd())
                .ultimaAtualizacao(vagaCompleta.getDataAbertura().toInstant().atZone(ZoneId.of("UTC-03:00")).toLocalDate().atStartOfDay())//seta data da ultima atualizacao para o dia de criacao
                .build();
    }
}