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
    public List<Submission> findByUserIdAndCompetitionId(int userId, int competitionId, int[] range) {
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionId", 
                Submission.class);
        query.setParameter("competitionProblemId", competitionId);
        query.setParameter("userId", userId);
        query.setFirstResult(range[0]).setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }
    
}
