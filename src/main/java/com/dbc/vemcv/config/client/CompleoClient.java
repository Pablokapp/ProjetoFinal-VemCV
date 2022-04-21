package com.dbc.vemcv.config.client;

import com.dbc.vemcv.dto.vagas.VagasCompleoCompletaDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value="compleo", url="https://api.compleo.com.br")
@Headers("Content-Type: application/json")
public interface CompleoClient {

    @RequestLine("GET /api/relatorios/listarRelatorioVagasGeral?Pagina={Pagina}&Quantidade={Quantidade}")
    VagasCompleoCompletaDTO listar(@Param("Pagina") Integer pagina,
                                   @Param("Quantidade") Integer quantidade);
}

