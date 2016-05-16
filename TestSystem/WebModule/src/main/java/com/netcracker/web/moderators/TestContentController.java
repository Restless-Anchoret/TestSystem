package com.netcracker.web.moderators;

import com.netcracker.businesslogic.holding.FileContentEJB;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.Problem;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class TestContentController {

    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    @EJB(beanName = "FileContentEJB")
    private FileContentEJB fileContentEJB;
    
    private Problem problem;
    private String testGroup;
    private int testNumber;
    private String testInputContent;
    private String testAnswerContent;
    
    @PostConstruct
    public void initController() {
        try {
            String problemFolder = JSFUtil.getRequestParameter("problemId");
            problem = problemFacade.find(Integer.parseInt(problemFolder));
            testGroup = JSFUtil.getRequestParameter("test_group");
            testNumber = Integer.parseInt(JSFUtil.getRequestParameter("test_number"));
            testInputContent = fileContentEJB.getTestInputContent(problemFolder, testGroup, testNumber);
            testAnswerContent = fileContentEJB.getTestAnswerContent(problemFolder, testGroup, testNumber);
        } catch (Throwable throwable) {
            WebLogging.logger.log(Level.FINE, "Exception while initializing TestContentController", throwable);
        }
    }

    public Problem getProblem() {
        return problem;
    }

    public String getTestGroup() {
        return testGroup;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public String getTestInputContent() {
        return testInputContent;
    }

    public String getTestAnswerContent() {
        return testAnswerContent;
    }
    
}