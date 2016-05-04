package com.netcracker.database.dal;

import com.netcracker.database.entity.Problem;
import com.netcracker.database.entity.AuthorDecision;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProblemFacadeLocal {

    void create(Problem problem);

    void edit(Problem problem);

    void remove(Problem problem);

    Problem find(Object id);
    
    Problem loadAuthorDecisions(Problem problem);
    
    Problem loadCompetitionProblems(Problem problem);
    
    Problem loadTestGroups(Problem problem);
    
    List<Problem> findAll();
    
}
