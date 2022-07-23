package com.pdv.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeComanda;
	@JsonIgnore
	@OneToMany(mappedBy = "venda")
	private List<ItemVenda> itensVemda;
	private Double subTotal;
	private Double valorTotal;
	private boolean finalizada;
	private LocalDateTime dataCriacaoVenda;
	private LocalDateTime dataFechamentoVenda;
	private Double porcentagemDesconto;
	@OneToMany(mappedBy = "venda")
	private List<Pagamento> pagamentos;

	@Override
	public String toString() {
		return "Venda [id=" + id + ", nomeComanda=" + nomeComanda + ", itensVemda=" + itensVemda + ", subTotal="
				+ subTotal + ", valorTotal=" + valorTotal + ", finalizada=" + finalizada + ", dataCriacaoVenda="
				+ dataCriacaoVenda + ", dataFechamentoVenda=" + dataFechamentoVenda + "]";
	}

}
