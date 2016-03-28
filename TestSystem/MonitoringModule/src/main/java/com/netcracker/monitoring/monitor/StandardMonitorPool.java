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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Магистраж
 */
public class StandardMonitorPool implements MonitorPool {

    private static MonitorPool poolOfDefaultMonitors;

    public static MonitorPool getDefault() {
        if (poolOfDefaultMonitors == null) {
            poolOfDefaultMonitors = new StandardMonitorPool();
        }
        return poolOfDefaultMonitors;
    }
    private Map<Integer, Monitor> allMonitors = new HashMap<>();

    private DatabaseDelegate delegate = null;
    private ResultsConservator conservator = null;
    private RankStrategy strategy = (RankStrategy) new StandardRankStrategy();

    void setDelegate(DatabaseDelegate delegate) {
        this.delegate = delegate;
    }

    void setConservator(ResultsConservator conservator) {
        this.conservator = conservator;
    }

    void setStrategy(RankStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    
    public synchronized Monitor getMonitor(int competitionId) {
        Monitor monitor = allMonitors.get(competitionId);
        if (monitor == null) {
            return monitor;
        } else {
            Monitor newStandardMonitor = new StandardMonitor(competitionId, delegate, conservator, strategy);
            newStandardMonitor.startMonitoring();
            allMonitors.put(competitionId, newStandardMonitor);
            return newStandardMonitor;
        }
    }
}
