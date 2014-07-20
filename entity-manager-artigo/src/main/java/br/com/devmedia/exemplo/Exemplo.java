package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hsqldb.util.DatabaseManagerSwing;

import br.com.devmedia.util.EntityManagerUtil;

public class Exemplo {

	public static void main(String[] args) {
		Pessoa p = new Pessoa();
		p.setNome("Andrei");
		p.setId(10l);

		EntityManager em1 = EntityManagerUtil.em();
		EntityTransaction tx1 = em1.getTransaction();
		tx1.begin();
		em1.persist(p);
		tx1.commit();
		em1.close();
		
		p.setNome("Andrei Tognolo");
		
		DatabaseManagerSwing.main(new String[0]);
		
		EntityManager em2 = EntityManagerUtil.em();
		EntityTransaction tx2 = em2.getTransaction();
		tx2.begin();
		System.out.println("Antes de executar o merge");
		em2.merge(p);
		System.out.println("aaaaa");
		em2.merge(p);
		System.out.println("Depois de executar o merge");
		tx2.commit();
		System.out.println("Apos executar commit");
		em2.close();
	}
}
