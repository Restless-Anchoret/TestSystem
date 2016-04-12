package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.rank.RankStrategy;
import java.util.List;

public interface Monitor {

    void startMonitoring();
    List<TotalResultInfo> getActualResults();
    List<TotalResultInfo> getVisibleResults();
    void setRankStrategy(RankStrategy strategy);
    
}
