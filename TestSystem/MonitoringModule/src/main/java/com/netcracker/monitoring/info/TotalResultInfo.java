/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.info;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Магистраж
 */
@XmlRootElement(name = "total-result")
public class TotalResultInfo {

    private List<ProblemResultInfo> problemResultInfoList;
    private int id;
    private int place;
    private int points;
    private int fine;

    public TotalResultInfo(int id, List<ProblemResultInfo> problemResultInfoList) {
        this.id = id;
        this.problemResultInfoList = problemResultInfoList;
        problemResultInfoList.forEach((ProblemResultInfo info) -> this.points += info.getPoints());
        problemResultInfoList.forEach((ProblemResultInfo info) -> this.fine += info.getFine());
    }

    public List<ProblemResultInfo> getProblemResultInfo() {
        return problemResultInfoList;
    }

    public int getPlace() {
        return place;
    }

    public int getPoints() {
        return points;
    }

    public int getFine() {
        return fine;
    }

    public int getId() {
        return id;
    }

    @XmlElement(name = "problem-result")
    @XmlElementWrapper(name = "problem-results")
    public void setProblemResultInfoList(List<ProblemResultInfo> problemResultInfoList) {
        this.problemResultInfoList = problemResultInfoList;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public void setPlace(int place) {
        this.place = place;
    }

    @XmlElement
    public void setPoints(int points) {
        this.points = points;
    }

    @XmlElement
    public void setFine(int fine) {
        this.fine = fine;
    }

}
