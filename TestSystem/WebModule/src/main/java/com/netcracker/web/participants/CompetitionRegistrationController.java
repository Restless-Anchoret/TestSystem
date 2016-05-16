package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.RegistrationType;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.ParticipationFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.PersonalData;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.JSFUtil;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class CompetitionRegistrationController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "ParticipationFacade")
    private ParticipationFacadeLocal participationFacade;
    private AuthenticationEJB authenticationEJB;
    private Competition competition;
    private PersonalData personalData;
    private boolean alreadyMadeRequest = false;
    private String patronymic; 

    @PostConstruct
    public void initController() {
        competition = new Competition();
        personalData = new PersonalData();
        try {
            authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("competitionId"));
            competition = competitionFacade.find(id);
            Participation participation = participationFacade.findByCompetitionIdAndUserId(
                    competition.getId(), authenticationEJB.getCurrentUser().getId());
            if (participation != null)
                alreadyMadeRequest = true;
        } catch (Throwable exception) {
            WebLogging.logger.log(Level.FINE, "Exception while loading competition", exception);
        }
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }
    
    public boolean isNeedPersonalData() {
        return !RegistrationType.PUBLIC.toString().toLowerCase().equals(competition.getRegistrationType());
    }
    
    public String getRegistrationStatus() {
        return (alreadyMadeRequest ? "Подана заявка на регистрацию" : "Не зарегистрирован");
    }

    public void registrateForCompetition() {
        try {
            if (isNeedPersonalData()) {
                personalData.setPatronymic(patronymic);
                competitionFacade.registrationNewParticipation(competition, authenticationEJB.getCurrentUser(), personalData);
            } else {
                competitionFacade.registrationNewParticipation(competition, authenticationEJB.getCurrentUser());
            }
            alreadyMadeRequest = true;
            JSFUtil.addInfoMessage("Регистрация на соревнование прошла успешно", "");
        } catch (Throwable exception) {
            JSFUtil.addErrorMessage("Ошибка при регистрации", 
                    "Произошла ошибка при регистрации, повторите попытку позже.");
            WebLogging.logger.log(Level.SEVERE, null, exception);
        }
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        if (patronymic.equals(""))
            this.patronymic = null;
        else
            this.patronymic = patronymic;
    }

    public boolean isViewRegistrationButton() {
        if (authenticationEJB.getCurrentUser().getRole().equals("admin") ||
                authenticationEJB.getCurrentUser().getRole().equals("moderator"))
            return false;
        else
            return !alreadyMadeRequest;
    }
    
}
