package com.netcracker.businesslogic.holding;

import com.netcracker.database.entity.Competition;
import com.netcracker.monitoring.info.CompetitionPhase;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class CompetitionEJB {

    public CompetitionPhase getCompetitionPhase(Competition competition) {
        if (competition.getFinished()) {
            return CompetitionPhase.FINISHED;
        }
        Date currentMoment = new Date();
        long millis = currentMoment.getTime();
        if (millis < competition.getCompetitionStart().getTime()) {
            return CompetitionPhase.BEFORE;
        } else if (millis < getCompetitionFrozenStart(competition).getTime()) {
            return CompetitionPhase.CODING;
        } else if (millis < getCompetitionEnd(competition).getTime()) {
            return CompetitionPhase.CODING_FROZEN;
        } else {
            return CompetitionPhase.WAITING_RESULTS;
        }
    }
    
    public Date getCompetitionFrozenStart(Competition competition) {
        return addMinutesToDate(competition.getCompetitionStart(),
                competition.getCompetitionInterval() - competition.getIntervalFrozen());
    }
    
    public Date getCompetitionEnd(Competition competition) {
        return addMinutesToDate(competition.getCompetitionStart(), competition.getCompetitionInterval());
    }
    
    private Date addMinutesToDate(Date date, int minutes) {
        return new Date(date.getTime() + minutes * 60_000);
    }
    
}
