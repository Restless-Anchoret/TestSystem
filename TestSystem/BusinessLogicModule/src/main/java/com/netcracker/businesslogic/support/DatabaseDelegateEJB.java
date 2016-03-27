package com.netcracker.businesslogic.support;

import com.netcracker.dal.CompetitionFacadeLocal;
import com.netcracker.dal.ParticipationResultFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class DatabaseDelegateEJB /*implements DatabaseDelegate*/ {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ParticipationResultFacade")
    private ParticipationResultFacadeLocal participationResultFacade;
    
//    @Override
//    public List<ProblemResultInfo> getProblemResultInfos(int competitionId) {
//        
//    }
//    
//    @Override
//    public CompetitionInfo getCompetitionInfo(int competitionId) {
//        
//    }
    
}
