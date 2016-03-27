package com.netcracker.web.guest;

import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.web.session.AuthenticationController;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
public class LogInController {

    private String login;
    private String password;
    private AuthenticationEJB authenticationEJB;
    
    public LogInController() { }
    
    @PostConstruct
    public void initController() {
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getAdminLogin() {
//        try {
//            User user = userFacade.findByLogin("admin");
//            if (user == null) {
//                return "Null!";
//            } else {
//                return "Not null! {" + user.getLogin() + ", " + user.getId() + "}";
//            }
//        } catch (Throwable throwable) {
//            return "Exception! {" + throwable.getMessage() + ", " + throwable.getClass().getName() +
//                    ", " + throwable.toString() + ", " + throwable.getCause() + "}";
//        }
//    }
    
//    public String getSubmissionVerdicts() {
//        List<Submission> list = submissionFacade.findByUserIdAndCompetitionProblemId(1, 1);
//        String result = "{ ";
//        for (Submission submission: list) {
//            result += submission.getVerdict() + " ";
//        }
//        result += "}";
//        return result;
//    }
    
    public String doAuthentication() {
//        AuthenticationEJB.Result authenticationResult = authenticationEJB
//                .tryAuthenticateUser(login, password);
        AuthenticationEJB.Result authenticationResult = new AuthenticationEJB.Result(null, AuthenticationEJB.Info.INCORRECT_PASSWORD);
        String message = null;
        switch (authenticationResult.getInfo()) {
            case SUCCESS:
                return "index.xhtml";
            case INCORRECT_PASSWORD:
                message = "Неверный пароль";
                break;
            case LOGIN_DOES_NOT_EXIST:
                message = "Данный логин не зарегистрирован";
                break;
            case FAIL:
                message = "Ошибка при аутентификации";
                break;
        }
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return null;
    }

}
