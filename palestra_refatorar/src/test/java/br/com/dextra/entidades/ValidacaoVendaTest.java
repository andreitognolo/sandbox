package br.com.dextra.entidades;

import org.junit.Test;

public class ValidacaoVendaTest {

	@Test
	public void testValidacaoVendaAVista() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_VISTA);
		
		// ...
	}
	
	@Test
	public void testValidarDatasParcelas() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_PRAZO);
		
		// ...
	}
	
	@Test
	public void testValidarItemDuplicado() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_VISTA);
		
		// ...
	}
}
