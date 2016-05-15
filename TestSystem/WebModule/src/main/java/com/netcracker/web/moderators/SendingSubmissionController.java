package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.SubmissionPresentationEJB;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SendingSubmissionController {

    @EJB(beanName = "SubmissionPresentationEJB")
    private SubmissionPresentationEJB submissionPresentationEJB;
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    
    public String runSubmissionForPresentation(Submission submission) {
        submissionPresentationEJB.runSubmissionForPresentation(submission);
        return "submission_results.xhtml";
    }
    
    public String runSubmissionForPresentation() {
        Submission submission = submissionFacade.find(1);
        return runSubmissionForPresentation(submission);
    }
    
}