package com.netcracker.database.dal;

import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);
    
    User loadParticipations(User user);
    
    User loadParticipationResults(User user);
    
    User loadSubmissions(User user);
    
    User findByLogin(String login);
    
    List<User> getModerators();

    List<User> getParticipants();
    
    User getAdmin();
    
}
