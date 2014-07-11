package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.devmedia.util.EntityManagerUtil;

public class Exemplo {

	public static void main(String[] args) {
		Pessoa p = new Pessoa();
		p.setNome("Andrei Tognolo");
		p.setId(10l);
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		em.close();
		
		em = EntityManagerUtil.em();
		//System.out.println(em.createQuery("FROM Pessoa").getResultList().size());
		tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		em.close();
	}
}
