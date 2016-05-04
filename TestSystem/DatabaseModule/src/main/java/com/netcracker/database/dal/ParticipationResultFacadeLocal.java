package com.netcracker.database.dal;

import com.netcracker.database.entity.ParticipationResult;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ParticipationResultFacadeLocal {

    void create(ParticipationResult participationResult);

    void edit(ParticipationResult participationResult);

    void remove(ParticipationResult participationResult);

    ParticipationResult find(Object id);
    
    List<ParticipationResult> findByCompetitionId(Object competitionId);
    
    ParticipationResult findByCompetitionIdAndUserIdAndCompetitionProblemId(
            Object competitionId, Object userId, Object competitionProblemId);
    
    ParticipationResult loadCompetitionProblem(ParticipationResult participationResult);
    
    ParticipationResult loadUser(ParticipationResult participationResult);
    
}
