package br.com.dextraining.entity;

public class CPFInvalidoException extends Exception {

	private static final long serialVersionUID = 4872758103364624643L;
	
	private String cpf;

	public CPFInvalidoException(String cpf) {
		this.cpf = cpf;
	}

	public String toString() {
		return "CPFInvalidoException [cpf=" + cpf + "]";
	}
	
}
