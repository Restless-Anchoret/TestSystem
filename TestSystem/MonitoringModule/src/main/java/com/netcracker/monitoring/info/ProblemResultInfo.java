/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.info;

/**
 *
 * @author Магистраж
 */
public interface ProblemResultInfo {

    short getPoints();

    int getFine();

    int getUserId();

    int getProblemId();
}
