package br.com.dextra.entidades;

import java.util.ArrayList;
import java.util.List;


public class Venda {
	
	private String cpfCliente;
	
	private List<ItemVenda> itens = new ArrayList<ItemVenda>();
	
	private List<Parcela> parcelas = new ArrayList<Parcela>();

	private TipoVenda tipo;
	
	public Venda(String cpfCliente, TipoVenda tipo) {
		this.cpfCliente = cpfCliente;
		this.tipo = tipo;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

	public TipoVenda getTipo() {
		return tipo;
	}

	public void addItem(ItemVenda itemVenda) {
		this.itens.add(itemVenda);
	}

	public Double calcularValorTotal() {
		Double total = 0.0;
		
		for (ItemVenda item : itens) {
			total += item.getValor();
		}
		
		if (tipo == TipoVenda.A_PRAZO) {
			Double juros;
			
			int quantidadeParcelas = parcelas.size();
			if (quantidadeParcelas <= 3) {
				juros = 0.0;
			} else if (quantidadeParcelas <= 6) {
				juros = 1.1;
			} else {
				juros = 1.15;
			}
			
			total = total * juros;
		}
		
		return total;
	}

	public void addParcela(Parcela parcela) {
		this.parcelas.add(parcela);
	}
}
