package com.dbc.vemcv.dto.vagas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PaginaVagasCompleoCompletaDTO {
    @JsonProperty("vagaGeralList")
    List<VagasCompleoCompletaDTO> vagaGeralList;
    @JsonProperty("total")
    Integer total;
    @JsonProperty("paginas")
    Integer paginas;
    @JsonProperty("pagina")
    Integer pagina;
    @JsonProperty("quantidade")
    Integer quantidade;
}
