/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.Competition;
import com.netcracker.entity.CompetitionProblem;
import com.netcracker.entity.Participation;
import com.netcracker.entity.ParticipationResult;
import com.netcracker.entity.PersonalData;
import com.netcracker.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface CompetitionFacadeLocal {

    void create(Competition competition);

    void edit(Competition competition);

    void remove(Competition competition);

    Competition find(Object id);

    List<Competition> findAllCompetiotions(int[] range);
    
    List<Competition> findVisibleCompetiotions(int[] range);
    
    Competition loadCompetitionProblems(Competition competition);
    
    Competition loadParticipations(Competition competition);
    
    List<ParticipationResult> createNullsResults(Competition competition);
    
    List<Competition> findAllTranings(int[] range);
    
    List<Competition> findVisibleTranings(int[] range);

    void finishedCompetition(Competition competition, List<ParticipationResult> participationResults);
    
    void registrationNewParticipation(Competition competition, User user, PersonalData personalData);
    
    void registrationNewParticipation(Competition competition, User user);
    
    void addPaticipations(Competition competition, List<User> users);
    
}
