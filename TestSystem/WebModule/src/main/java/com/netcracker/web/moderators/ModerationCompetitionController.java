package com.netcracker.web.moderators;

import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.web.util.JSFUtil;
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