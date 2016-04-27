package com.netcracker.web.moderators;

import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ModerationCompetitionController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    
    private Competition competition = new Competition();

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    
    public void loadCompetitionById() {
        competition = competitionFacade.find(competition.getId());
    }
    
    public void saveCompetitionChanges() {
        try {
            WebLogging.logger.log(Level.INFO, competition.getId().toString());
            WebLogging.logger.log(Level.INFO, competition.getName());
            if (competition.getHoldCompetition())
                WebLogging.logger.log(Level.INFO, "да");
            else
                WebLogging.logger.log(Level.INFO, "нет");
            WebLogging.logger.log(Level.INFO, competition.getEvaluationType());
            WebLogging.logger.log(Level.INFO, competition.getRegistrationType());
            WebLogging.logger.log(Level.INFO, competition.getFolderName());
            WebLogging.logger.log(Level.INFO, competition.getCompetitionStart().toString());
            WebLogging.logger.log(Level.INFO, competition.getCompetitionInterval().toString());
            WebLogging.logger.log(Level.INFO, competition.getIntervalFrozen().toString());
            competitionFacade.edit(competition);
            JSFUtil.addInfoMessage("Информация о соревновании сохранена", "");
        } catch (Exception exception) {
            JSFUtil.addErrorMessage(exception.toString(), "");
        }
    }
    
    public void runSystemTesting() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
    public void finalizeCompetition() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
}