package com.dbc.vemcv.controller.vaga;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.VagaService;
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

    private final VagaService vagaService;

    @GetMapping("/buscar-vagas-aberto")
    public ResponseEntity<PaginaVagasCompleoReduzidaDTO> buscarVagasEmAberto(@RequestParam("pagina") Integer pagina, @RequestParam("quantidade-por-pagina") Integer quantidadePorPagina) throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.listarVagasEmAberto(pagina, quantidadePorPagina));
    }

    @PostMapping("/vincular-candidato")
    public void vincularCandidato(Integer idVaga, Integer idCandidato) throws RegraDeNegocioException {
        vagaService.vincularCandidato(idVaga, idCandidato);
    }

    @GetMapping("/atualizar")
    public void forcarAtualizacao() throws RegraDeNegocioException {
        vagaService.atualizarTodasVagas();
    }

    @GetMapping("/data-ultima-atualizacao")
    public ResponseEntity<LocalDateTime> getDataUltimaAtualizacao() throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.getDataUltimaAtualizacao());
    }

}