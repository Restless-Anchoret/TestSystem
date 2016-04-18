package com.netcracker.businesslogic.users;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.support.HashCreator;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.entity.User;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PasswordChangeEJB {

    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    public Info tryChangePassword(User user, String oldPassword, String newPassword) {
        try {
            HashCreator hashCreator = HashCreator.getDefault();
            String oldPasswordHash = hashCreator.getHash(oldPassword, user.getHashSalt()).getPasswordHashString();
            if (!oldPasswordHash.equals(user.getPasswordHash())) {
                return Info.WRONG_OLD_PASSWORD;
            }
            String newPasswordHash = hashCreator.getHash(newPassword, user.getHashSalt()).getPasswordHashString();
            user.setPasswordHash(newPasswordHash);
            userFacade.edit(user);
            return Info.SUCCESS;
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, null, exception);
            return Info.FAIL;
        }
    }
    
    public static enum Info {
        SUCCESS,
        WRONG_OLD_PASSWORD,
        FAIL
    }
    
}
