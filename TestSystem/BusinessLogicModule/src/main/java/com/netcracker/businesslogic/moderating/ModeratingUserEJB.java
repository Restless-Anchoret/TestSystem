package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.businesslogic.users.RegistrationEJB;
import com.netcracker.database.dal.UserFacadeLocal;
import com.netcracker.database.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ModeratingUserEJB {
    
    @EJB(beanName = "UserFacade")
    private UserFacadeLocal userFacade;
    
    public List<User> getAllParticipants() {
        try {
            return userFacade.getParticipants();
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all participants", exception);
            return Collections.EMPTY_LIST;
        }
    }
    
    public List<User> getAllModerators() {
        try {
            return userFacade.getModerators();
        } catch (Exception exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while getting all moderators", exception);
            return Collections.EMPTY_LIST;
        }
    }
    
}