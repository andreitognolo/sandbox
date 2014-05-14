package br.com.dextraining.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerUtil {

	private static EntityManagerFactory emf;
	
	public static EntityManager em() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("exemplo-1");
			EntityManager em = getEmf().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			Query query = em.createNativeQuery("CREATE SCHEMA TE");
			query.executeUpdate();
			tx.commit();
			
			em = getEmf().createEntityManager();
			tx = em.getTransaction();
			
			tx.begin();
			query = em.createNativeQuery("GRANT ALL ON TE TO PUBLIC");
			query.executeUpdate();
			tx.commit();
		}
		
		return emf.createEntityManager();
	}

	private static EntityManagerFactory getEmf() {
		return emf;
	}
}
