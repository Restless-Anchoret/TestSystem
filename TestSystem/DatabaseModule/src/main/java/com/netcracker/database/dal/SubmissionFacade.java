package com.netcracker.database.dal;

import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Submission;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        Date finish = new Date(competiotionProblem.getCompetitionId().getCompetitionStart().getTime());
        finish.setTime(finish.getTime() + competiotionProblem.getCompetitionId().getCompetitionInterval() * 60000);
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
    public List<Submission> findAllSubmissionsByCompetitionId(Object competitionId) {
        Competition competiotion = competitionFacade.find(competitionId);
        Date finish = new Date(competiotion.getCompetitionStart().getTime());
        finish.setTime(finish.getTime() + competiotion.getCompetitionInterval() * 60000);
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionProblemId", 
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("start", competiotion.getCompetitionStart());
        query.setParameter("finish", finish);
        return query.getResultList();
    }

    @Override
    public List<Submission> findAllSubmissionsByUserIdAndCompetitionId(Object userId, Object competitionId) {
        Competition competiotion = competitionFacade.find(competitionId);
        Date finish = new Date(competiotion.getCompetitionStart().getTime());
        finish.setTime(finish.getTime() + competiotion.getCompetitionInterval() * 60000);
        TypedQuery query = em.createNamedQuery("Submission.findAllSubmissionsByUserIdAndCompetitionId", 
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("userId", userId);
        query.setParameter("start", competiotion.getCompetitionStart());
        query.setParameter("finish", finish);
        List<Submission> result = query.getResultList();
        for (Submission submission: result)
            submission.getCompetitionProblemId();
        return result;
    }
    
    
    
}
