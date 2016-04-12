package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.rank.RankStrategy;

public interface MonitorPool {

    Monitor getMonitor(int competitionId);
    void setDatabaseDelegate(DatabaseDelegate delegate);
    void setResultsConservator(ResultsConservator conservator);
    void setRankStrategy(RankStrategy strategy);
    
}
