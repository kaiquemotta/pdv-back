package com.pdv.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "modo_pagamento_id", referencedColumnName = "id")
	private ModoPagamento modoPagamento;

	private Double porcentagemDesconto;
	private Double valorPagamento;
	@ManyToOne
	@JoinColumn(name = "venda_id")
	private Venda venda;
	private Integer quantidadeParcela;
	private LocalDateTime dataPagamento;
	private Double troco;
	private Long idVenda;
	@ManyToOne
	private Caixa caixa;

}
