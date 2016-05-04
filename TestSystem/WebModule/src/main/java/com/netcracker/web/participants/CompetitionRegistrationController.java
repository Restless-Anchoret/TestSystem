package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.RegistrationType;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
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
    private AuthenticationEJB authenticationEJB;
    private Competition competition = new Competition();
    private PersonalData personalData = new PersonalData();
    private boolean alreadyMadeRequest = false;

    @PostConstruct
    public void initController() {
        try {
            authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
            Integer id = Integer.parseInt(JSFUtil.getRequestParameter("id"));
            competition = competitionFacade.find(id);
            for (Participation participation: competition.getParticipationList()) {
                if (participation.getUserId().getLogin().equals(authenticationEJB.getCurrentUser().getLogin())) {
                    alreadyMadeRequest = true;
                    break;
                }
            }
        } catch (Exception exception) {
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

    public boolean isAlreadyMadeRequest() {
        return alreadyMadeRequest;
    }

    public void setAlreadyMadeRequest(boolean alreadyMadeRequest) {
        this.alreadyMadeRequest = alreadyMadeRequest;
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
                competitionFacade.registrationNewParticipation(competition, authenticationEJB.getCurrentUser(), personalData);
            } else {
                competitionFacade.registrationNewParticipation(competition, authenticationEJB.getCurrentUser());
            }
            JSFUtil.addInfoMessage("Регистрация на соревнование прошла успешно", "");
        } catch (Exception exception) {
            JSFUtil.addErrorMessage("Ошибка при регистрации", "");
        }
    }

}
