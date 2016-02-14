package com.netcracker.testingmodule.evaluation;

import com.netcracker.testingmodule.system.TestGroupType;
import com.netcracker.testingmodule.system.TestTable;
import com.netcracker.testingmodule.system.VerdictInfo;
import java.util.Date;
import java.util.TreeMap;

public interface EvaluationSystem {

    void orderTesting(TesterDelegate delegate, boolean pretestsOnly);
    VerdictInfo getVerdictInfo(TestTable testTable, boolean pretestsOnly);
    ProblemResult countProblemResult(TreeMap<Date, VerdictInfo> verdictsMap, Date competitionBeginning);

    public interface TesterDelegate {
        TestTable getTestTable();
        Integer performTestGroup(TestGroupType type, boolean upToFirstFailure);
        VerdictInfo performOneTest(TestGroupType type, int testNumber);
    }
    
    public class ProblemResult {

        private short points;
        private int fine;

        public ProblemResult(short points, int fine) {
            this.points = points;
            this.fine = fine;
        }

        public short getPoints() {
            return points;
        }

        public int getFine() {
            return fine;
        }

    }

}