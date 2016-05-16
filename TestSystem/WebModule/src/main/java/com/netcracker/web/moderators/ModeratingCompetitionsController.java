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
    private String newName;
    
    public List<Competition> getCompetitionsList() {
        return moderatingCompetitionEJB.getAllCompetitions();
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
    public void addNewCompetition() {
        if (newName != null && newName.isEmpty()) {
            newName = null;
        }
        Competition competition = moderatingCompetitionEJB.addNewCompetition(newName);
        if (competition == null) {
            JSFUtil.addErrorMessage("Exception while adding competition", "");
        }
    }
    
    public void deleteCompetition(Competition competition) {
        String competitionName = competition.getName();
        boolean deletingResult = moderatingCompetitionEJB.removeCompetition(competition);
        if (deletingResult) {
            JSFUtil.addInfoMessage("Соревнование " + competitionName + " успешно удалено", "");
        } else {
            JSFUtil.addErrorMessage("Соревнование " + competitionName + " не может быть удалено", "");
        }
    }
    
}