package com.dbc.vemcv.config.client;

import com.dbc.vemcv.dto.vagas.VagasCompleoDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value="compleo", url="https://api.compleo.com.br")
@Headers("Content-Type: application/json")
public interface CompleoClient {

    @RequestLine("GET /api/relatorios/listarRelatorioVagasGeral?Pagina={Pagina}&Quantidade={Quantidade}")
    VagasCompleoDTO listar(@Param("Pagina") Integer pagina,
                           @Param("Quantidade") Integer quantidade);
}

