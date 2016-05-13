package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.RegistrationType;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.CompetitionProblemFacadeLocal;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Problem;
import com.netcracker.testing.evaluation.EvaluationSystemRegistry;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class ModeratingCompetitionEJB {
    
    private static final String DEFAULT_COMPETITION_NAME = "New competition";
    private static final Integer DEFAULT_COMPETITION_INTERVAL = 120;
    private static final Integer DEFAULT_INTERVAL_FROZEN = 0;
    
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    @EJB(beanName = "CompetitionProblemFacade")
    private CompetitionProblemFacadeLocal competitionProblemFacade;
    
    public List<Competition> getAllCompetitions() {
        try {
            return competitionFacade.findAllCompetiotions();
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all competitions", throwable);
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Competition addNewCompetition() {
        try {
            Competition competition = new Competition();
            competition.setName(DEFAULT_COMPETITION_NAME);
            String evaluationType = EvaluationSystemRegistry.registry().getAvailableIds().iterator().next();
            competition.setEvaluationType(evaluationType);
            competition.setRegistrationType(RegistrationType.PUBLIC.toString().toLowerCase());
            competition.setHoldCompetition(true);
            competition.setCompetitionStart(getFutureDate());
            competition.setCompetitionInterval(DEFAULT_COMPETITION_INTERVAL);
            competition.setIntervalFrozen(DEFAULT_INTERVAL_FROZEN);
            competitionFacade.create(competition);
            String competitionFolder = competition.getId().toString();
            applicationEJB.getFileSupplier().addCompetitionFolder(competitionFolder);
            competition.setFolderName(competitionFolder);
            competitionFacade.edit(competition);
            return competition;
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while trying to add new competition", throwable);
            return null;
        }
    }
    
    private Date getFutureDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }
    
    public List<Problem> getAllValidatedProblems() {
        try {
            return problemFacade.findByValidated(true);
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while loading all validated problems", throwable);
            return Collections.EMPTY_LIST;
        }
    }
    
    public boolean addCompetitionProblem(Competition competition, Integer problemId, String problemNumber) {
        try {
            Problem problem = problemFacade.find(problemId);
            CompetitionProblem competitionProblem = new CompetitionProblem();
            competitionProblem.setProblemId(problem);
            competitionProblem.setCompetitionId(competition);
            competitionProblem.setProblemNumber(problemNumber);
            competition.getCompetitionProblemList().add(competitionProblem);
            competitionFacade.edit(competition);
            return true;
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while adding new competition problem", throwable);
            return false;
        }
    }
    
    public boolean removeCompetitionProblem(Competition competition, CompetitionProblem competitionProblem) {
        try {
            if (!competitionProblem.getParticipationResultList().isEmpty() ||
                    !competitionProblem.getSubmissionList().isEmpty()) {
//                System.out.println("Competition problem id: " + competitionProblem.getId());
//                System.out.println("ParticipationResult list: " + competitionProblem.getParticipationResultList().isEmpty());
//                if (!competitionProblem.getParticipationResultList().isEmpty()) {
//                    System.out.println(competitionProblem.getParticipationResultList().get(0).getId());
//                }
//                System.out.println("Submissions list: " + competitionProblem.getSubmissionList().isEmpty());
                return false;
            }
            competition.getCompetitionProblemList().remove(competitionProblem);
            competitionFacade.edit(competition);
            //competitionProblemFacade.remove(competitionProblem);
            //competitionFacade.loadCompetitionProblems(competition);
            return true;
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while removing competition problem", throwable);
            return false;
        }
    }
    
}