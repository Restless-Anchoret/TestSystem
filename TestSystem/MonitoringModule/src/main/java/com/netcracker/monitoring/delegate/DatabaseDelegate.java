/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.delegate;

import com.netcracker.monitoring.info.CompetitionInfo;
import com.netcracker.monitoring.info.ProblemResultInfo;
import java.util.List;

/**
 *
 * @author Магистраж
 */
public interface DatabaseDelegate {

    List<ProblemResultInfo> getProblemResultInfos(int competitionId);

    CompetitionInfo getCompetitionInfo(int competitionId);
}
