/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.dal;

import com.netcracker.entity.CompetitionProblem;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ataman
 */
@Local
public interface CompetitionProblemFacadeLocal {

    void create(CompetitionProblem competitionProblem);

    void edit(CompetitionProblem competitionProblem);

    void remove(CompetitionProblem competitionProblem);

    CompetitionProblem find(Object id);
    
    CompetitionProblem loadParticipationResults(CompetitionProblem competitionProblem);
    
    CompetitionProblem loadSubmissionLists(CompetitionProblem competitionProblem);
    
}
