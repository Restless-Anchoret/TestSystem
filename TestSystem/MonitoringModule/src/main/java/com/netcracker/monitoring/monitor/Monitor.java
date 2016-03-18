/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.monitor;
/**
 *
 * @author Магистраж
 */
public interface Monitor {

    synchronized void startMonitoring();

    synchronized List<TotalResultInfo> getActualResults();

    synchronized List<TotalResultInfo> getVisibleResults();

    synchronized void setRankStrategy(RankStrategy strategy);
}
