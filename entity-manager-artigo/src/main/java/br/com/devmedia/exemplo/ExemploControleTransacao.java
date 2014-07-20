package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.devmedia.util.EntityManagerUtil;

public class ExemploControleTransacao {

   public static void main(String[] args) {
      EntityManager em = EntityManagerUtil.em();
      EntityTransaction tx = null;

      try {
         tx = em.getTransaction();
         tx.begin();

         Pessoa pessoa = new Pessoa();
         pessoa.setNome("Andrei");  

         em.persist(pessoa);

         tx.commit();
      } catch (RuntimeException e) {
         if (tx != null && tx.isActive()) {
            tx.rollback();
         }
      } finally {
         em.close();
      }

      EntityManagerUtil.closeEMF();
   }
}
