package com.netcracker.testingmodule.temp;

import com.netcracker.testingmodule.checker.Checker;
import com.netcracker.testingmodule.checker.CheckerRegistry;
import com.netcracker.testingmodule.evaluation.EvaluationSystem;
import com.netcracker.testingmodule.evaluation.EvaluationSystemRegistry;
import com.netcracker.testingmodule.language.LanguageToolkit;
import com.netcracker.testingmodule.language.LanguageToolkitRegistry;
import com.netcracker.testingmodule.system.MultithreadTestingSystem;
import com.netcracker.testingmodule.system.TestGroupType;
import com.netcracker.testingmodule.system.TestResultHandler;
import com.netcracker.testingmodule.system.TestTable;
import com.netcracker.testingmodule.system.TestingInfo;
import com.netcracker.testingmodule.tester.ProblemTester;
import com.netcracker.testingmodule.tester.ProblemTesterRegistry;

public class TestingModuleTest {
    
    public static void main(String[] args) {
        MultithreadTestingSystem.getDefault().setFileSupplier(new SimpleFileSupplier());
        MultithreadTestingSystem.getDefault().start();
        System.out.println("Started");
        
        TestResultHandler handler = new ConsoleResultHandler();
        ProblemTester tester = ProblemTesterRegistry.registry().get("coding");
        EvaluationSystem evaluationSystem = EvaluationSystemRegistry.registry().get("icpc");
        LanguageToolkit toolkit = LanguageToolkitRegistry.registry().get("java");
        Checker checker = CheckerRegistry.registry().getDefault();
        
//        TestingInfo info = new TestingInfo(handler, tester, evaluationSystem,
//                toolkit, checker, "3", "1", false, 1000, (short)64, getTestTable());
//        tester.performTesting(new SimpleFileSupplier(), info);
//        handler.process(info);
        
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "1", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "2", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "3", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "4", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "5", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "6", "1", false, 1000, (short)64, getTestTable()));
        MultithreadTestingSystem.getDefault().addSubmission(new TestingInfo(handler, tester, evaluationSystem,
                toolkit, checker, "7", "1", false, 1000, (short)64, getTestTable()));
        
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            
        }
        
        MultithreadTestingSystem.getDefault().stop();
        System.out.println("Stopped");
//        System.out.println(new DefaultChecker().check(null, Paths.get("output.txt"), Paths.get("answer.txt")));
    }
    
    private static TestTable getTestTable() {
        TestTable table = new TestTable();
        table.putTestGroup(TestGroupType.SAMPLES, (short)0, 2);
        table.putTestGroup(TestGroupType.PRETESTS, (short)4, 1);
        table.putTestGroup(TestGroupType.TESTS_1, (short)6, 1);
        return table;
    }
    
    private static class ConsoleResultHandler implements TestResultHandler {

        @Override
        public void process(TestingInfo info) {
            System.out.println("Submission id: " + info.getSubmissionFolder());
            System.out.println("Verdict: " + info.getVerdictInfo().getVerdict());
            System.out.println("Points: " + info.getVerdictInfo().getPoints());
            System.out.println("Wrong test number: " + info.getVerdictInfo().getWrongTestNumber());
            System.out.println();
        }
    
    }

}