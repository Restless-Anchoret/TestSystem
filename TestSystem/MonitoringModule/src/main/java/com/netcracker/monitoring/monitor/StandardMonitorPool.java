/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.rank.RankStrategy;
import com.netcracker.monitoring.rank.StandardRankStrategy;
import java.util.Map;

/**
 *
 * @author Магистраж
 */
public class StandardMonitorPool implements MonitorPool {

    Map<Integer, Monitor> allMonitors;
    static MonitorPool poolOfDefaultMonitors;

    
    DatabaseDelegate delegate = null;
    ResultsConservator conservator = null;
    RankStrategy strategy = new StandardRankStrategy;

    void setDelegate(DatabaseDelegate delegate) {
        this.delegate = delegate;
    }

    void setConservator(ResultsConservator conservator) {
        this.conservator = conservator;
    }

    void setStrategy(RankStrategy strategy) {
        this.strategy = strategy;
    }
    public static MonitorPool getDefault() {
        if (poolOfDefaultMonitors == null) {
            poolOfDefaultMonitors = new getDefault();
        }
        return poolOfDefaultMonitors;
    }

    @Override
    sychronized public Monitor getMonitor(int competitionId) {
    //    if() then {};
        StandardMonitor newStandardMonitor = new StandardMonitor(DatabaseDelegate delegate,ResultsConservator conservator,RankStrategy strategy);
        startMonitoring();
        allMonitors.put(competitionId, newStandardMonitor);
        return newStandardMonitor;
    }
}
