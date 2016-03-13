/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.ParticipationResult;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface ParticipationResultFacadeLocal {

    void create(ParticipationResult participationResult);

    void edit(ParticipationResult participationResult);

    void remove(ParticipationResult participationResult);

    ParticipationResult find(Object id);
    
    List<ParticipationResult> findByCompetitionId(Object competitionId);
    
    List<ParticipationResult> findByCompetitionIdAndUserId(Object competitionId, Object userId);
    
    ParticipationResult loadCompetitionProblem(ParticipationResult participationResult);
    
    ParticipationResult loadUser(ParticipationResult participationResult);
    
}
