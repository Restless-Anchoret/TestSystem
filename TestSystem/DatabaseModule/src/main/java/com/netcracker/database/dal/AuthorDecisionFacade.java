package com.netcracker.database.dal;

import com.netcracker.database.entity.AuthorDecision;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
