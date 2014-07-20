package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.devmedia.util.EntityManagerUtil;

public class ExemploMerge {

   public static void main(String[] args) {
      Pessoa p = new Pessoa();
      p.setNome("Andrei");

      EntityManager em1 = EntityManagerUtil.em();
      EntityTransaction tx1 = em1.getTransaction();
      tx1.begin();
      em1.persist(p);
      tx1.commit();
      em1.close();

      p.setNome("Andrei Tognolo");

      EntityManager em2 = EntityManagerUtil.em();
      EntityTransaction tx2 = em2.getTransaction();
      tx2.begin();
      System.out.println("Antes merge");
      em2.merge(p);
      System.out.println("Depois merge");
      System.out.println("Antes commit");
      tx2.commit();
      System.out.println("Depois commit");
      em2.close();

      EntityManagerUtil.closeEMF();
   }
}
