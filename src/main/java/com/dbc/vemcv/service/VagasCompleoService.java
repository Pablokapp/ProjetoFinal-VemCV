package com.dbc.vemcv.service;

import com.dbc.vemcv.config.client.CompleoClient;
import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoCompletaDTO;
import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagasCompleoCompletaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagasCompleoService {
    private final CompleoClient compleoClient;
    private final ObjectMapper objectMapper;

    public PaginaVagasCompleoReduzidaDTO listarNovos(Integer pagina, Integer quantidadePorPagina){
        PaginaVagasCompleoCompletaDTO paginaCompleo = compleoClient.listar(pagina, quantidadePorPagina, false);

        List<VagaCompleoReduzidaDTO> vagasReduzidasDTO = paginaCompleo.getVagaGeralList().stream()
                .filter(v->v.getDataAbertura().after(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.of("UTC-03:00")).toInstant())))//todo ZoneId.systemDefault() talvez
                .map(v -> {
                    log.info("Titulo da vaga buscada: " + v.getTitulo() + " | status: "+v.getStatus());
                    return this.mapVagaCompleo(v);
                })
                .collect(Collectors.toList());

        return this.montarPagina(vagasReduzidasDTO, paginaCompleo);
    }

    public PaginaVagasCompleoReduzidaDTO listar(Integer pagina, Integer quantidadePorPagina){
        PaginaVagasCompleoCompletaDTO paginaCompleo = compleoClient.listar(pagina, quantidadePorPagina, false);

        List<VagaCompleoReduzidaDTO> vagasReduzidasDTO = paginaCompleo.getVagaGeralList().stream()
                .filter(v->v.getDataAbertura().after(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.of("UTC-03:00")).toInstant())))
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
                    return this.mapVagaCompleo(v);
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
                .build();
    }
}