package br.com.dextraining.entity;

public class VendaService {

	public void inserir(Venda venda) throws CPFInvalidoException, VendaInvalidaException {
		String cpf = venda.getCpfComprador();
		
		if (cpf.length() != 14) {
			throw new CPFInvalidoException(cpf);
		}
		
		if (venda.getItens().isEmpty()) {
			throw new VendaInvalidaException();
		}
		
		BancoDeDados.insert(venda);
	}
}
