package com.pdv.dto;

public class RelatorioCaixaDTO {

	private String id_venda;
	private String valor_pagamento;
	private String quantida_parcela;
	private String descricao;

	public String getId_venda() {
		return id_venda;
	}

	public void setId_venda(String id_venda) {
		this.id_venda = id_venda;
	}

	public String getValor_pagamento() {
		return valor_pagamento;
	}

	public void setValor_pagamento(String valor_pagamento) {
		this.valor_pagamento = valor_pagamento;
	}

	public String getQuantida_parcela() {
		return quantida_parcela;
	}

	public void setQuantida_parcela(String quantida_parcela) {
		this.quantida_parcela = quantida_parcela;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
