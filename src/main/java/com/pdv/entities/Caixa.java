package com.pdv.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_caixa")
public class Caixa {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double valorAbertura;
	private Double valorFechamento;
	private Double valorFechamentoAvista;
	private Double valorFechamentoCartao;
	private Double diferencaAvista;
	private Double diferencaCartao;
	private LocalDateTime dataAbertura;
	private LocalDateTime dataFechamento;
	private boolean aberto;
	
    @OneToMany(mappedBy = "caixa")
	private List<Pagamento> pagamentos;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValorAbertura() {
		return valorAbertura;
	}

	public void setValorAbertura(Double valorAbertura) {
		this.valorAbertura = valorAbertura;
	}

	public Double getValorFechamento() {
		return valorFechamento;
	}

	public void setValorFechamento(Double valorFechamento) {
		this.valorFechamento = valorFechamento;
	}
	
	

	public Double getValorFechamentoAvista() {
		return valorFechamentoAvista;
	}

	public void setValorFechamentoAvista(Double valorFechamentoAvista) {
		this.valorFechamentoAvista = valorFechamentoAvista;
	}

	public Double getValorFechamentoCartao() {
		return valorFechamentoCartao;
	}

	public void setValorFechamentoCartao(Double valorFechamentoCartao) {
		this.valorFechamentoCartao = valorFechamentoCartao;
	}

	public Double getDiferencaAvista() {
		return diferencaAvista;
	}

	public void setDiferencaAvista(Double diferencaAvista) {
		this.diferencaAvista = diferencaAvista;
	}

	public Double getDiferencaCartao() {
		return diferencaCartao;
	}

	public void setDiferencaCartao(Double diferencaCartao) {
		this.diferencaCartao = diferencaCartao;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	@JsonBackReference
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamento) {
		this.pagamentos = pagamento;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

}
