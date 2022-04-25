package com.dbc.vemcv.config.client;

import com.dbc.vemcv.dto.vagas.PaginaVagasCompleoCompletaDTO;
import com.dbc.vemcv.dto.vagas.VagasCompleoCompletaDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value="compleo", url="https://api.compleo.com.br")
@Headers({ "Content-Type: application/json", "auth-token: hzL2rHXeERNR" })//onde conseguir esse token?
public interface CompleoClient {
    @RequestLine("GET api/relatorios/listarRelatorioVagasGeral?Pagina={Pagina}&Quantidade={Quantidade}&RetornaHistoricoMudancaStatus={RetornaHistoricoMudancaStatus}")
    PaginaVagasCompleoCompletaDTO listar(@Param("Pagina") Integer pagina,
                                         @Param("Quantidade") Integer quantidade,
                                         @Param("RetornaHistoricoMudancaStatus") Boolean relatorioHistoricoMudancaStatus);
}

