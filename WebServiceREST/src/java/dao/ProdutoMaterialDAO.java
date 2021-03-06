/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
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

    public void insert(ProdutoMaterial i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
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

    //id= id do produto
    public List<ProdutoMaterial> findAll(int pro_id, int mtr_id) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (pro_id != 0) {
            sql += " and pro_codigo = " + pro_id + "";
        }

        if (mtr_id != 0) {
            sql += " and mtr_codigo = " + mtr_id + "";
        }

        return session.createQuery("from ProdutoMaterial where 1 = 1 " + sql).list();
    }

    public ProdutoMaterial findById(int pro_id, int mtr_id) {
        session = HibernateUtil.getSessionFactory().openSession();
     
        ProdutoMaterial p = (ProdutoMaterial) session.createQuery("from ProdutoMaterial "
                    + " where pro_codigo = "+pro_id+" "
                    + " and mtr_codigo = "+mtr_id+"").uniqueResult();
                    
        
        return p;
    }

    public List<ProdutoMaterial> findByProdutos(String produtos) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoMaterial> ls = session.createQuery("from ProdutoMaterial where pro_codigo in ("+produtos+")").list();
        
        return ls;
    }

}
