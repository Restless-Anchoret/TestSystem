package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Problem;
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
            return Collections.EMPTY_LIST;
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all problems", exception);
            return Collections.EMPTY_LIST;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Problem addNewProblem() {
        try {
            Problem problem = new Problem();
            problem.setName(DEFAULT_PROBLEM_NAME);
            problem.setType(DEFAULT_PROBLEM_TYPE);
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
    
}