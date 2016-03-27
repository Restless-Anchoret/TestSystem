package com.netcracker.businesslogic.users;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.HashCreator;
import com.netcracker.dal.UserFacadeLocal;
import com.netcracker.entity.User;
import java.util.Date;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class RegistrationEJB {
    
    @Resource(name = "adminDefaultLogin")
    private String adminDefaultLogin;
    @Resource(name = "adminDefaultPassword")
    private String adminDefaultPassword;
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
//    public void checkAdminRegistration() {
//        User user = userFacade.getAdmin();
//        if (user == null) {
//            Result adminRegistrationResult = tryRegistrateActual(adminDefaultLogin,
//                    adminDefaultPassword, Role.ADMIN);
//            String message = "Trying to registrate admin. Result: " +
//                    adminRegistrationResult.getInfo().toString();
//            BusinessLogicLogging.logger.info(message);
//        }
//    }
    
    public Result tryRegistrateActual(String login, String password, Role role) {
        return tryRegistrate(login, password, role, true);
    }
    
    public Result tryRegistrateNotActual(String login, String password, Role role) {
        return tryRegistrate(login, password, role, false);
    }
    
    private Result tryRegistrate(String login, String password, Role role, boolean actual) {
        if (login.length() < 4 || login.length() > 30 || !login.matches("\\w*")) {
            return new Result(null, Info.UNSUITABLE_LOGIN);
        }
        if (password.length() < 4 || password.length() > 40 ||
                !password.matches(".*[a-zA-Z].*") ||
                !password.matches(".*\\d.*")) {
            return new Result(null, Info.UNSUITABLE_PASSWORD);
        }
        try {
//            if (userFacade.findByLogin(login) != null) {
//                return new Result(null, Info.LOGIN_ALREADY_EXISTS);
//            }
            Date registrationDate = new Date();
            //Date registrationDate = new GregorianCalendar(2016, 0, 1).getTime();
            String hash = HashCreator.getHash(password, registrationDate);
            User user = new User();
            user.setLogin(login);
            user.setPasswordHash(hash);
            user.setRole(role.name().toLowerCase());
            user.setRegistrationDate(registrationDate);
            user.setActual(actual);
            userFacade.create(user);
            return new Result(user, Info.SUCCESS);
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, null, exception);
            return new Result(null, Info.FAIL);
        }
    }
            
    public static enum Info {
        SUCCESS,
        NOT_AVAILABLE_LOGIN,
        LOGIN_ALREADY_EXISTS,
        UNSUITABLE_LOGIN,
        UNSUITABLE_PASSWORD,
        FAIL
    }
    
    public static class Result {
        private User user;
        private Info info;
        
        public Result(User user, Info info) {
            this.user = user;
            this.info = info;
        }

        public User getUser() {
            return user;
        }

        public Info getInfo() {
            return info;
        }
    }
    
}