/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Negocio;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author mateus
 */
public class NegocioDAO {

    private Session session;

    public NegocioDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public org.hibernate.Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Negocio i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        //session.close();
    }

    public void update(Negocio i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        //session.close();
    }

    public void delete(Negocio i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        //session.close();
    }

    public Negocio findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Negocio m = (Negocio) session.get(Negocio.class, id);
        //session.close();
        return m;
    }

    public List<Negocio> findAll(String tipo) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Negocio> ls = session.createQuery("from Negocio where neg_ctipo = " + tipo + "").list();
        //session.close();
        return ls;
    }

    public List<Negocio> findRange(int tipo, int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery(" from Negocio where neg_ctipo = " + tipo + " order by neg_vnome");
        q.setMaxResults(15);//(id2-(id+1));   total maximo de registros que o metodo pode retornar
        q.setFirstResult(id * 15);
        return q.list();
    }

   
}
