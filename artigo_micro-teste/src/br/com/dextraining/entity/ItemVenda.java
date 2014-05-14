package br.com.dextraining.entity;

public class ItemVenda {

	private String nomeProduto;
	
	private Double valor;

	public ItemVenda(String nomeProduto, Double valor) {
		this.nomeProduto = nomeProduto;
		this.valor = valor;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
