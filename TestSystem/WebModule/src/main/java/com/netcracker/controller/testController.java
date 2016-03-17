/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.controller;

import com.netcracker.dal.UserFacadeLocal;
import com.netcracker.entity.User;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;


/**
 *
 * @author Ataman
 */
@RequestScoped
@Named(value="testController")
public class testController {
    
    private String login;
    private String password;
    
    private User user;
    
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    public void addUser(ActionEvent actionEvent) {
        user = new User();
        user.setLogin(login);
        user.setPasswordHash(password);
        user.setRegistrationDate(new Date());
        user.setRole("admin");
        userFacade.create(user);
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
