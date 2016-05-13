package com.netcracker.web.participants;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.database.dal.ProblemFacadeLocal;
import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.database.entity.Problem;
import com.netcracker.web.logging.WebLogging;
import com.netcracker.web.util.JSFUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@RequestScoped
public class DownloadFileController {

    @EJB(beanName = "ProblemFacade")
    private ProblemFacadeLocal problemFacade;
    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    private StreamedContent file;

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    @PostConstruct
    public void downloadFile() {
        String parametr = JSFUtil.getRequestParameter("problemId");
        InputStream inputStream;
        try {
            Integer problemId = Integer.parseInt(parametr);
            Problem problem = problemFacade.find(problemId);
            Path pathStatementFile = applicationEJB.getFileSupplier().
                getProblemStatement(problem.getFolderName());
            File temp = pathStatementFile.toFile();
            inputStream = new FileInputStream(temp);
            file = new DefaultStreamedContent(inputStream, 
                new MimetypesFileTypeMap().getContentType(temp),
                temp.getName());
        } catch (Throwable ex) {
            file = null;
            WebLogging.logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public String isFileExist() {
        if (file == null)
            return "fileNotFound";
        else
            return null;
    }
    
}
