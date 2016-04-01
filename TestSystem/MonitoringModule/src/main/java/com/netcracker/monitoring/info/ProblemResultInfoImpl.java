/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.info;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Магистраж
 */
@XmlRootElement(name = "problem-result")
public class ProblemResultInfoImpl implements ProblemResultInfo {

    short points;
    int fine;
    int userId;
    int problemId;

    public ProblemResultInfoImpl(short points, int fine, int userId, int problemId) {
        this.points = points;
        this.fine = fine;
        this.userId = userId;
        this.problemId = problemId;
    }

    public short getPoints() {
        return this.points;
    }

    public int getFine() {
        return this.fine;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getProblemId() {
        return this.problemId;
    }

    @XmlElement
    public void setPoints(short points) {
        this.points = points;
    }

    @XmlElement
    public void setFine(int fine) {
        this.fine = fine;
    }

    @XmlElement(name = "user-id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @XmlElement(name = "problem-id")
    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

}
