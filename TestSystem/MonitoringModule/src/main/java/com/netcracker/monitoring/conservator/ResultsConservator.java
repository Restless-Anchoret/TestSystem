package com.netcracker.monitoring.conservator;

import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.List;

public interface ResultsConservator {

    List<TotalResultInfo> getVisibleResults(String competitionFolder);
    boolean persistVisibleResults(String competitionFolder, List<TotalResultInfo> results);
    
}
