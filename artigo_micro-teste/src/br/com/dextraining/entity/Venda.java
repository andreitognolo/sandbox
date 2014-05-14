package br.com.dextraining.entity;

import java.util.ArrayList;
import java.util.List;

public class Venda {

	private String nomeComprador;
	
	private String cpfComprador;
	
	private List<ItemVenda> itens = new ArrayList<ItemVenda>();

	public Double totalVenda() {
		Double total = 0.0;
		for (ItemVenda item : itens) {
			total += item.getValor();
		}
		
		Double frete = total * 0.1;
		total += frete;
		
		return total;
	}
	
	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public String getCpfComprador() {
		return cpfComprador;
	}

	public void setCpfComprador(String cpfComprador) {
		this.cpfComprador = cpfComprador;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
}
