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
import com.netcracker.entity.PersonalData;
import com.netcracker.entity.User;
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
    
    private List<Competition> findVisibleByHoldCompetitions(boolean holdCompetition, int[] range) {
        TypedQuery query = em.createNamedQuery("Competition.findByVisibleAndHoldCompetition", Competition.class);
        query.setParameter("visible", true);
        query.setParameter("holdCompetition", holdCompetition);
        query.setFirstResult(range[0]).setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }
    
    private List<Competition> findByHoldCompetitions(boolean holdCompetition, int[] range) {
        TypedQuery query = em.createNamedQuery("Competition.findByHoldCompetition", Competition.class);
        query.setParameter("holdCompetition", holdCompetition);
        query.setFirstResult(range[0]).setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }
    
    @Override
    public List<Competition> findVisibleCompetiotions(int[] range) {
        return findVisibleByHoldCompetitions(true, range);
    }

    @Override
    public List<Competition> findAllCompetiotions(int[] range) {
        return findByHoldCompetitions(true, range);
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

    @Override
    public List<Competition> findAllTranings(int[] range) {
        return findByHoldCompetitions(false, range);
    }

    @Override
    public List<Competition> findVisibleTranings(int[] range) {
        return findVisibleByHoldCompetitions(false, range);
    }

    @Override
    public Competition find(Object id) {
        return super.find(id, "Competition.findById");
    }

    @Override
    public void finishedCompetition(Competition competition, List<ParticipationResult> participationResults) {
        em.merge(competition).setFinished(true);
        for (ParticipationResult participationResult: participationResults)
            em.merge(participationResult);
    }

    @Override
    public Competition loadCompetitionProblems(Competition competition) {
        em.merge(competition).getCompetitionProblemList();
        return competition;
    }

    @Override
    public Competition loadParticipations(Competition competition) {
        em.merge(competition).getParticipationList();
        return competition;
    }

    @Override
    public void registrationNewParticipation(Competition competition, User user, PersonalData personalData) {
        Participation participation = new Participation();
        participation.setPersonalDataId(personalData);
        participation.setRegistered(false);
        participation.setUserId(user);
        em.merge(competition).getParticipationList().add(participation);
    }

    @Override
    public void registrationNewParticipation(Competition competition, User user) {
        Participation participation = new Participation();
        participation.setRegistered(true);
        participation.setUserId(user);
        em.merge(competition).getParticipationList().add(participation);
    }
    
    
    
}
