package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.JSFUtil;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

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
        String summary = null;
        switch (authenticationResult.getInfo()) {
            case SUCCESS:
                return "/participant/competitions.xhtml";
            case REFUSE:
                summary = "Неверные комбинация логина и пароля";
                break;
            case FAIL:
                summary = "Ошибка при аутентификации";
                break;
        }
        JSFUtil.addErrorMessage(summary, "");
        return null;
    }

}
