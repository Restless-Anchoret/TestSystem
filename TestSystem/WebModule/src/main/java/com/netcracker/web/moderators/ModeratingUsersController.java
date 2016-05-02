package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.ModeratingUserEJB;
import com.netcracker.database.entity.User;
import com.netcracker.web.util.JSFUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named
@RequestScoped
public class ModeratingUsersController {

    @EJB(beanName = "ModeratingUserEJB")
    private ModeratingUserEJB moderatingUserEJB;

    public List<User> getParticipants() {
        return moderatingUserEJB.getAllParticipants();
    }
    
    public List<User> getModerators() {
        return moderatingUserEJB.getAllModerators();
    }
    
    public void addNewParticipant() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
    public void addNewModerator() {
        JSFUtil.addInfoMessage("Unsupported operation", "");
    }
    
}
