package br.com.dextraining.exemplo;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hsqldb.util.DatabaseManagerSwing;

import br.com.dextraining.util.EntityManagerUtil;

public class ExemploResolvido {

	public static void main(String args[]) {
		criarVendasNoBanco();

		List<Venda> vendas = consultarVendas(VendaResolveLazy.ITENS);

		for (Venda venda : vendas) {
			System.out.println("B");
			int numeroItens = venda.getItens().size();
			System.out.println("Venda " + venda.getId() + " tem " + numeroItens + " itens");
		}
		
		DatabaseManagerSwing.main(new String[0]);
	}

	@SuppressWarnings("unchecked")
	private static List<Venda> consultarVendas(VendaResolveLazy ... resolveLazys) {
		EntityManager em = EntityManagerUtil.em();

		try {
			String jpql = "FROM Venda v";
			
			if (Arrays.asList(resolveLazys).contains(VendaResolveLazy.ITENS)) {
				jpql = jpql + " JOIN FETCH v.itens";
			}
			
			List<Venda> vendas = em.createQuery(jpql).getResultList();
			return vendas;
		} finally {
			em.close();
		}
	}

	private static void criarVendasNoBanco() {
		ItemVenda item1 = new ItemVenda();
		item1.setNomeProduto("Produto A");
		item1.setValor(10.0);

		ItemVenda item2 = new ItemVenda();
		item2.setNomeProduto("Produto B");
		item2.setValor(20.0);

		Venda venda = new Venda();
		venda.addItem(item1);
		venda.addItem(item2);

		ItemVenda item3 = new ItemVenda();
		item3.setNomeProduto("Produto C");
		item3.setValor(30.0);

		Venda venda2 = new Venda();
		venda2.addItem(item3);

		EntityManager em = EntityManagerUtil.em();

		// Nota: nao existe tratamento de exceptions para nao complicar o codigo
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(venda);
		em.persist(venda2);
		tx.commit();
	}
}
