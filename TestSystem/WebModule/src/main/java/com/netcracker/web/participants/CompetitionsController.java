package com.netcracker.web.participants;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.session.AuthenticationController;
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
    private AuthenticationEJB authenticationEJB;
    
    public CompetitionsController() {
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
    }
    
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
    
    public boolean isAccess(Competition competition) {
        boolean result = false;
        User user = authenticationEJB.getCurrentUser();
        if (user.getRole().equals("admin") || user.getRole().equals("moderator"))
            return true;
        switch (competitionEJB.getCompetitionPhase(competition)) {
            case BEFORE:
            case WAITING_RESULTS:    
                return false;
            case CODING:
            case CODING_FROZEN:
                competitionFacade.loadParticipations(competition);
                List<Participation> participations = competition.getParticipationList();
                for (Participation participation : participations) {
                    if (participation.getUserId().getId().equals(user.getId())) {
                        result = participation.getRegistered();
                        break;
                    }
                }
                return result;
            case FINISHED:
                return competition.getPracticePermition();
        }
        return result;
    }
    
    public boolean toRegistrate(Competition competition) {
        boolean result = (competition.getCompetitionStart().compareTo(new Date()) > 0);
        User user = authenticationEJB.getCurrentUser();
        result = result && user.getRole().equals("participant");
        result = result && 
                (competition.getRegistrationType().equals("public") || 
                competition.getRegistrationType().equals("moderation"));
        return result;
    }
    
    public long getSecondsBeforeStart(Competition competition) {
        if (competition.getCompetitionStart().before(new Date())) {
            return 0;
        }
        else
            return (competition.getCompetitionStart().getTime() - new Date().getTime()) / 1000;
    }
    
    public boolean isViewTimer(Competition competition) {
        if (competition.getCompetitionStart().before(new Date())) {
            return true;
        }
        return ((competition.getCompetitionStart().getTime() - new Date().getTime()) / 1000 <= 86_400);
    }
}
