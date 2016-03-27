package com.netcracker.database.dal;

import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserFacadeLocal {

    void create(User user); //test

    void edit(User user); //test

    void remove(User user); // test

    User find(Object id); //test
    
    User loadParticipations(User user);
    
    User loadParticipationResults(User user);
    
    User loadSubmissions(User user);
    
    User findByLogin(String login); //test
    
    List<User> getModerators(); //test

    List<User> getParticipants(); //test
    
    User getAdmin(); //test
    
}
