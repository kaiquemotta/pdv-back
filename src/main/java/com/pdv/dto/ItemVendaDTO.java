package com.pdv.dto;

public class ItemVendaDTO {

	private long id;
	private long quantidade;
	private Double subTotal;
	private String produtoNome;
	private Double produtoPreco;
	private long idVenda;
	private long idProduto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public Double getProdutoPreco() {
		return produtoPreco;
	}

	public void setProdutoPreco(Double produtoPreco) {
		this.produtoPreco = produtoPreco;
	}

	public long getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(long idVenda) {
		this.idVenda = idVenda;
	}

	public long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

}
