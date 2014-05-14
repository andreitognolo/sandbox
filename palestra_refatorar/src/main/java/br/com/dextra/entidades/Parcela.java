package br.com.dextra.entidades;

import java.util.Date;

public class Parcela {

	private Date dataVencimento;
	
	private Double valor;

	public Parcela(Double valor, Date dataVencimento) {
		this.dataVencimento = dataVencimento;
		this.valor = valor;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public Double getValor() {
		return valor;
	}
}
