package com.netcracker.web.util;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.entity.Competition;
import com.netcracker.monitoring.info.CompetitionPhase;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class TimersInfo {

    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    public long getSecondsBeforeStart(Competition competition) {
        if (competition.getCompetitionStart().before(new Date())) {
            return 0;
        }
        else {
            return (competition.getCompetitionStart().getTime() - new Date().getTime()) / 1000;
        }
    }
    
    public boolean isViewTimerBeforeStart(Competition competition) {
        return competition.getCompetitionStart().after(new Date()) &&
                ((competition.getCompetitionStart().getTime() - new Date().getTime()) / 1000 <= 86_400);
    }
    
    public String statusCompetitionOnCompetitionsPage(Competition competition) {
        if (competition.getCompetitionStart().after(new Date()) &&
                ((competition.getCompetitionStart().getTime() - new Date().getTime()) / 1000 > 86_400))
            return "больше 24 часов";
        else if (competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.CODING ||
                 competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.CODING_FROZEN)
            return "соревнование идет";
        else
            return "соревнование закончилось";
    }
    
    public long getSecondsBeforeEnd(Competition competition) {
        if (competitionEJB.getCompetitionEnd(competition).before(new Date())) {
            return 0;
        }
        else {
            return (competitionEJB.getCompetitionEnd(competition).getTime() - new Date().getTime()) / 1000;
        }
    }
    
    public boolean isViewTimerBeforeEnd(Competition competition) {
        return (competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.CODING) || 
                (competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.CODING_FROZEN);
    }
    
    public String statusCompetitionOnCompetitionPage(Competition competition) {
        if (competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.BEFORE)
            return "Соревнование еще не началось";
        switch (competitionEJB.getCompetitionPhase(competition)) {
            case BEFORE:
                return "Соревнование еще не началось ";
            case CODING:
            case CODING_FROZEN:
                return "До конца соревнования осталось ";
            default:
                return "Соревнование закончилось ";
        }
    }
    
}
