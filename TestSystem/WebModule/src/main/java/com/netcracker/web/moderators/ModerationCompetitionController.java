package com.netcracker.web.moderators;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.entity.Competition;
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
public class ModerationCompetitionController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    
    private Competition competition = new Competition();
    private List<SelectItem> evaluationSystemItems = new ArrayList<>();
    private List<SelectItem> registrationTypeItems = new ArrayList<>();

    @PostConstruct
    public void initController() {
        try {
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("id"));
            competition = competitionFacade.find(id);
            List<String> availableEvaluationSystems = competitionEJB.getAvailableEvaluationSystems();
            for (String name: availableEvaluationSystems) {
                evaluationSystemItems.add(new SelectItem(name, name.toUpperCase()));
            }
            List<String> availableRegistrationTypes = competitionEJB.getAvailableRegistrationTypes();
            Converter converter = new RegistrationTypeConverter();
            for (String type: availableRegistrationTypes) {
                registrationTypeItems.add(new SelectItem(type, converter.getAsString(null, null, type)));
            }
        } catch (Exception exception) {
            WebLogging.logger.log(Level.FINE, "Exception while loading moderation_competition.xhtml", exception);
        }
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
    
    public void saveCompetitionChanges() {
        try {
            competitionFacade.edit(competition);
            JSFUtil.addInfoMessage("Информация о соревновании сохранена", "");
        } catch (Exception exception) {
            WebLogging.logger.log(Level.FINE, "Exception while trying to edit competition", exception);
            JSFUtil.addErrorMessage("Ошибка при сохранении информации о соревновании", "");
        }
    }
    
    public void runSystemTesting() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
    public void finalizeCompetition() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
}