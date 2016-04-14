package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.businesslogic.users.Role;
import com.netcracker.web.session.AuthenticationController;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
        String message = "", summary = "";
        switch (registrationResult.getInfo()) {
            case SUCCESS:
                authenticationEJB.tryAuthenticateUser(login, password);
                return "competitions.xhtml";
            case LOGIN_ALREADY_EXISTS:
                summary = "Данный логин уже занят";
                break;
            case UNSUITABLE_LOGIN:
                summary = "Некорректный логин";
                message = "Логин должен иметь от 4 до 30 символов и содержать " +
                        "только латинские буквы, цифры или символ _";
                break;
            case UNSUITABLE_PASSWORD:
                summary = "Некорректный пароль";
                message = "Пароль должен иметь от 4 до 40 символов и содержать " +
                        " хотя бы одну латинскую букву и хотя бы одну цифру";
                break;
            case NOT_AVAILABLE_LOGIN:
                summary = "Данный логин недоступен";
                break;
            case FAIL:
                summary = "Ошибка при регистрации";
                break;
        }
        addMessage(summary, message);
        return null;
    }
    
    private void addMessage(String summary, String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

}
