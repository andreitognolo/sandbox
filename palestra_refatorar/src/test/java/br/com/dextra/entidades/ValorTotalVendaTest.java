package br.com.dextra.entidades;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class ValorTotalVendaTest {

	@Test
	public void testTotalVendaAVista() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_VISTA);
		venda.addItem(new ItemVenda("Camisa", 50.0));
		venda.addItem(new ItemVenda("Calca", 100.0));
		
		assertEquals(150.0, venda.calcularValorTotal(), 0.01);
	}
	
	@Test
	public void testTotalVendaAPrazo() {
		Venda venda = new Venda("111.111.111-11", TipoVenda.A_PRAZO);
		venda.addItem(new ItemVenda("Camisa", 50.0));
		venda.addItem(new ItemVenda("Calca", 100.0));
		
		venda.addParcela(new Parcela(40.0, primeiroDeJaneiro()));
		venda.addParcela(new Parcela(40.0, primeiroDeFevereiro()));
		venda.addParcela(new Parcela(40.0, primeiroDeMarco()));
		venda.addParcela(new Parcela(40.0, primeiroDeAbril()));
		
		Double juros = 1.10;
		assertEquals(150.0 * juros, venda.calcularValorTotal(), 0.01);
	}
	
	private Date primeiroDeJaneiro() {
		return data(1, Calendar.JANUARY, 2012);
	}
	
	private Date primeiroDeFevereiro() {
		return data(1, Calendar.FEBRUARY, 2012);
	}
	
	private Date primeiroDeMarco() {
		return data(1, Calendar.MARCH, 2012);
	}
	
	private Date primeiroDeAbril() {
		return data(1, Calendar.APRIL, 2012);
	}

	private Date data(int dia, int mes, int ano) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, ano);
		cal.set(Calendar.MONTH, mes);
		cal.set(Calendar.DAY_OF_MONTH, dia);
		return cal.getTime();
	}
}
