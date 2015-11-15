/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Materiais;
import model.Produto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class ProdutoDAO {

    private Session session;

    public ProdutoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Produto findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Produto m = (Produto) session.get(Produto.class, id);
        //session.close();
        return m;
    }

    //id -> id do Negocio
    public List<Produto> findAll(int negocio) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Produto> ls = session.createQuery("select p from Produto p "
                + "left outer join p.negocio n "
                + "where n.neg_codigo = :id")
                .setParameter("id", negocio).list();
        //session.close();
        return ls;
    }

}
