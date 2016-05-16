package com.netcracker.database.dal;

import com.netcracker.database.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User loadParticipations(User user) {
        em.merge(user).getParticipationList();
        return user;//исп.Антон
    }

    @Override
    public User loadParticipationResults(User user) {
        em.merge(user).getParticipationResultList();
        return user;
    }

    @Override
    public User loadSubmissions(User user) {
        em.merge(user).getSubmissionList();
        return user;
    }

    @Override
    public User find(Object id) {
        return super.find(id, "User.findById");
    }

    @Override
    public User findByLogin(String login) {
        TypedQuery query = em.createNamedQuery("User.findByLogin", User.class);
        query.setParameter("login", login);
        List<User> results = query.getResultList();
        if (results.isEmpty())
            return null;
        else
            return results.get(0);
    }

    @Override
    public List<User> getModerators() {
        TypedQuery query = em.createNamedQuery("User.findByRole", User.class);
        query.setParameter("role", "moderator");
        return query.getResultList();
    }

    @Override
    public List<User> getParticipants() {
        TypedQuery query = em.createNamedQuery("User.findByRole", User.class);
        query.setParameter("role", "participant");
        return query.getResultList();
    }

    @Override
    public User getAdmin() {
        TypedQuery query = em.createNamedQuery("User.findByRole", User.class);
        query.setParameter("role", "admin");
        List<User> results = query.getResultList();
        if (results.isEmpty())
            return null;
        else
            return results.get(0);
    }

    @Override
    public List<User> findAll() {
        TypedQuery query = em.createNamedQuery("User.findAll", User.class);
        return query.getResultList();
    }
    
    
    
}
