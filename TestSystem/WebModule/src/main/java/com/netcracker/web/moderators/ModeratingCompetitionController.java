package com.netcracker.web.moderators;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.moderating.ModeratingCompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Problem;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import com.netcracker.web.util.RegistrationTypeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@RequestScoped
public class ModeratingCompetitionController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    @EJB(beanName = "ModeratingCompetitionEJB")
    private ModeratingCompetitionEJB moderatingCompetitionEJB;

    private Competition competition = new Competition();
    private List<SelectItem> evaluationSystemItems = new ArrayList<>();
    private List<SelectItem> registrationTypeItems = new ArrayList<>();
    private List<SelectItem> validatedProblemItems = new ArrayList<>();
    private Integer chosenProblemId = null;
    private String problemNumber = null;

    @PostConstruct
    public void initController() {
        try {
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("id"));
            competition = competitionFacade.find(id);
            List<String> availableEvaluationSystems = competitionEJB.getAvailableEvaluationSystems();
            for (String name : availableEvaluationSystems) {
                evaluationSystemItems.add(new SelectItem(name, name.toUpperCase()));
            }
            List<String> availableRegistrationTypes = competitionEJB.getAvailableRegistrationTypes();
            Converter converter = new RegistrationTypeConverter();
            for (String type : availableRegistrationTypes) {
                registrationTypeItems.add(new SelectItem(type, converter.getAsString(null, null, type)));
            }
            updateProblemsList();
        } catch (Throwable throwable) {
            WebLogging.logger.log(Level.FINE, "Exception while loading moderation_competition.xhtml", throwable);
        }
    }

    private void updateProblemsList() {
        validatedProblemItems.clear();
        List<Problem> validatedProblems = moderatingCompetitionEJB.getAllValidatedProblems();
        for (Problem problem : validatedProblems) {
            boolean problemIsAlreadyInCompetition = false;
            for (CompetitionProblem competitionProblem : competition.getCompetitionProblemList()) {
                if (problem.getId().equals(competitionProblem.getProblemId().getId())) {
                    problemIsAlreadyInCompetition = true;
                }
            }
            if (problemIsAlreadyInCompetition) {
                continue;
            }
            validatedProblemItems.add(new SelectItem(problem.getId(), problem.getName()));
        }
        chosenProblemId = (validatedProblemItems.isEmpty() ? null : (Integer)validatedProblemItems.get(0).getValue());
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public CompetitionPhase getCompetitionPhase() {
        return competitionEJB.getCompetitionPhase(competition);
    }

    public List<SelectItem> getEvaluationSystemItems() {
        return evaluationSystemItems;
    }

    public List<SelectItem> getRegistrationTypeItems() {
        return registrationTypeItems;
    }

    public List<CompetitionProblem> getCompetitionProblems() {
        return competition.getCompetitionProblemList();
    }

    public List<SelectItem> getValidatedProblemItems() {
        return validatedProblemItems;
    }

    public String getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(String problemNumber) {
        this.problemNumber = problemNumber;
    }

    public Integer getChosenProblemId() {
        return chosenProblemId;
    }

    public void setChosenProblemId(Integer chosenProblemId) {
        this.chosenProblemId = chosenProblemId;
    }

    public void saveCompetitionChanges() {
        try {
            competitionFacade.edit(competition);
            JSFUtil.addInfoMessage("Информация о соревновании сохранена", "");
        } catch (Throwable throwable) {
            WebLogging.logger.log(Level.FINE, "Exception while trying to edit competition", throwable);
            JSFUtil.addErrorMessage("Ошибка при сохранении информации о соревновании", "");
        }
    }

    public void runSystemTesting() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }

    public void finalizeCompetition() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }

    public void addCompetitionProblem() {
        boolean addingResult = moderatingCompetitionEJB.addCompetitionProblem(
                competition, chosenProblemId, problemNumber);
        updateProblemsList();
        if (addingResult) {
            JSFUtil.addInfoMessage("Задача успешно добавлена в соревнование", "");
        } else {
            JSFUtil.addErrorMessage("Ошибка при добавлении задачи в соревнование", "");
        }
    }

    public void deleteCompetitionProblem(CompetitionProblem competitionProblem) {
        boolean deletingResult = moderatingCompetitionEJB.removeCompetitionProblem(competition, competitionProblem);
        updateProblemsList();
        if (deletingResult) {
            JSFUtil.addInfoMessage("Задача успешно удалена из соревнования", "");
        } else {
            JSFUtil.addErrorMessage("Ошибка при удалении задачи из соревнования", "");
        }
    }

}
