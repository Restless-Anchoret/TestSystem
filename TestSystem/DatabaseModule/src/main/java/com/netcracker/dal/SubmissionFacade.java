/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Submission;
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
public class SubmissionFacade extends AbstractFacade<Submission> implements SubmissionFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubmissionFacade() {
        super(Submission.class);
    }

    @Override
    public List<Submission> findByUserIdAndCompetitionId(int userId, int competitionId) {
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionId", 
                Submission.class);
        query.setParameter("competitionProblemId", competitionId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Submission find(Object id) {
        return super.find(id, "Submission.findById");
    }

    @Override
    public Submission loadCompetitionProblem(Submission submission) {
        em.merge(submission).getCompetitionProblemId();
        return submission;
    }

    @Override
    public Submission loadUser(Submission submission) {
        em.merge(submission).getUserId();
        return submission;
    }
    
    
    
}
