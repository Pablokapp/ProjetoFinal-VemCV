package com.dbc.vemcv.dto.vagas;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginaVagasCompleoReduzidaDTO {
    @ApiModelProperty("Lista de vagas")
    List<VagaCompleoReduzidaDTO> vagas;
    @ApiModelProperty("Quantidade total de elementos no sistema")
    Integer total;
    @ApiModelProperty("Quantidade total de páginas")
    Integer paginas;
    @ApiModelProperty("Página atual")
    Integer pagina;
    @ApiModelProperty("Quantidade de elementos na página atual")
    Integer quantidade;
}
