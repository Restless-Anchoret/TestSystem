package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.web.session.AuthenticationController;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class LogOutController {
    
    private AuthenticationEJB authenticationEJB;
    
    public LogOutController() { }
    
    @PostConstruct
    public void initController() {
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
    }
    
    public String doLogOut() {
        authenticationEJB.logOut();
        return "log_in.xhtml";
    }

}