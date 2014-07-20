package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.devmedia.util.EntityManagerUtil;

public class ExemploUnicidadeIdentificador {

	public static void main(String[] args) {
		Long id = inserirPessoa();
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		System.out.println("Antes de executar primeiro find");
		Pessoa p1 = em.find(Pessoa.class, id);
		System.out.println("Antes de executar segundo find");
		Pessoa p2 = em.find(Pessoa.class, id);
		
		System.out.println("p1 == p2 ? " + (p1 == p2));
		
		tx.commit();
		em.close();
		
		EntityManagerUtil.closeEMF();
	}

	private static Long inserirPessoa() {
		Pessoa p = new Pessoa();
		p.setNome("Andrei");
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		Long id = p.getId();
		tx.commit();
		em.close();
		return id;
	}
}
