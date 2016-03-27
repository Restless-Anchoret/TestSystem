package com.netcracker.database.dal;

import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.ParticipationResult;
import com.netcracker.database.entity.PersonalData;
import com.netcracker.database.entity.User;
import java.util.List;
import javax.ejb.Local;

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
