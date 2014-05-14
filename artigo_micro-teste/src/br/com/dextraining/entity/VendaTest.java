package br.com.dextraining.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VendaTest {

	@Before
	public void preparacao() {
		// Exemplo: Configuracao de conexao com banco de dados
	}
	
	@After
	public void finalizacao() {
		// Exemplo: Finalizar conexao com banco de dados
	}
	
	@Test
	public void testTotalVenda() {
		// Cenario
		Venda venda = new Venda();
		venda.getItens().add(new ItemVenda("Camiseta", 50.0));
		venda.getItens().add(new ItemVenda("Cala", 100.0));
		
		// Chamada ao metodo que estamos testando
		Double totalVenda = venda.totalVenda();
		
		// Verificacao do resultado esperado
		Assert.assertEquals(new Double(165), totalVenda);
	}
	
	@Test
	public void testTotalVendaReescrito() {
		// Cenario
		Venda venda = new Venda();
		venda.getItens().add(new ItemVenda("Camiseta", 50.0));
		venda.getItens().add(new ItemVenda("Cala", 100.0));
		
		// Chamada ao metodo que estamos testando
		Double totalVenda = venda.totalVenda();
		
		// Verificacao do resultado esperado
		Double frete = (50.0 + 100.0) * 0.1;
		Double totalEsperado = 50 + 100 + frete;
		Assert.assertEquals(totalEsperado, totalVenda);
	}
}
