package com.netcracker.monitoring.info;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "total-result")
public class TotalResultInfo {

    private List<ProblemResultInfo> problemResultInfoList = null;
    private int id = 0;
    private int place = 0;
    private short points = 0;
    private int fine = 0;

    public TotalResultInfo() { }

    public TotalResultInfo(int id, List<ProblemResultInfo> problemResultInfoList) {
        this.id = id;
        this.problemResultInfoList = problemResultInfoList;
        problemResultInfoList.forEach(info -> this.points += info.getPoints());
        problemResultInfoList.forEach(info -> this.fine += info.getFine());
    }

    public List<ProblemResultInfo> getProblemResultInfoList() {
        return problemResultInfoList;
    }

    public int getPlace() {
        return place;
    }

    public short getPoints() {
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
    public void setPoints(short points) {
        this.points = points;
    }

    @XmlElement
    public void setFine(int fine) {
        this.fine = fine;
    }

}
