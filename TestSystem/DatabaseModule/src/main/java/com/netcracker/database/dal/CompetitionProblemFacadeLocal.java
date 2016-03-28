package com.netcracker.database.dal;

import com.netcracker.database.entity.CompetitionProblem;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CompetitionProblemFacadeLocal {

    void create(CompetitionProblem competitionProblem);

    void edit(CompetitionProblem competitionProblem);

    void remove(CompetitionProblem competitionProblem);

    CompetitionProblem find(Object id);
    
    CompetitionProblem loadParticipationResults(CompetitionProblem competitionProblem);
    
    CompetitionProblem loadSubmissionLists(CompetitionProblem competitionProblem);
    
    CompetitionProblem loadCompetition(CompetitionProblem competitionProblem);
    
}
