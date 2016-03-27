package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.businesslogic.users.Role;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class RegistrationController {

    @EJB
    private RegistrationEJB registrationEJB;
    
    private String login;
    private String password;
    private String passwordAgain;
    
    public RegistrationController() { }

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
        RegistrationEJB.Result registrationResult = registrationEJB
                .tryRegistrateActual(login, password, Role.PARTICIPANT);
        switch (registrationResult.getInfo()) {
            case SUCCESS:
                break;
            default:
                break;
        }
        return null;
    }

}
