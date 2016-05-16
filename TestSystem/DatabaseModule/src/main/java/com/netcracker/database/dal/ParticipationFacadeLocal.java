package com.netcracker.database.dal;

import com.netcracker.database.entity.Participation;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ParticipationFacadeLocal {

    void create(Participation participation);

    void edit(Participation participation);
    
    void edit(List<Participation> participations);

    void remove(Participation participation);

    Participation find(Object id);
    
    Participation findByCompetitionIdAndUserId(Object competitionId, Object userId);
    
    List<Participation> findByCompetitionId(Object competitionId);
    
}
