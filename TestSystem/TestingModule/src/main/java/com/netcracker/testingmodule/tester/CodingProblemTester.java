package com.netcracker.testingmodule.tester;

import com.netcracker.testingmodule.evaluation.EvaluationSystem;
import com.netcracker.testingmodule.language.FailException;
import com.netcracker.testingmodule.language.LanguageToolkit;
import com.netcracker.testingmodule.language.TimeLimitException;
import com.netcracker.testingmodule.logging.TestingLogging;
import com.netcracker.testingmodule.system.TestGroupType;
import com.netcracker.testingmodule.system.TestTable;
import com.netcracker.testingmodule.system.TestingFileSupplier;
import com.netcracker.testingmodule.system.TestingInfo;
import com.netcracker.testingmodule.system.Verdict;
import com.netcracker.testingmodule.system.VerdictInfo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class CodingProblemTester implements ProblemTester {

    @Override
    public void performTesting(TestingFileSupplier fileSupplier, TestingInfo info) {
        Path sourceFile = fileSupplier.getSubmissionSourceFile(info.getSubmissionFolder());
        Path compileFolder = fileSupplier.getSubmissionCompileFolder(info.getSubmissionFolder());
        //System.out.println(sourceFile.toString());
        //System.out.println(compileFolder.toString());
        if (Files.notExists(sourceFile) || Files.notExists(compileFolder)) {
            TestingLogging.logger.fine("Source code or directory to compilation were not found");
            info.setVerdictInfo(VerdictInfo.VERDICT_FAIL);
            return;
        }
        int compilationResult = 0;
        try {
            compilationResult = info.getLanguageToolkit().compile(sourceFile, compileFolder);
        } catch (FailException exception) {
            TestingLogging.logger.log(Level.FINE, "FailException while compilation of decision", exception);
            if (exception.getCause() != null) {
                TestingLogging.logger.log(Level.FINE, "FailException while compilation of decision (cause)", exception.getCause());
            }
            info.setVerdictInfo(VerdictInfo.VERDICT_FAIL);
            return;
        }
        if (compilationResult != 0) {
            info.setVerdictInfo(VerdictInfo.VERDICT_COMPILE_ERROR);
            return;
        }
        EvaluationSystem evaluationSystem = info.getEvaluationSystem();
        evaluationSystem.orderTesting(new CodingTesterDelegate(info, fileSupplier), info.isPretestsOnly());
        VerdictInfo verdictInfo = evaluationSystem.getVerdictInfo(info.getTestTable(), info.isPretestsOnly());
        info.setVerdictInfo(verdictInfo);
    }
    
    private static class CodingTesterDelegate implements EvaluationSystem.TesterDelegate {

        private TestingInfo info;
        private TestingFileSupplier fileSupplier;

        public CodingTesterDelegate(TestingInfo testingInfo, TestingFileSupplier fileSupplier) {
            this.info = testingInfo;
            this.fileSupplier = fileSupplier;
        }
        
        @Override
        public TestTable getTestTable() {
            return info.getTestTable();
        }

        @Override
        public Integer performTestGroup(TestGroupType type, boolean upToFirstFailure) {
            Integer wrongTestNumber = null;
            int testsQuantity = info.getTestTable().getTestsQuantity(type);
            for (int testNumber = 1; testNumber <= testsQuantity; testNumber++) {
                VerdictInfo verdictInfo = performOneTest(type, testNumber);
                info.getTestTable().putVerdictInfo(type, testNumber, verdictInfo);
                if (wrongTestNumber == null && verdictInfo.getVerdict() != Verdict.ACCEPTED) {
                    wrongTestNumber = testNumber;
                }
                if (upToFirstFailure && verdictInfo.getVerdict() != Verdict.ACCEPTED) {
                    break;
                }
            }
            return wrongTestNumber;
        }

        @Override
        public VerdictInfo performOneTest(TestGroupType type, int testNumber) {
            Path outputFile = fileSupplier.getTempFile();
            try {
                if (outputFile == null || Files.notExists(outputFile)) {
                    TestingLogging.logger.fine("Temp file for output was not found");
                    return VerdictInfo.VERDICT_FAIL;
                }
                Path inputFile = fileSupplier.getTestInputFile(info.getProblemFolder(), type, testNumber);
                Path answerFile = fileSupplier.getTestAnswerFile(info.getProblemFolder(), type, testNumber);
                Path decisionFile = fileSupplier.getSubmissionCompileFile(info.getSubmissionFolder());
                if (Files.notExists(inputFile) || Files.notExists(answerFile) || Files.notExists(decisionFile)) {
                    TestingLogging.logger.fine("Input file or answer file or decision file were not found");
                    return VerdictInfo.VERDICT_FAIL;
                }
                try {
                    LanguageToolkit.ExecutionInfo executionInfo = info.getLanguageToolkit().execute(decisionFile,
                            inputFile, outputFile, info.getTimeLimit(), info.getMemoryLimit());
                    if (executionInfo.getExitCode() != 0) {
                        return new VerdictInfo(Verdict.RUNTIME_ERROR)
                                .setDecisionTime(executionInfo.getDecisionTime())
                                .setDecisionMemory(executionInfo.getDecisionMemory());
                    }
                    Verdict verdict = info.getChecker().check(inputFile, outputFile, answerFile);
                    return new VerdictInfo(verdict)
                            .setDecisionTime(executionInfo.getDecisionTime())
                            .setDecisionMemory(executionInfo.getDecisionMemory());
                } catch (TimeLimitException exception) {
                    return VerdictInfo.VERDICT_TIME_LIMIT;
                } catch (FailException exception) {
                    TestingLogging.logger.log(Level.FINE, "FailException while compilation of decision", exception);
                    if (exception.getCause() != null) {
                        TestingLogging.logger.log(Level.FINE, "FailException while compilation of decision (cause)", exception.getCause());
                    }
                    return VerdictInfo.VERDICT_FAIL;
                }
            } finally {
                fileSupplier.deleteTempFile(outputFile);
            }
        }
        
    }

}