/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Registros;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class RegistrosDAO {
    private Session session;
    
    public RegistrosDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Registros i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Registros i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Registros i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Registros findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Registros m = (Registros) session.get(Registros.class, id);
        //session.close();
        return m;
    }

    public List<Registros> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Registros> ls = session.createQuery("from Registros").list();
        //session.close();
        return ls;
    }
}
