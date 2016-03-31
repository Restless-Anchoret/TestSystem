package com.netcracker.database.dal;

import com.netcracker.database.entity.ParticipationResult;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ParticipationResultFacade extends AbstractFacade<ParticipationResult> implements ParticipationResultFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipationResultFacade() {
        super(ParticipationResult.class);
    }

    @Override
    public List<ParticipationResult> findByCompetitionId(Object competitionId) {
        TypedQuery query = em.createNamedQuery("ParticipationResult.findByCompetitionId", ParticipationResult.class);
        query.setParameter("competitionId", competitionId);
        return query.getResultList();
    }

    @Override
    public List<ParticipationResult> findByCompetitionIdAndUserId(Object competitionId, Object userId) {
        TypedQuery query = em.createNamedQuery("ParticipationResult.findByCompetitionId", ParticipationResult.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public ParticipationResult find(Object id) {
        return super.find(id, "ParticipationResult.findById");
    }

    @Override
    public ParticipationResult loadCompetitionProblem(ParticipationResult participationResult) {
        em.merge(participationResult).getCompetitionProblemId();
        return participationResult;
    }

    @Override
    public ParticipationResult loadUser(ParticipationResult participationResult) {
        em.merge(participationResult).getUserId();
        return participationResult;
    }
    
    
    
}
