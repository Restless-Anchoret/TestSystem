/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.logging.MonitoringLogging;
import com.netcracker.monitoring.rank.RankStrategy;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Магистраж
 */
public class StandardMonitor implements Monitor {


    public static final Logger logger = MonitoringLogging.logger;

    private static final long DELAY = 30000;
    private int competitionId;
    private String competitionFolder = null;
    private List<TotalResultInfo> actualResults = null;
    private List<TotalResultInfo> visibleResults = null;
    private DatabaseDelegate delegate;
    private ResultsConservator conservator;
    private RankStrategy strategy;
    private ScheduledFuture<List<TotalResultInfo>> savesOfVisibleResults = null;
    private Date lastTimeUpdateOfResults = null;
    private long toUpdateTheResultsDelay = DELAY;

    public StandardMonitor(int competitionId, DatabaseDelegate delegate, ResultsConservator conservator, RankStrategy strategy) {
        this.competitionId = competitionId;
        this.delegate = delegate;
        this.conservator = conservator;
        this.strategy = strategy;
    }

    @Override
    public synchronized void startMonitoring() {
        competitionFolder = delegate.getCompetitionInfo(competitionId).getCompetitionFolder();
        updateResults();
    }

    @Override
    public synchronized List<TotalResultInfo> getActualResults() {
        updateResults();
        return Collections.unmodifiableList(actualResults);
    }

    @Override
    public synchronized List<TotalResultInfo> getVisibleResults() {
        updateResults();
        return Collections.unmodifiableList(visibleResults);
    }

    @Override
    public synchronized void setRankStrategy(RankStrategy strategy) {
        this.strategy = strategy;
    }

    private class VisibleResultsSavingTask implements Callable<List<TotalResultInfo>> {

        ScheduledExecutorService executor;

        public VisibleResultsSavingTask(ScheduledExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public List<TotalResultInfo> call() {
            List<TotalResultInfo> nowActualResults = actualResults;
            conservator.persistVisibleResults(competitionFolder, nowActualResults);
            if (executor != null) {
                executor.shutdown();

            }
            return conservator.getVisibleResults(competitionFolder);
        }

    }

    private void updateResults() {
        CompetitionPhase competitionPhaseUpdate = delegate.getCompetitionInfo(competitionId).getCompetitionStatus();
        if (competitionPhaseUpdate.equals(CompetitionPhase.BEFORE)) {
            return;
        }
        if (actualResults == null || new Date().getTime() - lastTimeUpdateOfResults.getTime() < toUpdateTheResultsDelay) {
            List<ProblemResultInfo> problemResultInfos = delegate.getProblemResultInfos(competitionId);
            List<TotalResultInfo> formResults = strategy.formResults(problemResultInfos);
            this.actualResults = formResults;
            this.lastTimeUpdateOfResults = new Date();
        }
        if (competitionPhaseUpdate.equals(CompetitionPhase.CODING)) {
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            Date dateNow = new Date();
            Date dateMoment = delegate.getCompetitionInfo(competitionId).getTimeOfFreezing();
            long milliseconds = dateMoment.getTime() - dateNow.getTime();
            VisibleResultsSavingTask task = new VisibleResultsSavingTask(service);
            ScheduledFuture<List<TotalResultInfo>> future = service.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
            this.savesOfVisibleResults = future;
        } else {
            if (visibleResults != null) {
                return;
            }
            if (savesOfVisibleResults != null) {
                try {
                    this.visibleResults = savesOfVisibleResults.get();
                } catch (InterruptedException | ExecutionException ex) {
                      logger.log(Level.SEVERE, "some errors occured during getting results: {0}", ex.toString());
                }
            } else {
                List<TotalResultInfo> tmpVisibleResults = conservator.getVisibleResults(competitionFolder);
                if (tmpVisibleResults != null) {
                    this.visibleResults = tmpVisibleResults;
                } else {
                    VisibleResultsSavingTask task = new VisibleResultsSavingTask(null);
                    this.visibleResults = task.call();
                }
            }
        }
    }

}
