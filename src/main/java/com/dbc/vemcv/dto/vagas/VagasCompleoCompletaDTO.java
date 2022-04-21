package com.dbc.vemcv.dto.vagas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class VagasCompleoCompletaDTO {
    @JsonProperty("Ano_Mes_Dt_Abertura")
    private String Ano_Mes_Dt_Abertura;
    @JsonProperty("Semana_Data_Abertura")
    private Date Semana_Data_Abertura;
    @JsonProperty("Ano_Mes_Dt_Fechamento")
    private String Ano_Mes_Dt_Fechamento;
    @JsonProperty("Semana_Data_Fechamento")
    private Date Semana_Data_Fechamento;
    @JsonProperty("Ano_Mes_Dt_Cancelamento")
    private String Ano_Mes_Dt_Cancelamento;
    @JsonProperty("Semana_Data_Cancelamento")
    private Date Semana_Data_Cancelamento;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("Codigo")
    private String codigo;
    @JsonProperty("Titulo")
    private String titulo;
    @JsonProperty("Posicoes")
    private byte posicoes;
    @JsonProperty("Privada")
    private Boolean privada;
    @JsonProperty("StatusVaga_Id")
    private byte statusVagaId;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Cliente")
    private String cliente;
    @JsonProperty("Contato")
    private String contato;
    @JsonProperty("Analista")
    private String analista;
    @JsonProperty("DataAbertura")
    private Date dataAbertura;
    @JsonProperty("DataFechamento")
    private Date dataFechamento;
    @JsonProperty("Filial")
    private String filial;
    @JsonProperty("DataCancelamento")
    private Date dataCancelamento;
    @JsonProperty("PCD")
    private Boolean pcd;
    @JsonProperty("Categoria")
    private String categoria;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Estado")
    private String estado;
    @JsonProperty("Temporario")
    private Boolean temporario;
    @JsonProperty("Hunting")
    private Boolean hunting;
    @JsonProperty("ValorMinimoCobradoCliente")
    private Double valorMinimoCobradoCliente;
    @JsonProperty("ValorMaximoCobradoCliente")
    private Double valorMaximoCobradoCliente;
    @JsonProperty("TipoValorCliente")
    private String tipoValorCliente;
    @JsonProperty("MotivoCancelamento")
    private String motivoCancelamento;
    @JsonProperty("TipoContratacao")
    private String tipoContratacao;
    @JsonProperty("DataLimiteContratacao")
    private Date dataLimiteContratacao;
    @JsonProperty("Dias_Desde_Abertura")
    private Integer diasDesdeAbertura;
    @JsonProperty("Dias_Ate_DataLimiteContratacao")
    private Integer diasAteDataLimiteContratacao;
    @JsonProperty("Dias_Entre_Abertura_e_Fechamento")
    private Integer diasEntreAberturaEFechamento;
    @JsonProperty("Dias_entre_DataLimiteContratacao_e_Fechamento")
    private Integer diasEntreDataLimiteContratacaoEFechamento;
    @JsonProperty("ContatoEmail")
    private String contatoEmail;
    @JsonProperty("Responsavel")
    private String responsavel;
    @JsonProperty("Contratados")
    private Integer contratados;
    @JsonProperty("DiferencaPosicoesContratados")
    private Integer diferencaPosicoesContratados;
    @JsonProperty("Justificativa")
    private String justificativa;
    @JsonProperty("DescricaoObservacao")
    private String descricaoObservacao;
    @JsonProperty("NomeSubstituido")
    private String nomeSubstituido;
    @JsonProperty("VagaPrioridadeDescricao")
    private String vagaPrioridadeDescricao;
    @JsonProperty("CampoGenerico")
    private String campoGenerico;
    /*@JsonProperty("HistoricoMudancaStatus")
    private List<HistoricoMudancaStatusVagaDTO>
    @JsonProperty("ListaContatos")
    private List<ListaContatosDTO>*/
    @JsonProperty("Tags")
    private List<String> tags;
}
