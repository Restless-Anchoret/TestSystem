package com.netcracker.businesslogic.support;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionMediator {
    
    public static Object getSessionParameter(String parameter) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return session.getAttribute(parameter);
    }
    
    public static String getSessionStringParameter(String parameter) {
        return (String)getSessionParameter(parameter);
    }
    
    public static void setSessionParameter(String parameter, Object value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute(parameter, value);
    }
    
    private SessionMediator() { }
    
}