package com.netcracker.web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("compilatorNameConverter")
public class CompilatorNameConverter implements Converter {

    private static final String JAVA = "Java 1.8";
    private static final String VISUAL_CPP = "Microsoft Visual C++";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case JAVA:
                return "java";
            case VISUAL_CPP:
                return "visual_cpp";
            default:
                    return value;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String name = (String) value;
        switch (name.toLowerCase()) {
            case "java":
                return JAVA;
            case "visual_cpp":
                return VISUAL_CPP;
            default:
                    return name;
        }
    }

}
