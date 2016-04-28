package com.netcracker.monitoring.delegate;

import com.netcracker.monitoring.info.CompetitionInfo;
import com.netcracker.monitoring.info.ProblemResultInfo;
import java.util.List;

public interface DatabaseDelegate {

    void initilizeProblemResults(int competitionId);
    List<ProblemResultInfo> getProblemResultInfos(int competitionId);
    CompetitionInfo getCompetitionInfo(int competitionId);
    
}
