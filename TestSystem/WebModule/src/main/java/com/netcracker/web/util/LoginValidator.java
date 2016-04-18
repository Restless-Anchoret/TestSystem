package com.netcracker.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    private static final String MESSAGE_SUMMARY = "Некорректный логин";
    private static final String MESSAGE_DETAIL = "Логин должен иметь от 4 до 30 " +
            "символов и содержать только латинские буквы, цифры или символ _";
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String login = value.toString();
        if (login.length() < 4 || login.length() > 30 || !login.matches("\\w*")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    MESSAGE_SUMMARY, MESSAGE_DETAIL);
            throw new ValidatorException(message);
        }
    }
    
}