/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.rank.RankStrategy;
import java.util.List;

/**
 *
 * @author Магистраж
 */
public interface Monitor {

    void startMonitoring();

    List<TotalResultInfo> getActualResults();

    List<TotalResultInfo> getVisibleResults();

    void setRankStrategy(RankStrategy strategy);
}
