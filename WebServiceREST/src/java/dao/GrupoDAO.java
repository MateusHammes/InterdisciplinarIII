/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Grupo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class GrupoDAO {

    private Session session;

    public GrupoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Grupo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Grupo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Grupo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Grupo findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Grupo m = (Grupo) session.get(Grupo.class, id);
        //session.close();
        return m;
    }

    public List<Grupo> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Grupo> ls = session.createQuery(" from Grupo order by gru_vdescricao").list();
        //session.close();
        return ls;
    }

    public List<Grupo> findRange(int id, int id2) {
        session = HibernateUtil.getSessionFactory().openSession();

        //List<Grupo> ls
        Query q = session.createQuery(" from Grupo order by gru_vdescricao");
        q.setMaxResults(id2-(id+1));
        q.setFirstResult(id);
        //q.list();
        //session.close();
        return q.list();
    }

}
