package com.dbc.vemcv.controller.experiencia;

import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.ExperienciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/experiencias")
@Validated
@RequiredArgsConstructor
public class ExperienciasController implements ExperienciaAPI{

    private final ExperienciasService experienciaService;


    @GetMapping("/listar")
    public ResponseEntity<List<ExperienciasDTO>> listar(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) {
        return ResponseEntity.ok(experienciaService.list(idCandidato));
    }

    @PostMapping("/adicionar")
    public ResponseEntity<ExperienciasDTO> adicionar(@RequestParam(value = "idCandidato") Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(experienciaService.create(idCandidato, experienciasCreateDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ExperienciasDTO> atualizar(@RequestParam(value = "idCandidato") Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(experienciaService.update(idCandidato, experienciasCreateDTO));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> remover(@RequestParam(value = "idExperiencia") Integer idExperiencia) throws RegraDeNegocioException {
        return ResponseEntity.ok(experienciaService.delete(idExperiencia));
    }


}