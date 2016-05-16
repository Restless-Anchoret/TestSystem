package com.netcracker.web.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JSFUtil {
    
    public static void addMessage(String summary, String detail, Severity severity) {
        FacesMessage facesMessage = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public static void addErrorMessage(String summary, String detail) {
        addMessage(summary, detail, FacesMessage.SEVERITY_ERROR);
    }
    
    public static void addInfoMessage(String summary, String detail) {
        addMessage(summary, detail, FacesMessage.SEVERITY_INFO);
    }
    
    public static HttpServletRequest getHttpRequest() {
        return (HttpServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
    
    public static String getRequestParameter(String parameter) {
        HttpServletRequest request = getHttpRequest();
        return request.getParameter(parameter);
    }
    
    public static Path getRequestURIPath() {
        HttpServletRequest request = getHttpRequest();
        return Paths.get(request.getRequestURI());
    }
    
    private JSFUtil() { }
    
}