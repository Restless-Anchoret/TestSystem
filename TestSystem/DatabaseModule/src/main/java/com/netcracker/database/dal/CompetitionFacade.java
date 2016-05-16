package com.netcracker.database.dal;

import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.ParticipationResult;
import com.netcracker.database.entity.PersonalData;
import com.netcracker.database.entity.User;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CompetitionFacade extends AbstractFacade<Competition> implements CompetitionFacadeLocal {

    @EJB(beanName = "ParticipationFacade")
    private ParticipationFacadeLocal participationFacade;
    
    @EJB(beanName = "CompetitionProblemFacade")
    private CompetitionProblemFacadeLocal competitionProblemFacadeFacade;
    
    @PersistenceContext(unitName = "com.netcracker_DatabaseModule_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompetitionFacade() {
        super(Competition.class);
    }
    
    private List<Competition> findVisibleByHoldCompetitions(boolean holdCompetition) {
        TypedQuery query = em.createNamedQuery("Competition.findByVisibleAndHoldCompetition", Competition.class);
        query.setParameter("visible", true);
        query.setParameter("holdCompetition", holdCompetition);
        return query.getResultList();
    }
    
    private List<Competition> findByHoldCompetitions(boolean holdCompetition) {
        TypedQuery query = em.createNamedQuery("Competition.findByHoldCompetition", Competition.class);
        query.setParameter("holdCompetition", holdCompetition);
        return query.getResultList();
    }
    
    @Override
    public List<Competition> findVisibleCompetiotions() {
        return findVisibleByHoldCompetitions(true);
    }

    @Override
    public List<Competition> findAllCompetiotions() {
        return findByHoldCompetitions(true);
    }

    @Override
    public List<ParticipationResult> createNullsResults(Competition competition) {
        List<ParticipationResult> participationResults = new ArrayList<>();
        ParticipationResult participationResult;
        List<Participation> participations = participationFacade.findByCompetitionId(competition.getId());
        List<CompetitionProblem> competitionProblems = competitionProblemFacadeFacade.
                findByCompetitionId(competition.getId());
        for (Participation participation: participations) {
            if (participation.getRegistered()) {
                for (CompetitionProblem competitionProblem: competitionProblems) {
                    participationResult = new ParticipationResult();
                    participationResult.setUserId(participation.getUserId());
                    participationResult.setCompetitionProblemId(competitionProblem);
                    em.persist(participationResult);
                    participationResults.add(participationResult);
                }
            }
        }
        return participationResults;
    }

    @Override
    public List<Competition> findAllTrainings() {
        return findByHoldCompetitions(false);
    }

    @Override
    public List<Competition> findVisibleTranings() {
        return findVisibleByHoldCompetitions(false);
    }

    @Override
    public Competition find(Object id) {
        return super.find(id, "Competition.findById");
    }

    @Override
    public void finishCompetition(Competition competition, List<ParticipationResult> participationResults) {
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
        participation.setCompetitionId(competition);
        em.persist(participation);
    }

    @Override
    public void registrationNewParticipation(Competition competition, User user) {
        Participation participation = new Participation();
        participation.setRegistered(true);
        participation.setUserId(user);
        participation.setCompetitionId(competition);
        em.persist(participation);
    }

    @Override
    public void addParticipations(Competition competition, List<User> users) {
        Participation participation;
        for (User user: users) {
            participation = new Participation();
            participation.setRegistered(true);
            participation.setUserId(user);
            participation.setCompetitionId(competition);
            em.persist(participation);
        }
    }    
    
}
