/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Competition;
import com.netcracker.entity.CompetitionProblem;
import com.netcracker.entity.Participation;
import com.netcracker.entity.ParticipationResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
public class CompetitionFacade extends AbstractFacade<Competition> implements CompetitionFacadeLocal {

    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompetitionFacade() {
        super(Competition.class);
    }

    @Override
    public List<Competition> findVisible(int[] range) {
        TypedQuery query = em.createNamedQuery("Competition.findByVisible", Competition.class);
        query.setParameter("visible", true);
        query.setFirstResult(range[0]).setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }

    @Override
    public List<Competition> findAll(int[] range) {
        TypedQuery query = em.createNamedQuery("Competition.findAll", Competition.class);
        query.setFirstResult(range[0]).setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }

    @Override
    public List<CompetitionProblem> getCompetitionProblems(Competition competition) {
        return em.merge(competition).getCompetitionProblemList();
    }

    @Override
    public List<ParticipationResult> createNullsResults(Competition competition) {
        em.merge(competition);
        List<ParticipationResult> participationResults = new ArrayList<>();
        ParticipationResult participationResult;
        for (Participation participation: competition.getParticipationList()) {
            for (CompetitionProblem competitionProblem: competition.getCompetitionProblemList()) {
                participationResult = new ParticipationResult();
                participationResult.setUserId(participation.getUserId());
                participationResult.setCompetitionProblemId(competitionProblem);
                em.persist(participationResult);
                participationResults.add(participationResult);
            }
        }
        return participationResults;
    }
    
}
