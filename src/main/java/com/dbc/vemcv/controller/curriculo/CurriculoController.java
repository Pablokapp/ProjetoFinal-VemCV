package com.dbc.vemcv.controller.curriculo;

import com.dbc.vemcv.dto.curriculo.CurriculoDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.CurriculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/curriculo")
@Validated
@RequiredArgsConstructor
public class CurriculoController {

    private final CurriculoService curriculoService;

    @PostMapping("/upload-curriculo/{idCandidato}")
    ResponseEntity<CurriculoDTO> uploadCurriculo(@RequestPart("curriculo") MultipartFile curriculo, @PathVariable("idCandidato") Integer idCandidato) throws RegraDeNegocioException {
    return ResponseEntity.ok(curriculoService.uploadCurriculoCandidato(curriculo, idCandidato));
    }

    @GetMapping("/download-curriculo/{idCandidato}")
    public ResponseEntity<Resource> downloadCurriculo(@PathVariable Integer idCandidato) {
        return curriculoService.downloadCurriculo(idCandidato);
    }
}