package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingUserEJB;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.database.entity.User;
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

    public List<User> getParticipants() {
        return moderatingUserEJB.getAllParticipants();
    }
    
    public List<User> getModerators() {
        return moderatingUserEJB.getAllModerators();
    }
    
    public void addNewParticipant() {
        processRegistrationResult(registrationEJB.addNewParticipant());
    }
    
    public void addNewModerator() {
        processRegistrationResult(registrationEJB.addNewModerator());
    }
    
    public void processRegistrationResult(RegistrationEJB.Result result) {
        if (result.getInfo().equals(RegistrationEJB.Info.SUCCESS)) {
            JSFUtil.addInfoMessage("Пользователь успешно добавлен",
                    "Логин нового пользователя: " + result.getUser().getLogin());
        } else {
            JSFUtil.addErrorMessage("Ошибка при добавлении пользователя", "");
        }
    }
    
}
