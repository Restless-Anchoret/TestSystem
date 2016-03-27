package com.netcracker.businesslogic.support;

import com.netcracker.dal.CompetitionFacadeLocal;
import com.netcracker.dal.ParticipationResultFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class DatabaseDelegateEJB /*implements DatabaseDelegate*/ {

    @EJB
    private CompetitionFacadeLocal competitionFacade;
    @EJB
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
