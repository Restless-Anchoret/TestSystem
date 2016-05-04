package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingProblemEJB;
import com.netcracker.database.entity.Competition;
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
    
    public List<Problem> getProblemsList() {
        return moderatingProblemEJB.getAllProblems();
    }
    
    public void addNewProblem() {
        Problem problem = moderatingProblemEJB.addNewProblem();
        if (problem == null) {
            JSFUtil.addErrorMessage("Exception while adding problem", "");
        }
    }

}
