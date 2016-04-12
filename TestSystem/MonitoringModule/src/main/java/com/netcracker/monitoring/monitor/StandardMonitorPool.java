package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.rank.RankStrategy;
import com.netcracker.monitoring.rank.StandardRankStrategy;
import java.util.HashMap;
import java.util.Map;

public class StandardMonitorPool implements MonitorPool {

    private static MonitorPool defaultMonitorPool;

    public static MonitorPool getDefault() {
        if (defaultMonitorPool == null) {
            defaultMonitorPool = new StandardMonitorPool();
        }
        return defaultMonitorPool;
    }
    
    private Map<Integer, Monitor> monitors = new HashMap<>();

    private DatabaseDelegate databaseDelegate = null;
    private ResultsConservator resultsConservator = null;
    private RankStrategy rankStrategy = new StandardRankStrategy();

    @Override
    public void setDatabaseDelegate(DatabaseDelegate delegate) {
        this.databaseDelegate = delegate;
    }

    @Override
    public void setResultsConservator(ResultsConservator conservator) {
        this.resultsConservator = conservator;
    }

    @Override
    public void setRankStrategy(RankStrategy strategy) {
        this.rankStrategy = strategy;
    }

    @Override
    public synchronized Monitor getMonitor(int competitionId) {
        Monitor monitor = monitors.get(competitionId);
        if (monitor != null) {
            return monitor;
        } else {
            Monitor newStandardMonitor = new StandardMonitor(competitionId, databaseDelegate, resultsConservator, rankStrategy);
            newStandardMonitor.startMonitoring();
            monitors.put(competitionId, newStandardMonitor);
            return newStandardMonitor;
        }
    }
    
}
