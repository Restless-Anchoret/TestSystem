package com.netcracker.web.participants;

import com.netcracker.database.entity.CompetitionProblem;
import com.netcracker.web.logging.WebLogging;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import javax.activation.MimetypesFileTypeMap;
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

    private String pathFile;
    private StreamedContent file;
    
    public DownloadFileController() {}

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        //WebLogging.logger.log(Level.INFO, "init parametr");
        //WebLogging.logger.log(Level.INFO, pathFile);
        this.pathFile = pathFile;
        downloadFile();
    }

    public StreamedContent getFile() {
        //WebLogging.logger.log(Level.INFO, "download");
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public void downloadFile() {
        //WebLogging.logger.log(Level.INFO, "load");
        InputStream inputStream;
        try {
            File temp = new File(pathFile);
            inputStream = new FileInputStream(temp);
            file = new DefaultStreamedContent(inputStream, 
                new MimetypesFileTypeMap().getContentType(temp),
                temp.getName());
        } catch (Throwable ex) {
            WebLogging.logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public String isFileExist() {
        //WebLogging.logger.log(Level.INFO, "check");
        if (file == null)
            return "fileNotFound";
        else
            return null;
    }
    
}
