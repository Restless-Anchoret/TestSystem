package com.netcracker.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("decisionTimeConverter")
public class DecisionTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Integer decisionTime = (Integer)value;
        if (decisionTime == null) {
            return "";
        } else {
            return decisionTime + " ms";
        }
    }

}