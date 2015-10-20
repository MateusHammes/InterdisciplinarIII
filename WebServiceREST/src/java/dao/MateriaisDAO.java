/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Materiais;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class MateriaisDAO {
    private Session session;
    
    public MateriaisDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Materiais i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Materiais i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Materiais i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Materiais findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Materiais m = (Materiais) session.get(Materiais.class, id);
        //session.close();
        return m;
    }

    public List<Materiais> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Materiais> ls = session.createQuery("from Materiais").list();
        //session.close();
        return ls;
    }
}
