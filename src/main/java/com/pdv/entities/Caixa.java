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
@Table(name = "tb_caixa")
public class Caixa {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Long idUsuario;
	
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
	
	private LocalDateTime dataAbertura;
	private LocalDateTime dataFechamento;
	private boolean aberto;
	@JsonIgnore
    @OneToMany(mappedBy = "caixa")
	private List<Pagamento> pagamentos;
	
}
