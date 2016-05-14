package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.SubmissionCodeEJB;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.io.IOException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SubmissionCodeController {

    @EJB(beanName = "SubmissionCodeEJB")
    private SubmissionCodeEJB submissionCodeEJB;
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    private Integer id;
    private Submission submission;
    private String submissionText;

    @PostConstruct
    public void initSubmissionPage() {
        WebLogging.logger.log(Level.INFO, "submission page");
        Integer tmpId;
        try {
            String strId = JSFUtil.getRequestParameter("id");
            WebLogging.logger.log(Level.SEVERE, "id {0}", strId);
            if (strId == null) {
                WebLogging.logger.log(Level.SEVERE, "submissionId is null");
                return;
            }
            tmpId = Integer.parseInt(strId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return;
        }
        id = tmpId;
        submission = submissionFacade.find(id);
        try {
            submissionText = submissionCodeEJB.getSubmissionCode(submission);
        } catch (IOException ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getSubmissionText() {
        return submissionText;
    }

}
