package com.pdv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaDTOMessage {
	private String id;
	private String quantidade;
	private String subTotal;
	private String produtoNome;
	private String produtoPreco;
	private String idVenda;
	private String idProduto;
}
