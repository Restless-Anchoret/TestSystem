package com.netcracker.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    private static final String MESSAGE_SUMMARY = "Некорректный пароль";
    private static final String MESSAGE_DETAIL = "Пароль должен иметь от 4 до 40 " +
            "символов и содержать хотя бы одну латинскую букву и хотя бы одну цифру";
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String password = value.toString();
        if (password.length() < 4 || password.length() > 40 ||
                !password.matches(".*[a-zA-Z].*") ||
                !password.matches(".*\\d.*")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    MESSAGE_SUMMARY, MESSAGE_DETAIL);
            throw new ValidatorException(message);
        }
    }

}