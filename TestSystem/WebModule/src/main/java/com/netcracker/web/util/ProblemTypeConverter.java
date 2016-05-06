package com.netcracker.web.util;

import com.netcracker.web.logging.WebLogging;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("problemTypeConverter")
public class ProblemTypeConverter implements Converter {

    private static final String CODING_DESCRIPTION = "Задача по программированию";
    private static final String CODING_IDENTIFIER = "coding";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case CODING_DESCRIPTION: return CODING_IDENTIFIER;
            default: return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String type = value.toString();
        if (CODING_IDENTIFIER.equals(type)) {
            return CODING_DESCRIPTION;
        } else {
            return null;
        }
    }
    
}