package com.netcracker.web.participants;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.CompetitionProblemFacadeLocal;
import com.netcracker.database.dal.CompilatorFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.Submission;
import com.netcracker.database.entity.User;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.monitor.Monitor;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.CompilatorNameConverter;
import com.netcracker.web.util.JSFUtil;
import com.netcracker.web.util.MonitorColumn;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

@Named
@RequestScoped
public class CompetitionController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
    @EJB(beanName = "CompetitionProblemFacade")
    private CompetitionProblemFacadeLocal competitionProblemFacade;
    @EJB(beanName = "CompilatorFacade")
    private CompilatorFacadeLocal compilatorFacade;
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    @EJB(beanName = "CompetitionEJB")
    private CompetitionEJB competitionEJB;
    private AuthenticationEJB authenticationEJB;
    @EJB(beanName = "SubmissionFacade")
    private SubmissionFacadeLocal submissionFacade;
    private Integer competitionId;
    private List<CompetitionProblem> competitionProblems;
    private UploadedFile file;
    private List<Compilator> compilators;
    private List<SelectItem> compilatorsName;
    private String currentCompilator;
    private Integer currentCompetitionProblemId;
    private List<SelectItem> competitionProblemsItems;
    private final long SIZELIMIT = 262144;
    private List<Submission> submissions;
    private List<Submission> submissionsOutCompetition;
    private List<Submission> allSubmissions;
    private List<TotalResultInfo> results;
    private List<Participation> participations;
    private List<MonitorColumn> columns;
    private String competitionType;
    private Competition competition;
    
    @PostConstruct
    public void initPage() {
        String stringId = JSFUtil.getRequestParameter("competitionId");
        if (stringId == null) {
            return;
        }
        try {
            competitionId = Integer.parseInt(stringId);
            competition = competitionFacade.find(competitionId);
        }
        catch(Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return;
        }
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
        Path path = JSFUtil.getRequestURIPath();
        String pageName = path.getFileName().toString();
        switch (pageName) {
            case "competition_problems.xhtml":
                initProblemsPage();
                break;
            case "competition_submissions.xhtml":
                initSubmissionsPage();
                break;
            case "competition_monitor.xhtml":
                initMonitorPage();
                break;
            case "competition_all_submissions.xhtml":
                initAllSubmissionsPage();
                break;
        }
    }
    
    public void initProblemsPage() {
        try {
            competitionProblems = 
                    competitionProblemFacade.findByCompetitionId(competitionId);
            competitionProblemsItems = new ArrayList<>();
            for (CompetitionProblem problem: competitionProblems) {
                competitionProblemsItems.add(new SelectItem(problem.getId(), problem.getProblemId().getName()));
            }
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            competitionProblems = Collections.EMPTY_LIST;
        }
        try {
            compilators = compilatorFacade.findAll();
            compilatorsName = new ArrayList<>();
            Converter converter = new CompilatorNameConverter();
            for (Compilator compilator: compilators)
                compilatorsName.add(new SelectItem(compilator.getName(),
                        converter.getAsString(null, null, compilator.getName())));
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            compilators = Collections.EMPTY_LIST;
            compilatorsName = Collections.EMPTY_LIST;
        }
    }

    public void initSubmissionsPage() {
        try {
            submissions = submissionFacade.
                    findAllSubmissionsByUserIdAndCompetitionId(authenticationEJB.getCurrentUser().getId(),
                            competitionId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            submissions = Collections.EMPTY_LIST;
        }
        try {
            submissionsOutCompetition = submissionFacade.findAllSubmissionsByUserIdAndCompetitionIdOutCompetition(
                            authenticationEJB.getCurrentUser().getId(), competitionId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            submissionsOutCompetition = Collections.EMPTY_LIST;
        }
    }
    
    public void initMonitorPage() {
        columns = Collections.EMPTY_LIST;
        Monitor monitor = applicationEJB.getMonitorPool().getMonitor(competitionId);
        CompetitionPhase phase = competitionEJB.getCompetitionPhase(competition);
        if (phase == CompetitionPhase.BEFORE) {
            results = Collections.EMPTY_LIST;
            return;
        }
        try {
            competitionProblems = 
                    competitionProblemFacade.findByCompetitionId(competitionId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            competitionProblems = Collections.EMPTY_LIST;
            results = Collections.EMPTY_LIST;
            return;
        }
        columns = new ArrayList<>();
        for (int i = 0; i < competitionProblems.size(); i++)
            columns.add(new MonitorColumn(competitionProblems.get(i).getProblemNumber().toUpperCase(),
                    competitionProblems.get(i).getProblemNumber()));
        participations = competitionFacade.loadParticipations(competition).getParticipationList();
        competitionType = competition.getEvaluationType();
        User user = authenticationEJB.getCurrentUser();
        if (user.getRole().equals("admin") || user.getRole().equals("moderator")) {
            results = monitor.getActualResults();
            return;
        }
        if (phase == CompetitionPhase.WAITING_RESULTS || phase == CompetitionPhase.CODING_FROZEN) {
            results = monitor.getVisibleResults();
        }
        else
            results = monitor.getActualResults();
    }
    
    public void initAllSubmissionsPage() {
        try {
            allSubmissions = submissionFacade.findAllSubmissionsByCompetitionId(competitionId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            allSubmissions = Collections.EMPTY_LIST;
        }
    }
    
    public void upLoadFile() {
        if (!AuthenticationController.getSessionAuthenticationController().isAuthenticatedModeratorOrAdmin()
                && competitionEJB.getCompetitionPhase(competition) == CompetitionPhase.WAITING_RESULTS) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Загрузка файла.",
                    "Отправлять посылки больше нельзя, соревнование закончилось.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
            return;
        }
        if (file == null || file.getFileName().equals("")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.",
                    "Выберите файл.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
            return;
        }
        if (file.getSize() > SIZELIMIT) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.",
                    "Превышен допустимый размер файла (256 Кбайт).");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
            return;
        }
        InputStream is;
        try {
            is = file.getInputstream();
        } catch (IOException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", 
                    "Произошла ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            WebLogging.logger.log(Level.SEVERE, null, ex);
            file = null;
            return;
        }
        if (competitionEJB.addSubmission(competitionId, getFromProblems(currentCompetitionProblemId),
                authenticationEJB.getCurrentUser(), getFromCompilators(currentCompilator), is, 
                file.getFileName(), file.getSize())) {
            file = null;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Файл отправлен.", 
                    "Результат можно посмотреть во вкладке посылки.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", 
                    "Произошла ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
        }
    }
    
    public CompetitionProblem getFromProblems(Integer id) {
        for (CompetitionProblem problem: competitionProblems) {
            if (problem.getId().equals(id))
                return problem;
        }
        return null;
    }
    
    public Compilator getFromCompilators(String name) {
        for (Compilator compilator: compilators) {
            if (compilator.getName().equals(name))
                return compilator;
        }
        return null;
    }
    
    public String getUser(int userId) {
        for (Participation participation: participations)
            if (participation.getUserId().getId().equals(userId))
                return participation.getUserId().getLogin();
        return null;
    }
    
    public String getCountSolveProblem(TotalResultInfo result) {
        int count = 0;
        for (ProblemResultInfo info: result.getProblemResultInfoList())
            if (info.getPoints() > 0)
                count++;
        return String.valueOf(count);
    }
    
    public String getPointsOfProblem(TotalResultInfo result, String numberProblem) {
        CompetitionProblem competitionProblem = null;
        for (CompetitionProblem problem: competitionProblems)
            if (problem.getProblemNumber().equals(numberProblem)) {
                competitionProblem = problem;
                break;
            }
        if (competitionProblem == null) {
            WebLogging.logger.log(Level.SEVERE, "Нет задачи с номером {0}", 
                    new Object[]{numberProblem});
            return null;
        }
        for (ProblemResultInfo info: result.getProblemResultInfoList()) {
            if (competitionProblem.getId().equals(info.getProblemId())) {
                if (competitionType.equals("icpc"))
                    if (info.getPoints() > 0)
                        return "+";
                    else
                        return "-";
                else
                    return String.valueOf(info.getPoints());
            }
        }
        WebLogging.logger.log(Level.SEVERE, "Нет задачи с id {0}", 
                    new Object[]{competitionProblem.getProblemId().getId()});
        return null;
    }
    
    public boolean isViewTable() {
        return !submissionsOutCompetition.isEmpty();
    }
    
    public boolean isDisabledToSendButton() {
        User user = authenticationEJB.getCurrentUser();
        if (user.getRole().equals("admin") || user.getRole().equals("moderator")) {
            return false;
        }
        switch (competitionEJB.getCompetitionPhase(competition)) {
            case BEFORE:
            case WAITING_RESULTS:    
                return true;
            case CODING:
            case CODING_FROZEN:
                return false;
            case FINISHED:
                return !competition.getPracticePermition();
            default:
                return true;    
        }
    }
    
    public List<CompetitionProblem> getCompetitionProblems() {
        return competitionProblems;
    }

    public void setCompetitionProblems(List<CompetitionProblem> competitionProblems) {
        this.competitionProblems = competitionProblems;
    }
    
    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Compilator> getCompilators() {
        return compilators;
    }

    public void setCompilators(List<Compilator> compilators) {
        this.compilators = compilators;
    }

    public String getCurrentCompilator() {
        return currentCompilator;
    }

    public void setCurrentCompilator(String currentCompilator) {
        this.currentCompilator = currentCompilator;
    }

    public Integer getCurrentCompetitionProblemId() {
        return currentCompetitionProblemId;
    }

    public void setCurrentCompetitionProblemId(Integer currentCompetitionProblemId) {
        this.currentCompetitionProblemId = currentCompetitionProblemId;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public List<TotalResultInfo> getResults() {
        return results;
    }

    public void setResults(List<TotalResultInfo> results) {
        this.results = results;
    }

    public List<MonitorColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<MonitorColumn> columns) {
        this.columns = columns;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<SelectItem> getCompilatorsName() {
        return compilatorsName;
    }

    public void setCompilatorsName(List<SelectItem> compilatorsName) {
        this.compilatorsName = compilatorsName;
    }

    public List<SelectItem> getCompetitionProblemsItems() {
        return competitionProblemsItems;
    }

    public void setCompetitionProblemsItems(List<SelectItem> competitionProblemsItems) {
        this.competitionProblemsItems = competitionProblemsItems;
    }

    public List<Submission> getSubmissionsOutCompetition() {
        return submissionsOutCompetition;
    }

    public void setSubmissionsOutCompetition(List<Submission> submissionsOutCompetition) {
        this.submissionsOutCompetition = submissionsOutCompetition;
    }

    public List<Submission> getAllSubmissions() {
        return allSubmissions;
    }

    public void setAllSubmissions(List<Submission> allSubmissions) {
        this.allSubmissions = allSubmissions;
    }
    
}
