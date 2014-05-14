package br.com.dextraining.entity;

public class ValidadorCPF {

	public boolean valido(String cpf) {
		return cpf.length() == 14;
	}
}
