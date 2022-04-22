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

@RestController
@RequestMapping("/candidato-completo")
@Validated
@RequiredArgsConstructor
public class CandidatoCompletoController {
    private final CandidatoCompletoService candidatoCompletoService;
    @PostMapping
    public ResponseEntity<CandidatoCompletoDTO> create(@RequestBody CandidatoCompletoCreateDTO candidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoCompletoService.create(candidato));
    }

    @GetMapping("/get-paginado")
    public ResponseEntity<PaginaCandidatoCompletoDTO> listPaginado(@RequestParam(value = "idCandidato", required = false) Integer idCandidato,
                                                                   @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
                                                                   @RequestParam(value = "quantidadePorPagina", required = false, defaultValue = "10") Integer quantidadePorPagina) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoCompletoService.listPaginado(idCandidato,pagina,quantidadePorPagina));
    }
}
