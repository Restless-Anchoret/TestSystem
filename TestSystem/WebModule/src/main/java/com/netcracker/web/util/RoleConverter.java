package com.netcracker.web.util;

import com.netcracker.businesslogic.users.Role;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("roleConverter")
public class RoleConverter implements Converter {

    private static final String PARTICIPANT_DESCRIPTION = "Участник";
    private static final String MODERATOR_DESCRIPTION = "Модератор";
    private static final String ADMIN_DESCRIPTION = "Администратор";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case PARTICIPANT_DESCRIPTION: return Role.PARTICIPANT.toString().toLowerCase();
            case MODERATOR_DESCRIPTION: return Role.MODERATOR.toString().toLowerCase();
            case ADMIN_DESCRIPTION: return Role.ADMIN.toString().toLowerCase();
            default: return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String role = value.toString();
        if (Role.PARTICIPANT.toString().toLowerCase().equals(role)) {
            return PARTICIPANT_DESCRIPTION;
        } else if (Role.MODERATOR.toString().toLowerCase().equals(role)) {
            return MODERATOR_DESCRIPTION;
        } else if (Role.ADMIN.toString().toLowerCase().equals(role)) {
            return ADMIN_DESCRIPTION;
        } else {
            return null;
        }
    }

}