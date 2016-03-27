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
    public Participation loadCompetition(Participation participation) {
        em.merge(participation);
        participation.getCompetitionId();
        return participation;
    }
    
    
    
}
