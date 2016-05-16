package com.netcracker.database.dal;

import com.netcracker.database.entity.CompetitionProblem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CompetitionProblemFacade extends AbstractFacade<CompetitionProblem> implements CompetitionProblemFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompetitionProblemFacade() {
        super(CompetitionProblem.class);
    }

    @Override
    public CompetitionProblem find(Object id) {
        return super.find(id, "CompetitionProblem.findById");
    }

    @Override
    public CompetitionProblem loadParticipationResults(CompetitionProblem competitionProblem) {
        em.merge(competitionProblem).getParticipationResultList();
        return competitionProblem;
    }

    @Override
    public CompetitionProblem loadSubmissionLists(CompetitionProblem competitionProblem) {
        em.merge(competitionProblem).getSubmissionList();
        return competitionProblem;
    }

    @Override
    public CompetitionProblem loadCompetition(CompetitionProblem competitionProblem) {
        em.merge(competitionProblem).getCompetitionId();
        return competitionProblem;
    }

    @Override
    public List<CompetitionProblem> findByCompetitionId(Object competitionId) {
        TypedQuery query = em.createNamedQuery("CompetitionProblem.findByCompetitionId",
                CompetitionProblem.class);
        query.setParameter("competitionId", competitionId);
        return query.getResultList();
    }
    
    
    
}
