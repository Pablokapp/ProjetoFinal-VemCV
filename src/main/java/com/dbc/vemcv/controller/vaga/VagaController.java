package com.dbc.vemcv.controller.vaga;


import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoReduzidaDTO;
import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import com.dbc.vemcv.service.VagaService;
import com.dbc.vemcv.service.VagasCompleoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/atualizar-vagas")
    public void atualizarVagas(){
        vagaService.atualizarVagas();
    }
}