package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class CompetitionsController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB
    private CompetitionEJB competitionEJB;
    
    public CompetitionsController() { }
    
    public List<Competition> getCompetitionsList() {
        try {
            return competitionFacade.findAllCompetiotions(new int[] { 0, 30 });
        } catch (Exception exception) {
            return Collections.EMPTY_LIST;
        }
    }
    
//    public String getCompetitionPhase(Competition competition) {
//        
//    }
    
    public String getTypeDescription(Competition competition) {
        if (competition.getHoldCompetition()) {
            return "Соревнование";
        } else {
            return "Тренировка";
        }
    }

}
