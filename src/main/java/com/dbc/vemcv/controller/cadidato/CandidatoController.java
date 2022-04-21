package com.dbc.vemcv.controller.cadidato;



import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDadosExperienciasDTO;
import com.dbc.vemcv.dto.candidato.CandidatoReduzidoDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
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


    @GetMapping("/dados-completos")
    public ResponseEntity<List<CandidatoDadosExperienciasDTO>> listCandidatosDadosExperiencias(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.listCandidatosDadosExperiencias(idCandidato));
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

    @GetMapping("/get-reduzido")
    public ResponseEntity<List<CandidatoReduzidoDTO>> getCandidatoReduzido(){
        return ResponseEntity.ok(Arrays.asList(
                CandidatoReduzidoDTO.builder()
                        .idCandidato(1)
                        .nome("nome1")
                        .cargo("cargo1")
                        .dataNascimento(LocalDate.now().minusYears(20))
                        .senioridade("senioridade1")
                        .build(),
                CandidatoReduzidoDTO.builder()
                        .idCandidato(2)
                        .nome("nome2")
                        .cargo("cargo2")
                        .dataNascimento(LocalDate.now().minusYears(20))
                        .senioridade("senioridade2")
                        .build()));
    }



}