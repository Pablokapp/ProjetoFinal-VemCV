package com.dbc.vemcv.dto.vagas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginaVagasCompleoReduzidaDTO {
    List<VagaCompleoReduzidaDTO> vagas;
    Integer total;
    Integer paginas;
    Integer pagina;
    Integer quantidade;
}
