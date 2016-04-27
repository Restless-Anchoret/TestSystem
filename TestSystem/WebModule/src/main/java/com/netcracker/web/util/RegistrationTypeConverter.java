package com.netcracker.web.util;

import com.netcracker.businesslogic.holding.RegistrationType;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("registrationTypeConverter")
public class RegistrationTypeConverter implements Converter {

    private static final String PUBLIC_DESCRIPTION = "Открытое соревнование";
    private static final String MODERATION_DESCRIPTION = "Модерируемое соревнование";
    private static final String CLOSED_DESCRIPTION = "Закрытое соревнование";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case PUBLIC_DESCRIPTION: return RegistrationType.PUBLIC.toString().toLowerCase();
            case MODERATION_DESCRIPTION: return RegistrationType.MODERATION.toString().toLowerCase();
            case CLOSED_DESCRIPTION: return RegistrationType.CLOSED.toString().toLowerCase();
            default: return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String role = value.toString();
        if (RegistrationType.PUBLIC.toString().toLowerCase().equals(role)) {
            return PUBLIC_DESCRIPTION;
        } else if (RegistrationType.MODERATION.toString().toLowerCase().equals(role)) {
            return MODERATION_DESCRIPTION;
        } else if (RegistrationType.CLOSED.toString().toLowerCase().equals(role)) {
            return CLOSED_DESCRIPTION;
        } else {
            return null;
        }
    }
    
}