package com.dbc.vemcv.controller.vaga;


import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.VagaService;
import com.dbc.vemcv.service.VagasCompleoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
public class VagaController {
    private final VagasCompleoService vagasCompleoService;
    private final VagaService vagaService;

    @GetMapping("/get-vagas-compleo")
    public ResponseEntity<PaginaVagasCompleoReduzidaDTO> getVagasCompleo(@RequestParam("pagina") Integer pagina, @RequestParam("quantidade-por-pagina") Integer quantidadePorPagina){
        return ResponseEntity.ok(vagasCompleoService.listar(pagina, quantidadePorPagina));
    }

    @GetMapping("/get-vagas-aberto")
    public ResponseEntity<PaginaVagasCompleoReduzidaDTO> getVagasEmAberto(@RequestParam("pagina") Integer pagina, @RequestParam("quantidade-por-pagina") Integer quantidadePorPagina){
        return ResponseEntity.ok(vagaService.listarVagasEmAberto(pagina, quantidadePorPagina));
    }

    @PostMapping("/vincular-candidato")
    public void vincularCandidato(Integer idVaga, Integer idCandidato) throws RegraDeNegocioException {
        vagaService.vincularCandidato(idVaga, idCandidato);
    }

    @GetMapping("/forcaratualizacao")
    public void forcarAtualizacao() throws RegraDeNegocioException {
        vagaService.atualizarTodasVagas();
    }

    @GetMapping("/data-ultima-atualizacao")
    public ResponseEntity<LocalDateTime> getDataUltimaAtualizacao(){
        return ResponseEntity.ok(vagaService.getDataUltimaAtualizacao());
    }
}