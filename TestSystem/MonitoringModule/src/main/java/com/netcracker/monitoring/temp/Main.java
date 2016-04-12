package com.netcracker.monitoring.temp;

import com.netcracker.monitoring.conservator.ResultsConservator;
import com.netcracker.monitoring.conservator.XmlResultsConservator;
import com.netcracker.monitoring.delegate.FileSystemDelegate;
import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.rank.RankStrategy;
import com.netcracker.monitoring.rank.StandardRankStrategy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int PROBLEMS_QUANTITY = 5;
    private static final int USERS_QUANTITY = 200;
    private static final String RESULTS_PATH = "D:\\visible_results.xml";
    
    public static void main(String[] args) {
        List<ProblemResultInfo> problemResultInfos = new ArrayList<>();
        RankStrategy rankStrategy = new StandardRankStrategy();
        ResultsConservator resultsConservator = new XmlResultsConservator(new FileSystemDelegateImpl());
        Random random = new Random(0);
        for (int problemId = 1; problemId <= PROBLEMS_QUANTITY; problemId++) {
            for (int userId = 1; userId <= USERS_QUANTITY; userId++) {
                short points = (short)random.nextInt(12);
                int fine = random.nextInt(1000);
                ProblemResultInfo info = new ProblemResultInfo(points, fine, userId, problemId);
                problemResultInfos.add(info);
            }
        }
        List<TotalResultInfo> totalResultInfos = rankStrategy.formResults(problemResultInfos);
        printResults(totalResultInfos);
        long start = System.currentTimeMillis();
        boolean success = resultsConservator.persistVisibleResults(null, totalResultInfos);
        System.out.println(success + " " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        totalResultInfos = resultsConservator.getVisibleResults(null);
        System.out.println((System.currentTimeMillis() - start) + " ms");
        printResults(totalResultInfos);
    }
    
    private static void printResults(List<TotalResultInfo> totalResultInfos) {
        for (TotalResultInfo info: totalResultInfos) {
            System.out.print(info.getPlace() + " " + info.getId() + " " + info.getPoints() + " " + info.getFine() + " [");
            for (ProblemResultInfo prInfo: info.getProblemResultInfoList()) {
                System.out.print(prInfo.getPoints() + " ");
            }
            System.out.println("]");
        }
    }
    
    private static class FileSystemDelegateImpl implements FileSystemDelegate {

        @Override
        public Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting) {
            return Paths.get(RESULTS_PATH);
        }
        
    }
    
}