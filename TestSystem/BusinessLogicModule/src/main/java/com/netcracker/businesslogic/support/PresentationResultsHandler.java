package com.netcracker.businesslogic.support;

import com.netcracker.businesslogic.moderating.SubmissionPresentationEJB;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestResultHandler;
import com.netcracker.testing.system.TestingInfo;

public class PresentationResultsHandler implements TestResultHandler {

    private SubmissionPresentationEJB submissionPresentationEJB;
    private String presentationId;
    private Submission submission;

    public PresentationResultsHandler(SubmissionPresentationEJB submissionResultsEJB,
            String presentationId, Submission submission) {
        this.submissionPresentationEJB = submissionResultsEJB;
        this.presentationId = presentationId;
        this.submission = submission;
    }
    
    @Override
    public void process(TestingInfo info) {
        submissionPresentationEJB.putTestingInfo(presentationId, info, submission);
    }

}