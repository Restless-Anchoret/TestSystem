package com.netcracker.web.session;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

@Named
@SessionScoped
public class AuthenticationController implements Serializable {
    
    public static AuthenticationEJB getSessionAuthenticationEJB() {
        AuthenticationController controller = getSessionAuthenticationController();
        return (controller != null ? controller.getAuthenticationEJB() : null);
    }
    
    public static AuthenticationController getSessionAuthenticationController() {
        FacesContext context = FacesContext.getCurrentInstance();
        AuthenticationController controller = (AuthenticationController)context
                .getApplication().getELResolver()
                .getValue(context.getELContext(), null, "authenticationController");
        return controller;
    }
    
    @EJB(beanName = "AuthenticationEJB")
    private AuthenticationEJB authenticationEJB;
    
    public AuthenticationController() { }

    public AuthenticationEJB getAuthenticationEJB() {
        return authenticationEJB;
    }
    
    public User getCurrentUser() {
        return authenticationEJB.getCurrentUser();
    }
    
    public String getCurrentUserLogin() {
        User user = authenticationEJB.getCurrentUser();
        return (user != null ? user.getLogin() : null);
    }
    
    public boolean isAuthenticated() {
        return authenticationEJB.getCurrentUser() != null;
    }
    
    public boolean isAuthenticatedParticipant() {
        User user = authenticationEJB.getCurrentUser();
        return user != null && user.getRole().equals("participant");
    }
    
    public boolean isAuthenticatedModerator() {
        User user = authenticationEJB.getCurrentUser();
        return user != null && user.getRole().equals("moderator");
    }
    
    public boolean isAuthenticatedAdmin() {
        User user = authenticationEJB.getCurrentUser();
        return user != null && user.getRole().equals("admin");
    }
    
    public boolean isAuthenticatedModeratorOrAdmin() {
        User user = authenticationEJB.getCurrentUser();
        return user != null && (user.getRole().equals("moderator") ||
                user.getRole().equals("admin"));
    }
    
    public String getCurrentUserRole() {
        User user = authenticationEJB.getCurrentUser();
        return (user != null ? user.getRole() : null);
    }

}