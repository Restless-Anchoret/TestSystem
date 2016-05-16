package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.PasswordChangeEJB;
import com.netcracker.businesslogic.users.Role;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.Submission;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.User;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.JSFUtil;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import com.netcracker.monitoring.info.CompetitionPhase;
import java.nio.file.Path;

@Named
@RequestScoped
public class ProfileController {

    @EJB(beanName = "PasswordChangeEJB")
    private PasswordChangeEJB passwordChangeEJB;
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;

    private User user;
    private String oldPassword;
    private String newPassword;
    private AuthenticationEJB authenticationEJB;
    private List<Submission> submissions;
    private SubmissionFacadeLocal submissionFacade;
    private Integer competitionId;
    private List<Participation> participations;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;

    public ProfileController() {
    }

    @PostConstruct
    public void initController() {
        try {
            authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
            String userId = JSFUtil.getRequestParameter("userId");
            if (userId == null) {
                user = authenticationEJB.getCurrentUser();
            } else {
                user = userFacade.find(Integer.parseInt(userId));
            }
            Path path = JSFUtil.getRequestURIPath();
            String pageName = path.getFileName().toString();
            if (pageName.equals("user_submissions.xhtml")) {
                initUserSubmissions();
            }
        } catch (Exception exception) {
            WebLogging.logger.log(Level.FINE, "Exception while loading profile page", exception);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserActual() {
        return (user.getActual() ? "Активен" : "Не активен");
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isProfileOfCurrentUser() {
        return authenticationEJB.getCurrentUser().getId().equals(user.getId());
    }

    public boolean isNeedShowPasswordChanging() {
        User currentUser = authenticationEJB.getCurrentUser();
        return currentUser.equals(user) ||
                currentUser.getRole().equals(Role.ADMIN.toString().toLowerCase()) ||
                currentUser.getRole().equals(Role.MODERATOR.toString().toLowerCase()) &&
                user.getRole().equals(Role.PARTICIPANT.toString().toLowerCase());
    }
    
    public boolean isNeedShowActuality() {
        User currentUser = authenticationEJB.getCurrentUser();
        return currentUser.getRole().equals(Role.ADMIN.toString().toLowerCase()) &&
                !user.getRole().equals(Role.ADMIN.toString().toLowerCase()) ||
                currentUser.getRole().equals(Role.MODERATOR.toString().toLowerCase()) &&
                user.getRole().equals(Role.PARTICIPANT.toString().toLowerCase());
    }

    public void doChangePassword() {
        PasswordChangeEJB.Info resultInfo = passwordChangeEJB.tryChangePassword(user, oldPassword, newPassword);
        String summary = "";
        switch (resultInfo) {
            case SUCCESS:
                JSFUtil.addInfoMessage("Пароль изменён", "");
                return;
            case WRONG_OLD_PASSWORD:
                summary = "Неверный старый пароль";
                break;
            case FAIL:
                summary = "Ошибка при смене пароля";
                break;
        }
        JSFUtil.addErrorMessage(summary, "");
    }

    public void doChangeActuality() {
        boolean actualInStart = user.getActual();
        try {
            user.setActual(!user.getActual());
            userFacade.edit(user);
            JSFUtil.addInfoMessage("Активность пользователя успешно изменена", "");
        } catch (Exception exception) {
            user.setActual(actualInStart);
            WebLogging.logger.log(Level.FINE, "Exception while changing actuality of user", exception);
            JSFUtil.addErrorMessage("Ошибка при изменении активности пользователя", "");
        }
    }

    public void initUserAchievements() {
        participations = user.getParticipationList();
    }

    public void initUserSubmissions() {
        submissions = user.getSubmissionList();
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public CompetitionEJB getCompetitionEJB() {
        return competitionEJB;
    }

    public void setCompetitionEJB(CompetitionEJB competitionEJB) {
        this.competitionEJB = competitionEJB;
    }

    public String getTypeDescription(Participation participation) {
        if (participation.getRegistered()) {
            return "Зарегистрирован";
        } else {
            return "Не зарегистрирован";
        }
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}
