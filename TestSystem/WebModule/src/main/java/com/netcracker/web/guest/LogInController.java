package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.web.session.AuthenticationController;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
public class LogInController {

    private String login;
    private String password;
    private AuthenticationEJB authenticationEJB;
    
    public LogInController() { }
    
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
    
    public String doAuthentication() {
        AuthenticationEJB.Result authenticationResult = authenticationEJB
                .tryAuthenticateUser(login, password);
        String message = null;
        switch (authenticationResult.getInfo()) {
            case SUCCESS:
                return "competitions.xhtml";
            case REFUSE:
                message = "Неверные комбинация логина и пароля";
                break;
            case FAIL:
                message = "Ошибка при аутентификации";
                break;
        }
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return null;
    }

}
