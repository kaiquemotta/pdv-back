package com.pdv.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO {
	
    private Long id;
    private Long idModoPagamento;
    private Double porcentagemDesconto;
    private Double valorPagamento;
    private Long idVenda;
    private Integer quantidadeParcela;
    private LocalDateTime dataPagamento ;
    private Double troco;
    private String modoPagamentoDescricao;
    

}
