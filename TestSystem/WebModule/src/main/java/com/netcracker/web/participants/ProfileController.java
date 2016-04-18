package com.netcracker.web.participants;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.PasswordChangeEJB;
import com.netcracker.database.entity.User;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.JSFUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ProfileController {

    @EJB(beanName = "PasswordChangeEJB")
    private PasswordChangeEJB passwordChangeEJB;
    
    private String oldPassword;
    private String newPassword;
    private AuthenticationEJB authenticationEJB;
    
    public ProfileController() { }
    
    @PostConstruct
    public void initController() {
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
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
    
    public void doChangePassword() {
        User user = authenticationEJB.getCurrentUser();
        PasswordChangeEJB.Info resultInfo = passwordChangeEJB.tryChangePassword(user, oldPassword, newPassword);
        String summary = "";
        switch (resultInfo) {
            case SUCCESS: JSFUtil.addInfoMessage("Пароль изменён", ""); return;
            case WRONG_OLD_PASSWORD: summary = "Неверный старый пароль"; break;
            case FAIL: summary = "Ошибка при смене пароля"; break;
        }
        JSFUtil.addErrorMessage(summary, "");
    }

}
