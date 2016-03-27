package com.netcracker.businesslogic.users;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class RegistrationEJB {

    public enum Result {
        SUCCESS,
        NOT_AVAILABLE_LOGIN,
        LOGIN_ALREADY_EXISTS,
        SIMPLE_PASSWORD
    }
    
    public Result tryRegistrate(String user, String password) {
        return null;
    }
    
}
