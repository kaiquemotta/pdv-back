package com.pdv.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTO {
	
	private LocalDateTime dataCriacaoVenda ;
	private LocalDateTime dataFechamentoVenda ;
	private boolean finalizada;
	private Long id;
	private String nomeComanda;
	private Double subTotal;
	private Double valorTotal;
	private List<PagamentoDTO>pagamentos;
	private List<ItemVendaDTO>itensVenda;
}
