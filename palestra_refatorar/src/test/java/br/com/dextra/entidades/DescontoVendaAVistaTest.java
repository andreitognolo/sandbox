package br.com.dextra.entidades;

import org.junit.Test;

public class DescontoVendaAVistaTest {

	@Test
	public void testVendaAVistaSemDesconto() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_VISTA);
		
		// ...
	}
	
	@Test
	public void testVendaAVistaComDesconto() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_VISTA);
		
		// ...
	}
}
