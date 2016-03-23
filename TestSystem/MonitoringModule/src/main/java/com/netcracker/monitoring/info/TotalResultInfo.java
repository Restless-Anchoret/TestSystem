/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.info;

import java.util.List;

/**
 *
 * @author Магистраж
 */
public class TotalResultInfo {

    private int id;
    private int place;
    List<ProblemResultInfo> problemResultInfo;
    private int points;
    private int fine;

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

}
