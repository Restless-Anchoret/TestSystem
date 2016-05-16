package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.support.PresentationResultsHandler;
import com.netcracker.businesslogic.support.SessionMediator;
import com.netcracker.businesslogic.support.SubmissionPresentation;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestingInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@LocalBean
@Lock(LockType.WRITE)
public class SubmissionPresentationEJB {
    
    public static final String PRESENTATION_PARAM = "submission_presentation_id";
    public static final int SAVING_PRESENATIONS_DELAY = 30 * 60 * 1000;

    @Resource(name = "usedEvaluationSystemType")
    private String usedEvaluationSystemType;
    
    @EJB(beanName = "SendingSubmissionEJB")
    private SendingSubmissionEJB sendingSubmissionEJB;
    private Map<String, SubmissionPresentation> presentationsMap = new HashMap<>();
    private Random random = new Random();
    
    public void runSubmissionForPresentation(Submission submission) {
        deleteOldPresentations();
        String newPresentationId = getNewPresentationId();
        SessionMediator.setSessionParameter(PRESENTATION_PARAM, newPresentationId);
        presentationsMap.put(newPresentationId, new SubmissionPresentation(null, submission));
        PresentationResultsHandler handler = new PresentationResultsHandler(
                this, newPresentationId, submission);
        sendingSubmissionEJB.sendSubmission(submission.getCompetitionProblemId(),
                submission.getFolderName(), usedEvaluationSystemType, false,
                submission.getCompilatorId(), handler);
    }
    
    public SubmissionPresentation getSubmissionPresentation(String presentationId) {
        return presentationsMap.get(presentationId);
    }
    
    public SubmissionPresentation getSubmissionPresentation() {
        String presentationId = SessionMediator.getSessionStringParameter(PRESENTATION_PARAM);
        return getSubmissionPresentation(presentationId);
    }
    
    public void putTestingInfo(String id, TestingInfo testingInfo, Submission submission) {
        deleteOldPresentations();
        presentationsMap.put(id, new SubmissionPresentation(testingInfo, submission));
    }
    
    private String getNewPresentationId() {
        return Integer.toString(random.nextInt());
    }
    
    private void deleteOldPresentations() {
        Date currentMoment = new Date();
        List<String> oldPresentationIds = new ArrayList<>();
        for (Map.Entry<String, SubmissionPresentation> entry: presentationsMap.entrySet()) {
            if (currentMoment.getTime() - entry.getValue().getLastUpdatingDate().getTime() > SAVING_PRESENATIONS_DELAY) {
                oldPresentationIds.add(entry.getKey());
            }
        }
        for (String presentationId: oldPresentationIds) {
            presentationsMap.remove(presentationId);
        }
    }
    
}