/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.ProdutoMaterial;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


public class ProdutoMaterialDAO {
    private Session session;
    
    public ProdutoMaterialDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

     public void update(ProdutoMaterial i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }
     
      public void delete(ProdutoMaterial i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }
    
}
