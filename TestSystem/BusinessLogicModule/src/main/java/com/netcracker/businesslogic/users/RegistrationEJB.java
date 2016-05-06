package com.netcracker.businesslogic.users;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.HashCreator;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.entity.User;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class RegistrationEJB {
    
    private static final int RANDOM_ADDING_TRIES = 20;
    private static final int RANDOM_NUMBER_SYMBOLS = 8;
    
    @Resource(name = "adminDefaultLogin")
    private String adminDefaultLogin;
    @Resource(name = "moderatorDefaultLoginPrefix")
    private String moderatorDefaultLoginPrefix;
    @Resource(name = "participantDefaultLoginPrefix")
    private String participantDefaultLoginPrefix;
    @Resource(name = "defaultPassword")
    private String defaultPassword;
    
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    private Random random = new Random();
    
    public void checkAdminRegistration(/*String adminDefaultLogin, String adminDefaultPassword*/) {
        User user = null;
        try {
            user = userFacade.getAdmin();
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, null, exception);
            return;
        }
        if (user == null) {
            Result adminRegistrationResult = tryRegistrateActual(adminDefaultLogin,
                    defaultPassword, Role.ADMIN);
            String message = "Trying to registrate admin. Result: " +
                    adminRegistrationResult.getInfo().toString();
            BusinessLogicLogging.logger.info(message);
        }
    }
    
    public Result addNewModerator() {
        String newLogin = findUnusedLoginWithPrefix(moderatorDefaultLoginPrefix);
        if (newLogin == null) {
            return new Result(null, Info.FAIL);
        }
        return tryRegistrateActual(newLogin, defaultPassword, Role.MODERATOR);
    }
    
    public Result addNewParticipant() {
        String newLogin = findUnusedLoginWithPrefix(participantDefaultLoginPrefix);
        if (newLogin == null) {
            return new Result(null, Info.FAIL);
        }
        return tryRegistrateNotActual(newLogin, defaultPassword, Role.PARTICIPANT);
    }
    
    private String findUnusedLoginWithPrefix(String prefix) {
        for (int i = 0; i < RANDOM_ADDING_TRIES; i++) {
            String newLogin = prefix + getRandomNumberLine();
            User user = userFacade.findByLogin(newLogin);
            if (user == null) {
                return newLogin;
            }
        }
        return null;
    }
    
    private String getRandomNumberLine() {
        int number = random.nextInt();
        StringBuilder builder = new StringBuilder(RANDOM_NUMBER_SYMBOLS);
        for (int i = 0; i < RANDOM_NUMBER_SYMBOLS; i++) {
            builder.append(Math.abs(number % 10));
            number /= 10;
        }
        return builder.toString();
    }
    
    public Result tryRegistrateActual(String login, String password, Role role) {
        return tryRegistrate(login, password, role, true);
    }
    
    public Result tryRegistrateNotActual(String login, String password, Role role) {
        return tryRegistrate(login, password, role, false);
    }
    
    private Result tryRegistrate(String login, String password, Role role, boolean actual) {
        try {
            if (userFacade.findByLogin(login) != null) {
                return new Result(null, Info.LOGIN_ALREADY_EXISTS);
            }
            Date registrationDate = new Date();
            HashCreator hashCreator = HashCreator.getDefault();
            HashCreator.Result hashCreatingResult = hashCreator.getHash(password);
            User user = new User();
            user.setLogin(login);
            user.setPasswordHash(hashCreatingResult.getPasswordHashString());
            user.setHashSalt(hashCreatingResult.getSaltString());
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