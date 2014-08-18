package br.com.devmedia.exemplo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.devmedia.util.EntityManagerUtil;

public class ExemploFind {

   public static void main(String[] args) {
      Pessoa p = new Pessoa();
      p.setNome("Andrei");

      EntityManager em1 = EntityManagerUtil.em();
      EntityTransaction tx1 = em1.getTransaction();
      tx1.begin();
      em1.persist(p);
      tx1.commit();
      em1.close();

      EntityManager em2 = EntityManagerUtil.em();
      EntityTransaction tx2 = em2.getTransaction();
      tx2.begin();

      Pessoa pessoaPersistida = em2.find(Pessoa.class, p.getId());
      pessoaPersistida.setNome("Andrei Tognolo");

      System.out.println("Antes commmit");
      tx2.commit();
      System.out.println("Depois commmit");
      em2.close();

      EntityManagerUtil.closeEMF();
   }
}
