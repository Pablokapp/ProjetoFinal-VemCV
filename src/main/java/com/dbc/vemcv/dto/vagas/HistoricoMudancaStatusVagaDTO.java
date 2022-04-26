package com.dbc.vemcv.dto.vagas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoricoMudancaStatusVagaDTO {
    @JsonProperty("Data")
    private LocalDateTime data;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Observacao")
    private String observacao;
}
