package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingUserEJB;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.database.entity.User;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ModeratingUsersController {

    @EJB(beanName = "ModeratingUserEJB")
    private ModeratingUserEJB moderatingUserEJB;
    @EJB(beanName = "RegistrationEJB")
    private RegistrationEJB registrationEJB;
    
    private String newLogin = null;

    public List<User> getParticipants() {
        return moderatingUserEJB.getAllParticipants();
    }
    
    public List<User> getModerators() {
        return moderatingUserEJB.getAllModerators();
    }
    
    public String getActualityDescription(User user) {
        if (user.getActual()) {
            return "Активен";
        } else {
            return "Не активен";
        }
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }
    
    public void addNewParticipant() {
        checkNewLogin();
        processRegistrationResult(registrationEJB.addNewParticipant(newLogin));
    }
    
    public void addNewModerator() {
        checkNewLogin();
        processRegistrationResult(registrationEJB.addNewModerator(newLogin));
    }
    
    private void checkNewLogin() {
        if (newLogin != null && newLogin.isEmpty()) {
            newLogin = null;
        }
    }
    
    private void processRegistrationResult(RegistrationEJB.Result result) {
        if (result.getInfo().equals(RegistrationEJB.Info.SUCCESS)) {
            JSFUtil.addInfoMessage("Пользователь успешно добавлен",
                    "Логин нового пользователя: " + result.getUser().getLogin());
        } else {
            JSFUtil.addErrorMessage("Ошибка при добавлении пользователя", "");
        }
    }
    
    public void deleteUser(User user) {
        String userLogin = user.getLogin();
        boolean deletingResult = moderatingUserEJB.removeUser(user);
        if (deletingResult) {
            JSFUtil.addInfoMessage("Пользователь " + userLogin + " успешно удалён", "");
        } else {
            JSFUtil.addErrorMessage("Пользователь " + userLogin + " не может быть удалён", "");
        }
    }
    
}
