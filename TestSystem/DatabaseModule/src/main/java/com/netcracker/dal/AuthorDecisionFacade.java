/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.AuthorDecision;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ataman
 */
@Stateless
public class AuthorDecisionFacade extends AbstractFacade<AuthorDecision> implements AuthorDecisionFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorDecisionFacade() {
        super(AuthorDecision.class);
    }

    @Override
    public AuthorDecision find(Object id) {
        return super.find(id, "AuthorDecision.findById");
    }

    @Override
    public AuthorDecision loadCompilator(AuthorDecision authorDecision) {
        em.merge(authorDecision).getCompilatorId();
        return authorDecision;
    }
    
    
    
}
