package com.pdv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTOMessage {

    private String id;
    private String idModoPagamento;
    private String porcentagemDesconto;
    private String valorPagamento;
    private String idVenda;
    private String quantidadeParcela;
    private String dataPagamento ;
    private String troco;
    private String modoPagamentoDescricao;
    
}
