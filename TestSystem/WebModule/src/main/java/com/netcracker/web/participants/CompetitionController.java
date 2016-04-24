package com.netcracker.web.participants;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.holding.CompetitionEJB;
import com.netcracker.database.dal.CompetitionFacadeLocal;
import com.netcracker.database.dal.CompilatorFacadeLocal;
import com.netcracker.database.entity.Competition;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Compilator;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.CompetitionProblemComporatorOfProblemNumber;
import com.netcracker.web.util.Pages;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
    private Integer competitionId;
    private List<CompetitionProblem> competitionProblems;
    private UploadedFile file;
    private List<Compilator> compilators;
    private String currentCompilator;
    private String currentCompetitionProblem;
    private final long SIZELIMIT = 262144;
    private String page;
    
    @PostConstruct
    public void initPage() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer id = Integer.parseInt(request.getParameter("competitionId"));
        competitionId = id;
        Pages currentPage = null;
        try {
            currentPage = Pages.valueOf(request.getParameter("page"));
        }
        catch(Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
        }
        if (currentPage == null) {
            initProblemsPage();
            return;
        }
        switch (currentPage) {
            case PROBLEMS:
                initProblemsPage();
                break;
        }
    }
    
    private void initProblemsPage() {
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

    public String loadStatementFile(CompetitionProblem competitionProblem) {
        Path pathStatementFile = applicationEJB.getFileSupplier().
                getProblemStatement(competitionProblem.getCompetitionId().getFolderName());
        if (pathStatementFile == null) {
            return "fileNotFound";
        } else {
            return "downloadFile?pathFile=" + pathStatementFile.toString();
        } 
    }
    
    public String upLoadFile() {
        if (file.getFileName().equals("") || currentCompetitionProblem == null || currentCompilator == null) {
            if (currentCompilator == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", "Выбирите язык.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            if (currentCompetitionProblem == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка.", "Выбирите задачу для которой хотите отослать решение.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            if (file.getFileName().equals("")) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Выбирите файл.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            page = Pages.PROBLEMS.toString();
            return "competition_problems";
        }
        if (file.getSize() > SIZELIMIT) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка загрузки файла.", "Превышен допустимый размер файла (256 Кбайт).");
            FacesContext.getCurrentInstance().addMessage(null, message);
            file = null;
            page = Pages.PROBLEMS.toString();
            return "competition_problems";
        }
        InputStream is;
        try {
            is = file.getInputstream();
        } catch (IOException ex) {
            FacesMessage message = new FacesMessage("Ошибка загрузки файла.", "Произошда ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            WebLogging.logger.log(Level.SEVERE, null, ex);
            page = Pages.PROBLEMS.toString();
            return "competition_problems";
        }
        if (competitionEJB.addSubmission(competitionId, getFromProblems(currentCompetitionProblem),
                getFromCompolators(currentCompilator), is, file.getFileName(), file.getSize())) {
            page = Pages.SUBMSSIONS.toString();
            return "competition_submissions";
        }
        else {
            FacesMessage message = new FacesMessage("Ошибка загрузки файла.", "Произошда ошибка при загрузке файла, попробуйте загрузить еще раз.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            page = Pages.PROBLEMS.toString();
            return "competition_problems";
        }
    }
    
    public CompetitionProblem getFromProblems(String name) {
        for (CompetitionProblem problem: competitionProblems) {
            if (problem.getCompetitionId().getName().equals(name))
                return problem;
        }
        return null;
    }
    
    public Compilator getFromCompolators(String name) {
        for (Compilator compilator: compilators) {
            if (compilator.getName().equals(name))
                return compilator;
        }
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
