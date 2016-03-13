/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Participation;
import com.netcracker.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
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
}
