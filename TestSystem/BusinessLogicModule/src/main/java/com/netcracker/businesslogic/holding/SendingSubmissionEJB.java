package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.support.ProblemFileSupplierImpl;
import com.netcracker.businesslogic.support.SubmissionFileSupplier;
import com.netcracker.database.dal.TestGroupFacadeLocal;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.TestGroup;
import com.netcracker.testing.checker.Checker;
import com.netcracker.testing.checker.CheckerRegistry;
import com.netcracker.testing.evaluation.EvaluationSystem;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import com.netcracker.testing.language.LanguageToolkit;
import com.netcracker.testing.language.LanguageToolkitRegistry;
import com.netcracker.testing.system.CodeFileSupplier;
import com.netcracker.testing.system.ProblemFileSupplier;
import com.netcracker.testing.system.TestGroupType;
import com.netcracker.testing.system.TestResultHandler;
import com.netcracker.testing.system.TestTable;
import com.netcracker.testing.system.TestingInfo;
import com.netcracker.testing.tester.ProblemTester;
import com.netcracker.testing.tester.ProblemTesterRegistry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class SendingSubmissionEJB {

    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "TestGroupFacade")
    private TestGroupFacadeLocal testGroupFacade;
    
    public void sendSubmission(CompetitionProblem competitionProblem, String submissionFolder,
            String evaluationSystemType, boolean pretestsOnly, Compilator compilator, TestResultHandler handler) {
        ProblemTester problemTester = ProblemTesterRegistry.registry().get(competitionProblem.getProblemId().getType());
        EvaluationSystem evaluationSystem = EvaluationSystemRegistry.registry().
                get(evaluationSystemType);
        LanguageToolkit languageToolkit = LanguageToolkitRegistry.registry().get(compilator.getName());
        Checker checker = CheckerRegistry.registry().get(competitionProblem.getProblemId().getCheckerType());
        CodeFileSupplier codeFileSupplier = new SubmissionFileSupplier(submissionFolder,
                applicationEJB.getFileSupplier());
        ProblemFileSupplier problemFileSupplier = new ProblemFileSupplierImpl(
                competitionProblem.getProblemId().getFolderName(),
                applicationEJB.getFileSupplier());
        TestTable testTable = new TestTable();
        List<TestGroup> testGroups = testGroupFacade.getTestGroupsByProblemId(competitionProblem.
                getProblemId().getId());
        for (TestGroup testGroup: testGroups)
            testTable.putTestGroup(TestGroupType.valueOf(testGroup.getTestGroupType().toUpperCase()),
                    testGroup.getPointsForTest(), testGroup.getTestsQuantity());
        TestingInfo testingInfo = new TestingInfo(handler,
                problemTester, evaluationSystem, languageToolkit, checker, codeFileSupplier,
                problemFileSupplier, pretestsOnly,
                competitionProblem.getProblemId().getTimeLimit(), 
                competitionProblem.getProblemId().getMemoryLimit(), testTable);
        applicationEJB.getTestingSystem().addSubmission(testingInfo);
    }
    
}