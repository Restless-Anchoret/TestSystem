package com.netcracker.businesslogic.support;

import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestResultHandler;
import com.netcracker.testing.system.TestingInfo;

public class CheckSubmissionHandler implements TestResultHandler{

    protected SubmissionFacadeLocal submissionFacade;
    protected Submission submission;
    
    
    public CheckSubmissionHandler(Submission submission, SubmissionFacadeLocal submissionFacade) {
        this.submission = submission;
        this.submissionFacade = submissionFacade;
    }
    
    @Override
    public void process(TestingInfo info) {
        submission.setVerdict(info.getVerdictInfo().getVerdict().name());
        submission.setDecisionMemory(info.getVerdictInfo().getDecisionTime());
        submission.setDecisionTime(info.getVerdictInfo().getDecisionTime());
        submission.setPoints(info.getVerdictInfo().getPoints());
        if (info.getVerdictInfo().getWrongTestNumber() == null)
            submission.setWrongTestNumber(null);
        else
            submission.setWrongTestNumber(info.getVerdictInfo().getWrongTestNumber().shortValue());
        submissionFacade.edit(submission);
    }

}
