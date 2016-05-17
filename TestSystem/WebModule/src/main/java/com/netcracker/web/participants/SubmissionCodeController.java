package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.FileContentEJB;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SubmissionCodeController {

    @EJB(beanName = "FileContentEJB")
    private FileContentEJB fileContentEJB;
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    private Integer id;
    private Submission submission;
    private String submissionText;

    @PostConstruct
    public void initSubmissionPage() {
        Integer tmpId;
        try {
            String strId = JSFUtil.getRequestParameter("id");
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
        submissionText = fileContentEJB.getSubmissionCode(submission);
        if (submissionText == null) {
            submissionText = "Невозможно отобразить содержимое посылки";
        }
    }

    public Integer getId() {
        return id;
    }

    public String getSubmissionText() {
        return submissionText;
    }

    public Submission getSubmission() {
        return submission;
    }

}
