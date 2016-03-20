/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Competition;
import com.netcracker.entity.CompetitionProblem;
import com.netcracker.entity.Submission;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.inject.Inject;

/**
 *
 * @author Ataman
 */
@Stateless
public class SubmissionFacade extends AbstractFacade<Submission> implements SubmissionFacadeLocal {

    @EJB(beanName = "CompetitionProblemFacade")
    CompetitionProblemFacadeLocal competitionProblemFacade;
    
    @EJB(beanName = "CompetitionFacade")
    CompetitionFacadeLocal competitionFacade;
    
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
    public List<Submission> findByUserIdAndCompetitionProblemId(Object userId, Object competitionProblemId) {
        CompetitionProblem competiotionProblem = competitionProblemFacade.find(competitionProblemId);
        Date finish = competiotionProblem.getCompetitionId().getCompetitionStart();
        finish.setTime(finish.getTime() + competiotionProblem.getCompetitionId().getCompetitionInterval());
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionProblemId", 
                Submission.class);
        query.setParameter("competitionProblemId", competitionProblemId);
        query.setParameter("userId", userId);
        query.setParameter("start", competiotionProblem.getCompetitionId().getCompetitionStart());
        query.setParameter("finish", finish);
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

    @Override
    public List<Submission> getAllSubmissionsByCompetitionId(Object competitionId) {
        Competition competiotion = competitionFacade.find(competitionId);
        Date finish = competiotion.getCompetitionStart();
        finish.setTime(finish.getTime() + competiotion.getCompetitionInterval());
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionProblemId", 
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("start", competiotion.getCompetitionStart());
        query.setParameter("finish", finish);
        return query.getResultList();
    }
    
    
    
}
