package com.netcracker.web.util;

import com.netcracker.testing.system.Verdict;
import com.netcracker.testing.system.VerdictInfo;
import java.util.Properties;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("verdictInfoConverter")
public class VerdictInfoConverter implements Converter {

    private static Properties presentationProperties = null;
    
    private static Properties getPresentationProperties() {
        if (presentationProperties == null) {
            presentationProperties = new Properties();
            presentationProperties.setProperty(Verdict.NOT_TESTED.toString(), "Not tested");
            presentationProperties.setProperty(Verdict.WAITING.toString(), "Waiting");
            presentationProperties.setProperty(Verdict.TESTING.toString(), "Testing");
            presentationProperties.setProperty(Verdict.ACCEPTED.toString(), "Accepted");
            presentationProperties.setProperty(Verdict.PRETESTS_ACC.toString(), "Pretests accepted");
            presentationProperties.setProperty(Verdict.PARTIAL_ACC.toString(), "Partial accepted");
            presentationProperties.setProperty(Verdict.COMPILE_ERROR.toString(), "Compilation error");
            presentationProperties.setProperty(Verdict.RUNTIME_ERROR.toString(), "Runtime error");
            presentationProperties.setProperty(Verdict.WRONG_ANSWER.toString(), "Wrong answer");
            presentationProperties.setProperty(Verdict.TIME_LIMIT.toString(), "Time limit exceeded");
            presentationProperties.setProperty(Verdict.MEMORY_LIMIT.toString(), "Memory limit exceeded");
            presentationProperties.setProperty(Verdict.SECUR_VIOL.toString(), "Security violation");
            presentationProperties.setProperty(Verdict.FAIL.toString(), "Fail");
        }
        return presentationProperties;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        VerdictInfo verdictInfo = (VerdictInfo)value;
        Properties properties = getPresentationProperties();
        String description = properties.getProperty(verdictInfo.getVerdict().toString());
        if (verdictInfo.getPoints() != null) {
            description += " with " + verdictInfo.getPoints() + " points";
        } else if (verdictInfo.getWrongTestNumber() != null) {
            description += " on test " + verdictInfo.getWrongTestNumber();
        }
        return description;
    }
    
}