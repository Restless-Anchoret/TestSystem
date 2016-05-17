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
        CompetitionProblem competitionProblem = competitionProblemFacade.find(competitionProblemId);
        Date finish = getFinishCompetition(competitionProblem.getCompetitionId());
        TypedQuery query = em.createNamedQuery("Submission.findAllByUserIdAndCompetitionProblemId", 
                Submission.class);
        query.setParameter("competitionProblemId", competitionProblemId);
        query.setParameter("userId", userId);
        query.setParameter("start", competitionProblem.getCompetitionId().getCompetitionStart());
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
        Competition competition = competitionFacade.find(competitionId);
        Date finish = getFinishCompetition(competition);
        TypedQuery query = em.createNamedQuery("Submission.findAllSubmissionsByCompetitionId", 
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("start", competition.getCompetitionStart());
        query.setParameter("finish", finish);
        return query.getResultList();
    }

    @Override
    public List<Submission> findAllSubmissionsByUserIdAndCompetitionId(Object userId, Object competitionId) {
        Competition competition = competitionFacade.find(competitionId);
        Date finish = getFinishCompetition(competition);
        TypedQuery query = em.createNamedQuery("Submission.findAllSubmissionsByUserIdAndCompetitionId", 
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("userId", userId);
        query.setParameter("start", competition.getCompetitionStart());
        query.setParameter("finish", finish);
        List<Submission> result = query.getResultList();
        for (Submission submission: result)
            submission.getCompetitionProblemId();
        return result;
    }
    
    private Date getFinishCompetition(Competition competition) {
        Date result = new Date(competition.getCompetitionStart().getTime());
        result.setTime(result.getTime() + 
                competition.getCompetitionInterval() * 60000);
        return result;
    }

    @Override
    public Submission loadUserAndCompetitionProblemAndCompetition(Submission submission) {
        submission = em.merge(submission);
        submission.getCompetitionProblemId().getCompetitionId();
        submission.getUserId();
        return submission;
    }

    @Override
    public List<Submission> findAllSubmissionsByUserIdAndCompetitionIdOutCompetition(Object userId, Object competitionId) {
        Competition competition = competitionFacade.find(competitionId);
        Date finish = getFinishCompetition(competition);
        TypedQuery query = em.createNamedQuery("Submission.findAllSubmissionsByUserIdAndCompetitionIdOutCompetition",
                Submission.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("userId", userId);
        query.setParameter("finish", finish);
        query.setParameter("start", competition.getCompetitionStart());
        List<Submission> result = query.getResultList();
        for (Submission submission : result) {
            submission.getCompetitionProblemId();
        }
        return result;
    }
    
    @Override
    public List<Submission> findAll() {
        TypedQuery query = em.createNamedQuery("Submission.findAll", Submission.class);
        return query.getResultList();
    }

    @Override
    public List<Submission> findAllSubmissionsByUserId(Object userId) {
        TypedQuery query = em.createNamedQuery("Submission.findAllSubmissionsByUserId",
                Submission.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
}
