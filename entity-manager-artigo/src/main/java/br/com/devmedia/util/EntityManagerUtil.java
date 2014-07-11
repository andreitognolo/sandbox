package br.com.devmedia.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	private static EntityManagerFactory emf;
	
	public static EntityManager em() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("exemplo-1");
		}
		
		return emf.createEntityManager();
	}
}
