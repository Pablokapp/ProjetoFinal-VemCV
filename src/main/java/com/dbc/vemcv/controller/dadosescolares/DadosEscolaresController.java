package com.dbc.vemcv.controller.dadosescolares;

import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.DadosEscolaresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dados-escolares")
@Validated
@RequiredArgsConstructor
public class DadosEscolaresController implements DadosEscolaresAPI {

    private final DadosEscolaresService dadosEscolaresService;

    @GetMapping("/")
    public ResponseEntity<List<DadosEscolaresDTO>> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(dadosEscolaresService.list(idCandidato));
    }

    @PostMapping("/{id}")
    public ResponseEntity<DadosEscolaresDTO> create(@RequestParam("id") Integer idCandidato, @Valid @RequestBody DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(dadosEscolaresService.create(idCandidato, dadosEscolaresCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosEscolaresDTO> update(@RequestParam("id") Integer idCandidato, @Valid @RequestBody DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(dadosEscolaresService.update(idCandidato, dadosEscolaresCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestParam("id") Integer idDadosEscolares) throws RegraDeNegocioException {
        dadosEscolaresService.delete(idDadosEscolares);
        return ResponseEntity.ok("Dados Escolares deletados com sucesso");
    }






}