package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.ProblemFileSupplierImpl;
import com.netcracker.businesslogic.support.SubmissionFileSupplier;
import com.netcracker.businesslogic.support.CheckSubmissition;
import com.netcracker.businesslogic.support.CheckSubmissitionCompetition;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationResultFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.dal.TestGroupFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.Submission;
import com.netcracker.database.entity.TestGroup;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.testing.checker.Checker;
import com.netcracker.testing.checker.CheckerRegistry;
import com.netcracker.testing.evaluation.EvaluationSystem;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import com.netcracker.testing.language.LanguageToolkit;
import com.netcracker.testing.language.LanguageToolkitRegistry;
import com.netcracker.testing.system.CodeFileSupplier;
import com.netcracker.testing.system.MultithreadTestingSystem;
import com.netcracker.testing.system.ProblemFileSupplier;
import com.netcracker.testing.system.TestGroupType;
import com.netcracker.testing.system.TestTable;
import com.netcracker.testing.system.TestingInfo;
import com.netcracker.testing.tester.ProblemTester;
import com.netcracker.testing.tester.ProblemTesterRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class CompetitionEJB {
    
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "TestGroupFacade")
    private TestGroupFacadeLocal testGroupFacade;
    @EJB(beanName = "ParticipationResultFacade")
    ParticipationResultFacadeLocal participationResultFacade;
    
    public CompetitionPhase getCompetitionPhase(Competition competition) {
        if (competition.getFinished()) {
            return CompetitionPhase.FINISHED;
        }
        if (competition.getCompetitionStart() == null ||
                competition.getCompetitionInterval() == null ||
                competition.getIntervalFrozen() == null) {
            return CompetitionPhase.BEFORE;
        }
        Date currentMoment = new Date();
        long millis = currentMoment.getTime();
        if (millis < competition.getCompetitionStart().getTime()) {
            return CompetitionPhase.BEFORE;
        } else if (millis < getCompetitionFrozenStart(competition).getTime()) {
            return CompetitionPhase.CODING;
        } else if (millis < getCompetitionEnd(competition).getTime()) {
            return CompetitionPhase.CODING_FROZEN;
        } else {
            return CompetitionPhase.WAITING_RESULTS;
        }
    }
    
    public RegistrationType getRegistrationType(Competition competition) {
        return RegistrationType.valueOf(competition.getRegistrationType().toUpperCase());
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean addSubmission(Integer competitionId, CompetitionProblem competitionProblem, 
            User user, Compilator compilator, InputStream file, String fileName, Long fileSize) {
        //applicationEJB.getMonitorPool().getMonitor(competitionId);
        Submission submission = new Submission();
        submission.setCompetitionProblemId(competitionProblem);
        submission.setUserId(user);
        submission.setCompilatorId(compilator);
        submission.setSubmissionTime(new Date());
        submission.setVerdict("проверяется");
        try {
            submissionFacade.create(submission);
            submission.setFolderName(submission.getId().toString());
        } catch(Throwable ex) {
            BusinessLogicLogging.logger.log(Level.SEVERE, null, ex);
            return false;
        }
        if (!applicationEJB.getFileSupplier().addSubmissionFolder(submission.getFolderName())) {
            return false;
        }
        try {
            Path submissionFile = Paths.get(applicationEJB.getFileSupplier().
                      getSubmissionSourceFolder(submission.getFolderName()).toString(), fileName);
            OutputStream outputStream = new FileOutputStream(submissionFile.toFile());
            long bytes = saveFile(file, outputStream);
            if (bytes != fileSize) {
                Files.deleteIfExists(submissionFile);
                submission.setFolderName(null);
            }
            submissionFacade.edit(submission);
        } catch (Throwable ex) {
            BusinessLogicLogging.logger.log(Level.SEVERE, null, ex);
            return false;
        }
        ProblemTester problemTester = ProblemTesterRegistry.registry().get("coding");
        Competition competition = competitionFacade.find(competitionId);
        EvaluationSystem evaluationSystem = EvaluationSystemRegistry.registry().
                get(competition.getEvaluationType());
        LanguageToolkit languageToolkit = LanguageToolkitRegistry.registry().get(compilator.getName());
        Checker checker = CheckerRegistry.registry().getDefault();
        CodeFileSupplier codeFileSupplier = new SubmissionFileSupplier(submission.getFolderName(),
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
        CheckSubmissition checkSubmissition;
        //if (submission.getSubmissionTime().compareTo(getCompetitionEnd(competition)) < 0)
            checkSubmissition = new CheckSubmissitionCompetition(submission, submissionFacade,
                participationResultFacade);
//        else
//            checkSubmissition = new CheckSubmissition(submission);
        TestingInfo testingInfo = new TestingInfo(checkSubmissition,
                problemTester, evaluationSystem, languageToolkit, checker, codeFileSupplier,
                problemFileSupplier, competition.getPretestsOnly(),
                competitionProblem.getProblemId().getTimeLimit(), 
                competitionProblem.getProblemId().getMemoryLimit(), testTable);
        BusinessLogicLogging.logger.log(Level.INFO, "add in test system");
        applicationEJB.getTestingSystem().addSubmission(testingInfo);
        return true;
    }
    
    private long saveFile(InputStream inputStream, OutputStream outputStream) {
        long result = 0;
        int read;
        byte[] buffer = new byte[1024];
        try {
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                result += read;
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            BusinessLogicLogging.logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Date getCompetitionFrozenStart(Competition competition) {
        return addMinutesToDate(competition.getCompetitionStart(),
                competition.getCompetitionInterval() - competition.getIntervalFrozen());
    }
    
    public Date getCompetitionEnd(Competition competition) {
        return addMinutesToDate(competition.getCompetitionStart(), competition.getCompetitionInterval());
    }
    
    private Date addMinutesToDate(Date date, int minutes) {
        return new Date(date.getTime() + minutes * 60_000);
    }
    
    public List<String> getAvailableEvaluationSystems() {
        return new ArrayList<>(EvaluationSystemRegistry.registry().getAvailableIds());
    }
    
    public List<String> getAvailableRegistrationTypes() {
        List<String> resultList = new ArrayList<>();
        for (RegistrationType type: RegistrationType.values()) {
            resultList.add(type.toString().toLowerCase());
        }
        return resultList;
    }
    
}
