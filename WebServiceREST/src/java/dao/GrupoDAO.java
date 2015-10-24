
package dao;

import java.util.List;
import model.Grupo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


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
    
    ///teste 1
    public List<Grupo> findRange(int id, int id2) {
        session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery(" from Grupo order by gru_vdescricao");
        q.setMaxResults(15);//(id2-(id+1));   total maximo de registros que o metodo pode retornar
        q.setFirstResult(id);      
        return q.list();
    }

    public List<Grupo> findRange(int id, int id2) {
        session = HibernateUtil.getSessionFactory().openSession();

        //List<Grupo> ls
        Query q = session.createQuery(" from Grupo order by gru_vdescricao");
        q.setMaxResults(15);//(id2-(id+1));   total maximo de registros que o metodo pode retornar
        q.setFirstResult(id);
        //q.list();
        //session.close();
        
        return q.list();
    }

}
