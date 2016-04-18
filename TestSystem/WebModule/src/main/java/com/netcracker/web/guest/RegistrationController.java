package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.businesslogic.users.Role;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.JSFUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class RegistrationController {

    @EJB(beanName = "RegistrationEJB")
    private RegistrationEJB registrationEJB;
    
    private String login;
    private String password;
    private AuthenticationEJB authenticationEJB;
    
    public RegistrationController() { }

    @PostConstruct
    public void initController() {
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String doRegistration() {
        RegistrationEJB.Result registrationResult = registrationEJB
                .tryRegistrateActual(login, password, Role.PARTICIPANT);
        String summary = "";
        switch (registrationResult.getInfo()) {
            case SUCCESS:
                authenticationEJB.tryAuthenticateUser(login, password);
                return "competitions.xhtml";
            case LOGIN_ALREADY_EXISTS:
                summary = "Данный логин уже занят";
                break;
            case NOT_AVAILABLE_LOGIN:
                summary = "Данный логин недоступен";
                break;
            case FAIL:
                summary = "Ошибка при регистрации";
                break;
        }
        JSFUtil.addErrorMessage(summary, "");
        return null;
    }

}
