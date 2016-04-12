package com.netcracker.monitoring.monitor;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.delegate.DatabaseDelegate;
import com.netcracker.monitoring.info.CompetitionInfo;
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

public class StandardMonitor implements Monitor {

    private static final long DEFAULT_DELAY = 30_000;
    
    private int competitionId;
    private String competitionFolder = null;
    private List<TotalResultInfo> actualResults = null;
    private List<TotalResultInfo> visibleResults = null;
    private DatabaseDelegate databaseDelegate;
    private ResultsConservator resultsConservator;
    private RankStrategy rankStrategy;
    private ScheduledFuture<List<TotalResultInfo>> visibleResultsSavingTask = null;
    private Date lastUpdatingMoment = null;
    private long updatingDelay = DEFAULT_DELAY;

    public StandardMonitor(int competitionId, DatabaseDelegate delegate, ResultsConservator conservator, RankStrategy strategy) {
        this.competitionId = competitionId;
        this.databaseDelegate = delegate;
        this.resultsConservator = conservator;
        this.rankStrategy = strategy;
    }

    @Override
    public synchronized void startMonitoring() {
        competitionFolder = databaseDelegate.getCompetitionInfo(competitionId).getCompetitionFolder();
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
        this.rankStrategy = strategy;
    }

    private void updateResults() {
        CompetitionInfo competitionInfo = databaseDelegate.getCompetitionInfo(competitionId);
        CompetitionPhase competitionPhase = competitionInfo.getCompetitionStatus();
        if (competitionPhase.equals(CompetitionPhase.BEFORE)) {
            return;
        }
        Date currentMoment = new Date();
        if (actualResults == null || currentMoment.getTime() - lastUpdatingMoment.getTime() >= updatingDelay) {
            List<ProblemResultInfo> problemResultInfos = databaseDelegate.getProblemResultInfos(competitionId);
            actualResults = rankStrategy.formResults(problemResultInfos);
            lastUpdatingMoment = currentMoment;
        }
        if (competitionPhase.equals(CompetitionPhase.CODING)) {
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            Date dateNow = new Date();
            Date dateMoment = competitionInfo.getTimeOfFreezing();
            long milliseconds = dateMoment.getTime() - dateNow.getTime();
            VisibleResultsSavingTask task = new VisibleResultsSavingTask(service);
            visibleResultsSavingTask = service.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        } else {
            if (visibleResults != null) {
                return;
            }
            if (visibleResultsSavingTask != null) {
                try {
                    visibleResults = visibleResultsSavingTask.get();
                } catch (InterruptedException | ExecutionException exception) {
                    MonitoringLogging.logger.log(Level.FINE, "Exception while calling get()-method of saving task", exception);
                }
            } else {
                List<TotalResultInfo> tempVisibleResults = resultsConservator.getVisibleResults(competitionFolder);
                if (tempVisibleResults != null) {
                    visibleResults = tempVisibleResults;
                } else {
                    VisibleResultsSavingTask task = new VisibleResultsSavingTask(null);
                    visibleResults = task.call();
                }
            }
        }
    }
    
    private class VisibleResultsSavingTask implements Callable<List<TotalResultInfo>> {
        private ScheduledExecutorService executor;

        public VisibleResultsSavingTask(ScheduledExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public List<TotalResultInfo> call() {
            List<TotalResultInfo> nowActualResults = actualResults;
            resultsConservator.persistVisibleResults(competitionFolder, nowActualResults);
            if (executor != null) {
                executor.shutdown();
            }
            return nowActualResults;
        }
    }

}
