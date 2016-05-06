package com.netcracker.web.util;

import com.netcracker.web.logging.WebLogging;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("checkerTypeConverter")
public class CheckerTypeConverter implements Converter {

    private static final String MATCH_DESCRIPTION = "Совпадение с ответом жюри";
    private static final String MATCH_IDENTIFIER = "match";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case MATCH_DESCRIPTION: return MATCH_IDENTIFIER;
            default: return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String type = value.toString();
        if (MATCH_IDENTIFIER.equals(type)) {
            return MATCH_DESCRIPTION;
        } else {
            return null;
        }
    }
    
}