package com.netcracker.controller;

import com.netcracker.database.dal.*;
import com.netcracker.database.entity.*;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@RequestScoped
@Named(value="testController")
public class TestsController {
    
    private String resultUserFacade;
    
    private User user;
    
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    
    public void testing(ActionEvent actionEvent) {
        testingUserFacade();
    }

    private void testingUserFacade() {
        newUser("Sergey", "participant");
        userFacade.create(user);
        user = null;
        
        user = userFacade.findByLogin("Sergey");
        int id = user.getId();
        if (!user.getLogin().equals("Sergey")) {
            resultUserFacade = "Failed findByLogin";
            return;
        }
        user = null;
        
        user = userFacade.find(id);
        if (!user.getLogin().equals("Sergey")) {
            resultUserFacade = "Failed find";
            return;
        }
        
        user.setActual(true);
        userFacade.edit(user);
        user = null;
        
        user = userFacade.find(id);
        if (!user.getLogin().equals("Sergey") || !user.getActual()) {
            resultUserFacade = "Failed edit";
            return;
        }
        
        userFacade.remove(user);
        user = null;
        user = userFacade.find(id);
        if (user != null) {
            resultUserFacade = "Failed remove";
            return;
        }
        
        newUser("Sergey", "admin");
        userFacade.create(user);
        user = null;
        user = userFacade.getAdmin();
        if (!user.getLogin().equals("Sergey")) {
            resultUserFacade = "Failed getAdmin";
            return;
        }
        userFacade.remove(user);
        
        newUser("SergeyM", "moderator");
        userFacade.create(user);
        user = null;
        
        newUser("AntonM", "moderator");
        userFacade.create(user);
        user = null;
        
        newUser("IlyaM", "moderator");
        userFacade.create(user);
        user = null;
        
        newUser("SergeyP", "participant");
        userFacade.create(user);
        user = null;
        
        newUser("AntonP", "participant");
        userFacade.create(user);
        user = null;
        
        newUser("IlyaP", "participant");
        userFacade.create(user);
        user = null;
        
        List<User> moderators = userFacade.getModerators();
        if (moderators.size() != 3) {
            resultUserFacade = "Failed getModerators";
            return;
        }
        for (User u: moderators)
            if (!u.getRole().equals("moderator")) {
                resultUserFacade = "Failed getModerators";
                return;
            }
        
        List<User> participants = userFacade.getParticipants();
        if (moderators.size() != 3) {
            resultUserFacade = "Failed getParticipants";
            return;
        }
        for (User u: participants)
            if (!u.getRole().equals("participant")) {
                resultUserFacade = "Failed getParticipants";
                return;
            }
        for (User u: moderators)
            userFacade.remove(u);
        for (User u: participants)
            userFacade.remove(u);
        
        resultUserFacade = "SUCCESS";
    }
    
    private void newUser(String name, String role) {
        user = new User();
        user.setLogin(name);
        user.setPasswordHash("qwerty");
        user.setRegistrationDate(new Date());
        user.setRole(role);
    } 
    
    public String getResultUserFacade() {
        return resultUserFacade;
    }

    public void setResultUserFacade(String resultUserFacade) {
        this.resultUserFacade = resultUserFacade;
    }
    
}
