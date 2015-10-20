/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Pessoa;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class PessoaDAO {
     
    private Session session;
    
    public PessoaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Pessoa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Pessoa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Pessoa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Pessoa findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Pessoa m = (Pessoa) session.get(Pessoa.class, id);
        //session.close();
        return m;
    }

    public List<Pessoa> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Pessoa> ls = session.createQuery("from Pessoa").list();
        //session.close();
        return ls;
    }
}
