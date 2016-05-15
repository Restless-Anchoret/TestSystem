package com.netcracker.businesslogic.support;

import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.dal.ParticipationResultFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.ParticipationResult;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.evaluation.EvaluationSystem.ProblemResult;
import com.netcracker.testing.system.TestingInfo;
import com.netcracker.testing.system.Verdict;
import com.netcracker.testing.system.VerdictInfo;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;


public class CheckSubmissionCompetitionHandler extends CheckSubmissionHandler{

    protected ParticipationResultFacadeLocal participationResultFacade;
    
    public CheckSubmissionCompetitionHandler(Submission submission, SubmissionFacadeLocal submissionFacade,
            ParticipationResultFacadeLocal participationResultFacade) {
        super(submission, submissionFacade);
        this.participationResultFacade = participationResultFacade;
    }

    @Override
    public void process(TestingInfo info) {
        super.process(info);
        submissionFacade.loadUserAndCompetitionProblemAndCompetition(submission);
        List<Submission> submissions = submissionFacade.findByUserIdAndCompetitionProblemId(
                submission.getUserId().getId(), submission.getCompetitionProblemId().getId());
        TreeMap<Date, VerdictInfo> treeMap = new TreeMap<>();
        VerdictInfo verdictInfo;
        for (Submission s: submissions) {
            verdictInfo = new VerdictInfo(Verdict.valueOf(s.getVerdict().toUpperCase()), s.getPoints());
            treeMap.put(s.getSubmissionTime(), verdictInfo);
        }
        ProblemResult problemResult = info.getEvaluationSystem().countProblemResult(treeMap, 
                submission.getCompetitionProblemId().getCompetitionId().getCompetitionStart());
        ParticipationResult participationResult = participationResultFacade.
                findByCompetitionIdAndUserIdAndCompetitionProblemId(
                        submission.getCompetitionProblemId().getCompetitionId().getId(),
                        submission.getUserId().getId(), submission.getCompetitionProblemId().getId());
        if (participationResult == null) {
            BusinessLogicLogging.logger.log(Level.SEVERE, "participation_result is null," + 
                    " where comppetition_id = {0}, user_id = {1}, competition_problem_id = {2}", 
                    new Object[]{submission.getCompetitionProblemId().getCompetitionId().getId(),
                        submission.getUserId().getId(), submission.getCompetitionProblemId().getId()});
            return;
        }
        participationResult.setFine(problemResult.getFine());
        participationResult.setPoints(problemResult.getPoints());
        participationResultFacade.edit(participationResult);
    }

    
    
}
