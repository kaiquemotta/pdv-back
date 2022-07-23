package com.pdv.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
}
