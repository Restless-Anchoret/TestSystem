package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingCompetitionEJB;
import com.netcracker.database.entity.Competition;
import com.netcracker.web.util.JSFUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ModeratingCompetitionsController {

    @EJB(beanName = "ModeratingCompetitionEJB")
    private ModeratingCompetitionEJB moderatingCompetitionEJB;
    
    public List<Competition> getCompetitionsList() {
        return moderatingCompetitionEJB.getAllCompetitions();
    }
    
    public void addNewCompetition() {
        Competition competition = moderatingCompetitionEJB.addNewCompetition();
        if (competition == null) {
            JSFUtil.addErrorMessage("Exception while adding competition", "");
        }
    }
    
}