package br.com.dextraining.entity;

import org.junit.Test;

public class VendaServiceTest {

	@Test
	public void testVenda() throws CPFInvalidoException, VendaInvalidaException {
		Venda venda = new Venda();
		venda.setCpfComprador("368.395.088-97");
		venda.getItens().add(new ItemVenda("Calca", 50.0));

        // Sem assert, pois se o cpf for invalido, teremos uma exception, 
        // sinalizando a quebra do teste
		new VendaService().inserir(venda);
	}
}
