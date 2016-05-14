package com.netcracker.web.participants;

import com.netcracker.businesslogic.holding.SubmissionCodeEJB;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.web.logging.WebLogging;
import java.io.IOException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class SubmissionCodeController {

    @EJB(beanName = "SubmissionCodeEJB")
    private SubmissionCodeEJB submissionCodeEJB;
    private Integer submissionId;
    private String submissionText;

    @PostConstruct
    public void initSubmissionPage() {
        WebLogging.logger.log(Level.INFO, "submission page");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer id;
        try {
            String strId = request.getParameter("submissionId");
            WebLogging.logger.log(Level.SEVERE, "submissionId {0}", strId);
            if (strId == null) {
                WebLogging.logger.log(Level.SEVERE, "submissionId is null");
                return;
            }
            id = Integer.parseInt(strId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return;
        }
        submissionId = id;
        try {
            submissionText = submissionCodeEJB.getSubmissionCode(submissionId);
        } catch (IOException ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
        }
    }

    public String getSubmissionText() {
        return submissionText;
    }

}
