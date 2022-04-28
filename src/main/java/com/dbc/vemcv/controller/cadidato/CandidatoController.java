package com.dbc.vemcv.controller.cadidato;

import com.dbc.vemcv.dto.candidato.*;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.CandidatoService;
import com.dbc.vemcv.service.DadosEscolaresService;
import com.dbc.vemcv.service.ExperienciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/candidato")
@Validated
@RequiredArgsConstructor
public class CandidatoController implements CandidatoAPI {

    private final CandidatoService candidatoService;

    @GetMapping
    public ResponseEntity<List<CandidatoDTO>> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.list(idCandidato));
    }

    @PostMapping
    public ResponseEntity<CandidatoDTO> create(@RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.create(candidatoCreateDTO));
    }

    @PutMapping
    public ResponseEntity<CandidatoDTO> update(@RequestParam Integer idCandidato, @RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.update(idCandidato, candidatoCreateDTO));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        candidatoService.delete(idCandidato);
        return ResponseEntity.ok("Candidato deletado com sucesso");
    }

    @GetMapping("/get-paginado")
    public ResponseEntity<PaginaCandidatoDTO> listPaginado(@RequestParam(value = "idCandidato", required = false) Integer idCandidato,
                                                           @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
                                                           @RequestParam(value = "quantidadePorPagina", required = false, defaultValue = "10") Integer quantidadePorPagina) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.listPaginado(idCandidato,pagina,quantidadePorPagina));
    }

}