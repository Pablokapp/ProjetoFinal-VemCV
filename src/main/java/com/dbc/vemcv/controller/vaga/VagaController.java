package com.dbc.vemcv.controller.vaga;


import com.dbc.vemcv.dto.vagas.VagaCompleoReduzidaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/get-vagas-compleo")
    public ResponseEntity<List<VagaCompleoReduzidaDTO>> getVagasCompleo(){
        return ResponseEntity.ok(Arrays.asList(
                VagaCompleoReduzidaDTO.builder()
                        .id(1)
                        .titulo("titulo1")
                        .status("status1")
                        .responsavel("responsavel1")
                        .estado("estado1")
                        .dataAbertura(LocalDate.now().minusYears(1))
                        .cliente("cliente1")
                        .cidade("cidade1")
                        .analista("analista1")
                        .build(),
                VagaCompleoReduzidaDTO.builder()
                        .id(2)
                        .titulo("titulo2")
                        .status("status2")
                        .responsavel("responsavel2")
                        .estado("estado2")
                        .dataAbertura(LocalDate.now().minusYears(1))
                        .cliente("cliente2")
                        .cidade("cidade2")
                        .analista("analista2")
                        .build(),
                VagaCompleoReduzidaDTO.builder()
                        .id(3)
                        .titulo("titulo3")
                        .status("status3")
                        .responsavel("responsavel3")
                        .estado("estado3")
                        .dataAbertura(LocalDate.now().minusYears(1))
                        .cliente("cliente3")
                        .cidade("cidade3")
                        .analista("analista3")
                        .build()
        ));
    }
}