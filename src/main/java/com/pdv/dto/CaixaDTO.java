package com.pdv.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdv.entities.Pagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaDTO {
	private Long id;
	private String nome;
	private Integer idUsuario;
	
	private Double valorAbertura;
	private Double valorFechamento;
	//
	private Double valorFechamentoDinheiro;
	private Double valorFechamentoPix;
	private Double valorFechamentoCartaoCredito;
	private Double valorFechamentoCartaoDebito;
	private Double valorFechamentoConsignado;
	//
	private Double diferencaDinheiro;
	private Double diferencaPix;
	private Double diferencaCartaoCredito;
	private Double diferencaCartaoDebito;
	private Double diferencaConsignado;
	//
	
	private Double valorPagamentoDinheiro;
	private Double valorPagamentoPix;
	private Double valorPagamentoCartaoCredito;
	private Double valorPagamentoCartaoDebito;
	private Double valorPagamentoConsignado;
	
	private String dataAbertura;
	private String dataFechamento;
	private boolean aberto;
	private List<PagamentoDTO>pagamentos;

}
