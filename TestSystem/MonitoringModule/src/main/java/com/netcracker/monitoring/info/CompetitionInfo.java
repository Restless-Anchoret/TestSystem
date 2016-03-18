/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.info;

import java.util.Date;

/**
 *
 * @author Магистраж
 */
public class CompetitionInfo {

    private Date timeOfBegin;
    private Date timeOfFreezing;
    private Date timeOfEnd;
    private int theDurationOfTheCompetition;
    private int freezingDuration;
    private CompetitionPhase competitionStatus;
    private String competitionFolder;

    public CompetitionInfo(int theDurationOfTheCompetition, int freezingDuration, CompetitionPhase competitionStatus, String competitionFolder) {
        this.timeOfEnd = new Date();
        this.timeOfFreezing = new Date();
        this.timeOfBegin = new Date();
        this.theDurationOfTheCompetition = theDurationOfTheCompetition;
        this.freezingDuration = freezingDuration;
        this.competitionStatus = competitionStatus;
        this.competitionFolder = competitionFolder;
    }

    public Date getTimeOfBegin() {
        return timeOfBegin;
    }

    public Date getTimeOfFreezing() {
        return timeOfFreezing;
    }

    public Date getTimeOfEnd() {
        return timeOfEnd;
    }

    public int getTheDurationOfTheCompetition() {
        return theDurationOfTheCompetition;
    }

    public int getFreezingDuration() {
        return freezingDuration;
    }

    public CompetitionPhase getCompetitionStatus() {
        return competitionStatus;
    }

    public String getCompetitionFolder() {
        return competitionFolder;
    }

}
