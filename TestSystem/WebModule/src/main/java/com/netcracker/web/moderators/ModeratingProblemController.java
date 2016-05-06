package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingProblemEJB;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Problem;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.CheckerTypeConverter;
import com.netcracker.web.util.JSFUtil;
import com.netcracker.web.util.ProblemTypeConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

@Named
@RequestScoped
public class ModeratingProblemController {

    @EJB(beanName = "ModeratingProblemEJB")
    private ModeratingProblemEJB moderatingProblemEJB;
    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    
    private Problem problem = new Problem();
    private List<SelectItem> problemTypeItems = new ArrayList<>();
    private List<SelectItem> checkerTypeItems = new ArrayList<>();

    @PostConstruct
    public void initController() {
        try {
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("id"));
            problem = problemFacade.find(id);
            List<String> availableProblemTypes = moderatingProblemEJB.getAvailableProblemTypes();
            Converter problemTypeConverter = new ProblemTypeConverter();
            for (String type: availableProblemTypes) {
                problemTypeItems.add(new SelectItem(type, problemTypeConverter.getAsString(null, null, type)));
            }
            List<String> availableCheckerTypes = moderatingProblemEJB.getAvailableCheckerTypes();
            Converter checkerTypeConverter = new CheckerTypeConverter();
            for (String type: availableCheckerTypes) {
                checkerTypeItems.add(new SelectItem(type, checkerTypeConverter.getAsString(null, null, type)));
            }
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
    
    public List<SelectItem> getProblemTypeItems() {
        return problemTypeItems;
    }
    
    public List<SelectItem> getCheckerTypeItems() {
        return checkerTypeItems;
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
    
    public void loadTests() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }

}
