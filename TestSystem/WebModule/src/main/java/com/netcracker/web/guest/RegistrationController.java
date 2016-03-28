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

    @EJB
    private RegistrationEJB registrationEJB;
    
    private String login;
    private String password;
    private String passwordAgain;
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

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
    
    public String doRegistration() {
        if (!Objects.equals(password, passwordAgain)) {
            addMessage("Пароли не совпадают");
            return null;
        }
        RegistrationEJB.Result registrationResult = registrationEJB
                .tryRegistrateActual(login, password, Role.PARTICIPANT);
        String message = null;
        switch (registrationResult.getInfo()) {
            case SUCCESS:
                authenticationEJB.tryAuthenticateUser(login, password);
                return "competitions.xhtml";
            case LOGIN_ALREADY_EXISTS:
                message = "Данный логин уже занят";
                break;
            case UNSUITABLE_LOGIN:
                message = "Логин должен иметь от 4 до 30 символов и содержать " +
                        "только латинские буквы, цифры или символ _";
                break;
            case UNSUITABLE_PASSWORD:
                message = "Пароль должен иметь от 4 до 40 символов и содержать " +
                        " хотя бы одну латинскую букву и хотя бы одну цифру";
                break;
            case NOT_AVAILABLE_LOGIN:
                message = "Данный логин недоступен";
                break;
            case FAIL:
                message = "Ошибка при регистрации";
                break;
        }
        addMessage(message);
        return null;
    }
    
    private void addMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

}
