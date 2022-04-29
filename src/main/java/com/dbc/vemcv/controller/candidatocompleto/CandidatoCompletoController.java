package com.dbc.vemcv.controller.candidatocompleto;

import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoCreateDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.candidatocompleto.PaginaCandidatoCompletoDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.CandidatoCompletoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidato-completo")
@Validated
@RequiredArgsConstructor
public class CandidatoCompletoController implements CandidatoCompletoAPI {
    private final CandidatoCompletoService candidatoCompletoService;

    @PostMapping
    public ResponseEntity<CandidatoCompletoDTO> create(@Valid @RequestBody CandidatoCompletoCreateDTO candidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoCompletoService.create(candidato));
    }

    @GetMapping("/get-paginado")
    public ResponseEntity<PaginaCandidatoCompletoDTO> listPaginado(@RequestParam(value = "id-candidato", required = false) Integer idCandidato,
                                                                   @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
                                                                   @RequestParam(value = "quantidade-por-pagina", required = false, defaultValue = "10") Integer quantidadePorPagina) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoCompletoService.listPaginado(idCandidato,pagina,quantidadePorPagina));
    }

    @PutMapping
    public ResponseEntity<CandidatoCompletoDTO> update(@RequestParam(value = "id-candidato") Integer idCandidato, @Valid @RequestBody CandidatoCompletoCreateDTO candidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoCompletoService.update(idCandidato, candidato));
    }
}
