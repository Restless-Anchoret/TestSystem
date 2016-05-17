package com.netcracker.businesslogic.moderating;

import com.netcracker.businesslogic.support.PresentationResultsHandler;
import com.netcracker.businesslogic.support.SubmissionPresentation;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestingInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    //public static final String PRESENTATION_PARAM = "submission_presentation_id";
    public static final int SAVING_PRESENATIONS_DELAY = 5 * 60 * 1000;

    @Resource(name = "usedEvaluationSystemType")
    private String usedEvaluationSystemType;
    
    @EJB(beanName = "SendingSubmissionEJB")
    private SendingSubmissionEJB sendingSubmissionEJB;
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    private Map<Integer, SubmissionPresentation> presentationsMap = new HashMap<>();
    //private Random random = new Random();
    
//    public void runSubmissionForPresentation(Submission submission) {
//        deleteOldPresentations();
//        //String newPresentationId = getNewPresentationId();
//        SessionMediator.setSessionParameter(PRESENTATION_PARAM, newPresentationId);
//        
//    }
    
    public SubmissionPresentation getSubmissionPresentation(Integer submissionId) {
        deleteOldPresentations();
        if (!presentationsMap.containsKey(submissionId)) {
            Submission submission = submissionFacade.find(submissionId);
            presentationsMap.put(submissionId, new SubmissionPresentation(null, submission));
            PresentationResultsHandler handler = new PresentationResultsHandler(
                    this, null, submission);
            sendingSubmissionEJB.sendSubmission(submission.getCompetitionProblemId(),
                    submission.getFolderName(), usedEvaluationSystemType, false,
                    submission.getCompilatorId(), handler);
        }
        return presentationsMap.get(submissionId);
    }
    
//    public SubmissionPresentation getSubmissionPresentation() {
//        String presentationId = SessionMediator.getSessionStringParameter(PRESENTATION_PARAM);
//        return getSubmissionPresentation(presentationId);
//    }
    
    public void putTestingInfo(Submission submission, TestingInfo testingInfo) {
        deleteOldPresentations();
        presentationsMap.put(submission.getId(), new SubmissionPresentation(testingInfo, submission));
    }
    
//    private String getNewPresentationId() {
//        return Integer.toString(random.nextInt());
//    }
    
    private void deleteOldPresentations() {
        Date currentMoment = new Date();
        List<Integer> oldPresentationIds = new ArrayList<>();
        for (Map.Entry<Integer, SubmissionPresentation> entry: presentationsMap.entrySet()) {
            if (currentMoment.getTime() - entry.getValue().getLastUpdatingDate().getTime() > SAVING_PRESENATIONS_DELAY) {
                oldPresentationIds.add(entry.getKey());
            }
        }
        for (Integer presentationId: oldPresentationIds) {
            presentationsMap.remove(presentationId);
        }
    }
    
}