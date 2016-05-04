package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.web.logging.WebLogging;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class CompetitionsController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    public CompetitionsController() { }
    
    public List<Competition> getCompetitionsList() {
        try {
            return competitionFacade.findVisibleCompetiotions();
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }
    
    public String getTypeDescription(Competition competition) {
        if (competition.getHoldCompetition()) {
            return "Соревнование";
        } else {
            return "Тренировка";
        }
    }
    
    public boolean isStartedCompetiotion(Competition competition) {
        return competition.getCompetitionStart().compareTo(new Date()) < 0;
    }
}
