package br.com.dextraining.entity;

import org.junit.Assert;
import org.junit.Test;

public class ValidadorCPFTest {

	@Test
	public void testCPF() {
		ValidadorCPF validador = new ValidadorCPF();
		
		Assert.assertTrue(validador.valido("000.000.000-00"));
		Assert.assertFalse(validador.valido("000.000.000"));
	}
}
