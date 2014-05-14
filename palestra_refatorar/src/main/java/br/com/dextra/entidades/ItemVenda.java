package br.com.dextra.entidades;


public class ItemVenda {

	private String produto;
	
	private Double valor;
	
	public ItemVenda(String produto, Double valor) {
		this.produto = produto;
		this.valor = valor;
	}

	public String getProduto() {
		return produto;
	}

	public Double getValor() {
		return valor;
	}
}
