package com.netcracker.database.dal;

import com.netcracker.database.entity.Submission;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SubmissionFacadeLocal {

    void create(Submission submission);

    void edit(Submission submission);

    void remove(Submission submission);

    Submission find(Object id);
    
    List<Submission> findByUserIdAndCompetitionProblemId(Object userId, Object competitionProblemId);
    
    Submission loadCompetitionProblem(Submission submission);
    
    Submission loadUser(Submission submission);
    
    List<Submission> findAllSubmissionsByCompetitionId(Object competitionId);
    
}
