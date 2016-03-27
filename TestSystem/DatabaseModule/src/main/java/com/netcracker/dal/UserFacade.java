/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ataman
 */
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
        return user;
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
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getModerators() {
        TypedQuery query = em.createNamedQuery("User.findByRole", User.class);
        query.setParameter("role", "moderator");
        return query.getResultList();
    }
    
    
    
}
