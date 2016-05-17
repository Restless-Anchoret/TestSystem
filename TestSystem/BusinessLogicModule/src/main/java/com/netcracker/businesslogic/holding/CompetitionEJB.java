package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.moderating.SendingSubmissionEJB;
import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.CheckSubmissionHandler;
import com.netcracker.businesslogic.support.CheckSubmissionCompetitionHandler;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.Role;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationResultFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.Submission;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import com.netcracker.testing.system.Verdict;
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
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class CompetitionEJB {
    
    @Resource(name = "timeZoneId")
    private String timeZoneId;
    
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ParticipationResultFacade")
    private ParticipationResultFacadeLocal participationResultFacade;
    @EJB(beanName = "SendingSubmissionEJB")
    private SendingSubmissionEJB sendingSubmissionEJB;
    private AuthenticationEJB authenticationEJB;
    
    
    public String getTimeZoneId() {
        return timeZoneId;
    }
    
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
        applicationEJB.getMonitorPool().getMonitor(competitionId);
        Submission submission = new Submission();
        submission.setCompetitionProblemId(competitionProblem);
        submission.setUserId(user);
        submission.setCompilatorId(compilator);
        submission.setSubmissionTime(new Date());
        submission.setVerdict(Verdict.WAITING.toString());
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
        Competition competition = competitionFacade.find(competitionId);
        CheckSubmissionHandler handler;
        if (Role.valueOf(user.getRole().toUpperCase()) == Role.ADMIN ||
                Role.valueOf(user.getRole().toUpperCase()) == Role.MODERATOR)
            handler = new CheckSubmissionHandler(submission, submissionFacade);
        else if (submission.getSubmissionTime().compareTo(getCompetitionEnd(competition)) < 0)
            handler = new CheckSubmissionCompetitionHandler(submission, submissionFacade,
                participationResultFacade);
        else
            handler = new CheckSubmissionHandler(submission, submissionFacade);
        sendingSubmissionEJB.sendSubmission(competitionProblem, submission.getFolderName(),
                competition.getEvaluationType(), competition.getPretestsOnly(), compilator, handler);
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
