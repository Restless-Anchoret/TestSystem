package com.netcracker.web.participants;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.PasswordChangeEJB;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.entity.User;
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
public class ProfileController {

    @EJB(beanName = "PasswordChangeEJB")
    private PasswordChangeEJB passwordChangeEJB;
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    private User user;
    private String oldPassword;
    private String newPassword;
    private AuthenticationEJB authenticationEJB;
    
    public ProfileController() { }
    
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
        return user.getId().equals(authenticationEJB.getCurrentUser().getId());
    }
    
    public void doChangePassword() {
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
