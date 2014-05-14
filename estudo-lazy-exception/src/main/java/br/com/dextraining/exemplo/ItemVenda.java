package br.com.dextraining.exemplo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ItemVenda {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nomeProduto;
	
	private Double valor;

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

	public Long getId() {
		return id;
	}
}
