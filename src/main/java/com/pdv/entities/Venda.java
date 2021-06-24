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
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeComanda() {
		return nomeComanda;
	}

	public void setNomeComanda(String nomeComanda) {
		this.nomeComanda = nomeComanda;
	}

	public List<ItemVenda> getItensVemda() {
		return itensVemda;
	}

	public void setItensVemda(List<ItemVenda> itensVemda) {
		this.itensVemda = itensVemda;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDateTime getDataCriacaoVenda() {
		return dataCriacaoVenda;
	}

	public void setDataCriacaoVenda(LocalDateTime dataCriacaoVenda) {
		this.dataCriacaoVenda = dataCriacaoVenda;
	}

	public LocalDateTime getDataFechamentoVenda() {
		return dataFechamentoVenda;
	}

	public void setDataFechamentoVenda(LocalDateTime dataFechamentoVenda) {
		this.dataFechamentoVenda = dataFechamentoVenda;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", nomeComanda=" + nomeComanda + ", itensVemda=" + itensVemda + ", subTotal="
				+ subTotal + ", valorTotal=" + valorTotal + ", finalizada=" + finalizada + ", dataCriacaoVenda="
				+ dataCriacaoVenda + ", dataFechamentoVenda=" + dataFechamentoVenda + "]";
	}


	

}
