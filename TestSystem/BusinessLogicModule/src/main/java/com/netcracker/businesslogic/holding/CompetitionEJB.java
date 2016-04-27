package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.Submission;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class CompetitionEJB {
    
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    
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
    
    public boolean addSubmission(Integer competitionId, CompetitionProblem competitionProblem,
                        Compilator compilator, InputStream file, String fileName, Long fileSize) {
        BusinessLogicLogging.logger.log(Level.INFO, "addsubmission method");
        Submission submission = new Submission();
        submission.setCompetitionProblemId(competitionProblem);
        submission.setUserId(user);
        submission.setCompilatorId(compilator);
        submission.setSubmissionTime(new Date());
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
            File submissionFile = new File(applicationEJB.getFileSupplier().
                    getSubmissionSourceFolder(fileName) + fileName);
            submissionFile.createNewFile();
            OutputStream outputStream = new FileOutputStream(submissionFile);
            long bytes = saveFile(file, outputStream);
            if (bytes != fileSize) {
                submissionFile.delete();
                submission.setFolderName(null);
            }
            submissionFacade.edit(submission);
        } catch (Throwable ex) {
            BusinessLogicLogging.logger.log(Level.SEVERE, null, ex);
            return false;
        }
        
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
