package com.netcracker.businesslogic.support;

import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationResultFacadeLocal;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.info.CompetitionInfo;
import com.netcracker.monitoring.info.ProblemResultInfo;
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
    
    @Override
    public List<ProblemResultInfo> getProblemResultInfos(int competitionId) {
        return null;
    }
    
    @Override
    public CompetitionInfo getCompetitionInfo(int competitionId) {
        return null;
    }
    
}
