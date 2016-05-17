package com.netcracker.web.moderators;

import com.netcracker.businesslogic.moderating.SubmissionPresentationEJB;
import com.netcracker.businesslogic.support.SubmissionPresentation;
import com.netcracker.database.entity.Submission;
import com.netcracker.testing.system.TestGroupType;
import com.netcracker.testing.system.TestTable;
import com.netcracker.testing.system.VerdictInfo;
import com.netcracker.web.util.TestDescription;
import com.netcracker.web.util.VerdictInfoConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SubmissionResultsConroller {

    @EJB(beanName = "SubmissionPresentationEJB")
    private SubmissionPresentationEJB submissionPresentationEJB;
    private SubmissionPresentation presentation;
    private List<TestDescription> testDescriptions = new ArrayList<>();
    
    @PostConstruct
    public void initController() {
        presentation = submissionPresentationEJB.getSubmissionPresentation();
        if (presentation.isReady()) {
            int testsCounter = 0;
            TestTable testTable = presentation.getTestingInfo().getTestTable();
            for (TestGroupType testGroupType: TestGroupType.values()) {
                for (int i = 1; i <= testTable.getTestsQuantity(testGroupType); i++) {
                    testsCounter++;
                    VerdictInfo verdictInfo = testTable.getVerdictInfoForTest(testGroupType, i);
                    testDescriptions.add(new TestDescription(testsCounter,
                            testGroupType.toString().toLowerCase(),
                            verdictInfo.getVerdict().toString().toLowerCase(),
                            verdictInfo.getDecisionTime(), i));
                }
            }
        }
    }

    public Submission getPresentationSubmission() {
        return presentation.getSubmission();
    }
    
    public Date getPresentationUpdating() {
        return presentation.getLastUpdatingDate();
    }
    
    public boolean isPresentationReady() {
        return presentation.isReady();
    }
    
    public String getVerdictInfo() {
        if (presentation.isReady()) {
            return new VerdictInfoConverter().getAsString(null, null, presentation.getTestingInfo().getVerdictInfo());
        } else {
            return "Обновите для вывода результатов...";
        }
    }

    public List<TestDescription> getTestDescriptions() {
        return testDescriptions;
    }
    
}