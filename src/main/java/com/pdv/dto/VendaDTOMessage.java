package com.pdv.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTOMessage {

	private String dataCriacaoVenda ;
	private String dataFechamentoVenda ;
	private String finalizada;
	private String id;
	private String nomeComanda;
	private String subTotal;
	private String valorTotal;
	private List<PagamentoDTOMessage>pagamentos;
	private List<ItemVendaDTOMessage>itensVenda;
}
