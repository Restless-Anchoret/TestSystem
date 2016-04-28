package com.netcracker.businesslogic.support;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationResultFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.ParticipationResult;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.info.CompetitionInfo;
import com.netcracker.monitoring.info.ProblemResultInfo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class DatabaseDelegateEJB implements DatabaseDelegate {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ParticipationResultFacade")
    private ParticipationResultFacadeLocal participationResultFacade;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    @Override
    public void initilizeProblemResults(int competitionId) {
        List<ParticipationResult> participationResults = participationResultFacade.findByCompetitionId(competitionId);
        if (participationResults.isEmpty()) {
            Competition competition = competitionFacade.find(competitionId);
            competitionFacade.createNullsResults(competition);
        }
    }
    
    @Override
    public List<ProblemResultInfo> getProblemResultInfos(int competitionId) {
        List<ParticipationResult> participationResults = participationResultFacade.findByCompetitionId(competitionId);
        List<ProblemResultInfo> problemResultInfos = new ArrayList<>();
        for (ParticipationResult result: participationResults) {
            problemResultInfos.add(new ProblemResultInfo(result.getPoints(), result.getFine(),
                    result.getUserId().getId(), result.getCompetitionProblemId().getId()));
        }
        return problemResultInfos;
    }
    
    @Override
    public CompetitionInfo getCompetitionInfo(int competitionId) {
        Competition competition = competitionFacade.find(competitionId);
        return new CompetitionInfo(competition.getCompetitionStart(),
                competitionEJB.getCompetitionFrozenStart(competition),
                competitionEJB.getCompetitionEnd(competition),
                competition.getCompetitionInterval(),
                competition.getIntervalFrozen(),
                competitionEJB.getCompetitionPhase(competition),
                competition.getFolderName());
    }
    
}
