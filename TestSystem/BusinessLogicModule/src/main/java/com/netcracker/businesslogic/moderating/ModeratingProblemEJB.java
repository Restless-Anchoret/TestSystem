package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Problem;
import com.netcracker.testing.checker.CheckerRegistry;
import com.netcracker.testing.tester.ProblemTesterRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@LocalBean
public class ModeratingProblemEJB {

    private static final String DEFAULT_PROBLEM_NAME = "New problem";
    private static final String DEFAULT_PROBLEM_TYPE = "coding";
    
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    
    public List<Problem> getAllProblems() {
        try {
            return problemFacade.findAll();
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all problems", exception);
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Problem addNewProblem(String problemName) {
        if (problemName == null) {
            problemName = DEFAULT_PROBLEM_NAME;
        }
        try {
            Problem problem = new Problem();
            problem.setName(problemName);
            problem.setType(DEFAULT_PROBLEM_TYPE);
            problem.setFolderName("0");
            problem.setCheckerType("match");
            problem.setDescriptionFileExists(false);
            problem.setTimeLimit(1000);
            problem.setMemoryLimit((short)64);
            problem.setValidated(false);
            problemFacade.create(problem);
            String problemFolder = problem.getId().toString();
            applicationEJB.getFileSupplier().addProblemFolder(problemFolder);
            problem.setFolderName(problemFolder);
            problemFacade.edit(problem);
            return problem;
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while trying to add new problem", exception);
            return null;
        }
    }
    
    public List<String> getAvailableProblemTypes() {
        return new ArrayList<>(ProblemTesterRegistry.registry().getAvailableIds());
    }
    
    public List<String> getAvailableCheckerTypes() {
        return new ArrayList<>(CheckerRegistry.registry().getAvailableIds());
    }
    
    public boolean removeProblem(Problem problem) {
        try {
            if (!problem.getAuthorDecisionList().isEmpty() ||
                    !problem.getCompetitionProblemList().isEmpty() ||
                    !problem.getTestGroupList().isEmpty()) {
                return false;
            }
            problemFacade.remove(problem);
            return true;
        } catch (Throwable throwable) {
            BusinessLogicLogging.logger.log(Level.INFO, "Exception while removing problem", throwable);
            return false;
        }
    }
    
}