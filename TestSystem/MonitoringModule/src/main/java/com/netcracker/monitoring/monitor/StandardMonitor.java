/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.rank.RankStrategy;
import com.netcracker.monitoring.rank.StandardRankStrategy;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 *
 * @author Магистраж
 */
public class StandardMonitor implements Monitor{
    private static final long delay = 30000; 
    int competitionId;
    String theNameOfTheCompetitionFolder = null;
    List<TotalResultInfo> ActualResults = null;
    List<TotalResultInfo> VisibleResults = null;
    DatabaseDelegate delegate;
    ResultsConservator conservator;
    RankStrategy strategy;
    ScheduledFuture<List<TotalResultInfo>> savesOfVisibleResults = null;
    Date lastTimeUpdateOfCurrentResults = null;
    long toUpdateTheResultsDelay = delay;
    
}
