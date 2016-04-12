package com.netcracker.monitoring.rank;

import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.List;

public interface RankStrategy {
    
    List<TotalResultInfo> formResults(List<ProblemResultInfo> problemResultInfos);
    
}
