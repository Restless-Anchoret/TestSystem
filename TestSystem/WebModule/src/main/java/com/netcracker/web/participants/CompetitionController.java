package com.netcracker.web.participants;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.businesslogic.users.AuthenticationEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.CompilatorFacadeLocal;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.database.entity.Participation;
import com.netcracker.database.entity.Submission;
import com.netcracker.monitoring.info.CompetitionPhase;
import com.netcracker.monitoring.info.ProblemResultInfo;
import com.netcracker.monitoring.info.TotalResultInfo;
import com.netcracker.monitoring.monitor.Monitor;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.session.AuthenticationController;
import com.netcracker.web.util.CompetitionProblemComporatorOfProblemNumber;
import com.netcracker.web.util.MonitorColumn;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;

@Named
@RequestScoped
public class CompetitionController {

    @EJB(beanName = "CompetitionFacade")
    private CompetitionFacadeLocal competitionFacade;
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
    private String currentCompilator;
    private String currentCompetitionProblem;
    private final long SIZELIMIT = 262144;
    private List<Submission> submissions;
    private List<TotalResultInfo> results;
    private List<Participation> participations;
    private List<MonitorColumn> columns;
    private String competitionType;
    
    @PostConstruct
    public void initPage() {
        WebLogging.logger.log(Level.INFO, "init page");
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer id;
        try {
            String strId = request.getParameter("competitionId");
            if (strId == null) {
                WebLogging.logger.log(Level.SEVERE, "competitionId is null");
                return;
            }
            id = Integer.parseInt(strId);
        }
        catch(Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            return;
        }
        competitionId = id;
        authenticationEJB = AuthenticationController.getSessionAuthenticationEJB();
        Path path = Paths.get(request.getRequestURI());
        String pageName = path.getFileName().toString();
        WebLogging.logger.log(Level.INFO, path.getFileName().getName(0).toString());
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
        }
    }
    
    public void initProblemsPage() {
        WebLogging.logger.log(Level.INFO, "init problems page");
        try {
            Competition currentCompetition = competitionFacade.find(competitionId);
            competitionProblems = competitionFacade.loadCompetitionProblems(currentCompetition).getCompetitionProblemList();
            competitionProblems.sort(new CompetitionProblemComporatorOfProblemNumber());
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            competitionProblems = Collections.EMPTY_LIST;
        }
        try {
            compilators = compilatorFacade.findAll();
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            compilators = Collections.EMPTY_LIST;
        }
    }

    public void initSubmissionsPage() {
        WebLogging.logger.log(Level.INFO, "init submissions page");
        try {
            submissions = submissionFacade.
                    findAllSubmissionsByUserIdAndCompetitionId(authenticationEJB.getCurrentUser().getId(),
                            competitionId);
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
            submissions = Collections.EMPTY_LIST;
        }
    }
    
    public void initMonitorPage() {
        WebLogging.logger.log(Level.INFO, "init monitor page");
        columns = Collections.EMPTY_LIST;
        Monitor monitor = applicationEJB.getMonitorPool().getMonitor(competitionId);
        Competition competition = competitionFacade.find(competitionId);
        CompetitionPhase phase = competitionEJB.getCompetitionPhase(competition);
        if (phase == CompetitionPhase.BEFORE) {
            results = Collections.EMPTY_LIST;
            return;
        }
        try {
            Competition currentCompetition = competitionFacade.find(competitionId);
            competitionProblems = competitionFacade.loadCompetitionProblems(currentCompetition).getCompetitionProblemList();
            competitionProblems.sort(new CompetitionProblemComporatorOfProblemNumber());
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
        if (phase == CompetitionPhase.WAITING_RESULTS || phase == CompetitionPhase.CODING_FROZEN) {
            results = monitor.getVisibleResults();
        }
        else
            results = monitor.getActualResults();
    }
    
    public String loadStatementFile(CompetitionProblem competitionProblem) {
        Path pathStatementFile = applicationEJB.getFileSupplier().
                getProblemStatement(competitionProblem.getProblemId().getFolderName());
        if (pathStatementFile == null) {
            return "fileNotFound";
        } else {
            return "downloadFile?pathFile=" + pathStatementFile.toString();
        } 
    }
    
    public void upLoadFile() {
        WebLogging.logger.log(Level.INFO, "start upload ");
        WebLogging.logger.log(Level.INFO, "compilator " + currentCompilator);
        if (file == null || file.getFileName().equals("") || currentCompetitionProblem == null || currentCompilator == null) {
            if (currentCompilator == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", "Выберите язык.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            if (currentCompetitionProblem == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", "Выберите задачу для которой хотите отослать решение.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            if (file == null || file.getFileName().equals("")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Выберите файл.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            file = null;
            return;
        }
        if (file.getSize() > SIZELIMIT) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Превышен допустимый размер файла (256 Кбайт).");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
            return;
        }
        InputStream is;
        try {
            is = file.getInputstream();
        } catch (IOException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Произошда ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            WebLogging.logger.log(Level.SEVERE, null, ex);
            file = null;
            return;
        }
        if (competitionEJB.addSubmission(competitionId, getFromProblems(currentCompetitionProblem),
                authenticationEJB.getCurrentUser(), getFromCompilators(currentCompilator), is, 
                file.getFileName(), file.getSize())) {
            file = null;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Файл отправлен.", "Результат можно посмотреть во вкладке посылки.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Произошда ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
        }
    }
    
    public CompetitionProblem getFromProblems(String name) {
        for (CompetitionProblem problem: competitionProblems) {
            if (problem.getProblemId().getName().equals(name))
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
            if (competitionProblem.getProblemId().getId().equals(info.getProblemId())) {
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

    public String getCurrentCompetitionProblem() {
        return currentCompetitionProblem;
    }

    public void setCurrentCompetitionProblem(String currentCompetitionProblem) {
        this.currentCompetitionProblem = currentCompetitionProblem;
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
    
}
