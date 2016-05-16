package com.netcracker.businesslogic.support;

import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestingInfo;
import java.util.Date;

public class SubmissionPresentation {

    private TestingInfo testingInfo;
    private Submission submission;
    private Date lastUpdatingDate;

    public SubmissionPresentation(TestingInfo testingInfo, Submission submission) {
        this.testingInfo = testingInfo;
        this.submission = submission;
        this.lastUpdatingDate = new Date();
    }

    public boolean isReady() {
        return testingInfo != null;
    }
    
    public TestingInfo getTestingInfo() {
        return testingInfo;
    }

    public Submission getSubmission() {
        return submission;
    }

    public Date getLastUpdatingDate() {
        return lastUpdatingDate;
    }
}
