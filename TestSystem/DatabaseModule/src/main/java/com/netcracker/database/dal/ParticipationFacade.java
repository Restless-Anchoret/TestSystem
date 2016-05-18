package com.netcracker.database.dal;

import com.netcracker.database.entity.Participation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ParticipationFacade extends AbstractFacade<Participation> implements ParticipationFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipationFacade() {
        super(Participation.class);
    }

    @Override
    public Participation find(Object id) {
        return super.find(id, "Participation.findById");
    }

    @Override
    public void edit(List<Participation> participations) {
        for (Participation participation: participations)
            em.merge(participation);
    }

    @Override
    public Participation findByCompetitionIdAndUserId(Object competitionId, Object userId) {
        TypedQuery query = em.createNamedQuery("Participation.findByCompetitionIdAndUserId",
                Participation.class);
        query.setParameter("competitionId", competitionId);
        query.setParameter("userId", userId);
        List<Participation> results = query.getResultList();
        if (results.isEmpty())
            return null;
        else
            return results.get(0);
    }

    @Override
    public List<Participation> findByCompetitionId(Object competitionId) {
        TypedQuery query = em.createNamedQuery("Participation.findByCompetitionId",
                Participation.class);
        query.setParameter("competitionId", competitionId);
        return query.getResultList();
    }

    @Override
    public List<Participation> findByUserId(Object userId) {
        TypedQuery query = em.createNamedQuery("Participation.findByUserId", Participation.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
}
