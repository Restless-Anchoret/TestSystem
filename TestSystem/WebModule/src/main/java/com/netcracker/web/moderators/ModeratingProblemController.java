package com.netcracker.web.moderators;

import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Problem;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ModeratingProblemController {

    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    private Problem problem = new Problem();

    @PostConstruct
    public void initController() {
        try {
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("id"));
            problem = problemFacade.find(id);
        } catch (Exception exception) {
            WebLogging.logger.log(Level.FINE, "Exception while loading moderation_problem.xhtml", exception);
        }
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    
    public void saveProblemChanges() {
        try {
            problemFacade.edit(problem);
            JSFUtil.addInfoMessage("Информация о задаче сохранена", "");
        } catch (Exception exception) {
            WebLogging.logger.log(Level.FINE, "Exception while trying to edit problem", exception);
            JSFUtil.addErrorMessage("Ошибка при сохранении информации о задаче", "");
        }
    }

}
