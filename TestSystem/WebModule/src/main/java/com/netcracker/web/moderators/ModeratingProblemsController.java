package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingProblemEJB;
import com.netcracker.database.entity.Problem;
import com.netcracker.web.util.JSFUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ModeratingProblemsController {

    @EJB(beanName = "ModeratingProblemEJB")
    private ModeratingProblemEJB moderatingProblemEJB;
    private String newName;
    
    public List<Problem> getProblemsList() {
        return moderatingProblemEJB.getAllProblems();
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
    public String getValidatedDescription(Problem problem) {
        if (problem.getValidated()) {
            return "Проверена";
        } else {
            return "Не проверена";
        }
    }
    
    public void addNewProblem() {
        if (newName != null && newName.isEmpty()) {
            newName = null;
        }
        Problem problem = moderatingProblemEJB.addNewProblem(newName);
        if (problem == null) {
            JSFUtil.addErrorMessage("Ошибка при добавлении задачи", "");
        }
    }
    
    public void deleteProblem(Problem problem) {
        String problemName = problem.getName();
        boolean deletingResult = moderatingProblemEJB.removeProblem(problem);
        if (deletingResult) {
            JSFUtil.addInfoMessage("Задача " + problemName + " успешно удалена", "");
        } else {
            JSFUtil.addErrorMessage("Задача " + problemName + " не может быть удалена", "");
        }
    }

}
