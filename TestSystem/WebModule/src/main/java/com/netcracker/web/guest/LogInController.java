package com.netcracker.web.guest;

import com.netcracker.dal.UserFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class LogInController {

    private String login;
    private String password;
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    public LogInController() { }

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
        return null;
    }

}
