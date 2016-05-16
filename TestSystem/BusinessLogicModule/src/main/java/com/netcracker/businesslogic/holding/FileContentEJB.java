package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.businesslogic.logging.BusinessLogicLogging;
import com.netcracker.database.entity.Submission;
import com.netcracker.filesystem.supplier.FileSupplier;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class FileContentEJB {

    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;

    public String getTestInputContent(String problemFolder, String testGroupType, int testNumber) {
        FileSupplier fileSupplier = applicationEJB.getFileSupplier();
        return getFileContentAsString(fileSupplier.getTestInputFile(problemFolder, testGroupType, testNumber));
    }
    
    public String getTestAnswerContent(String problemFolder, String testGroupType, int testNumber) {
        FileSupplier fileSupplier = applicationEJB.getFileSupplier();
        return getFileContentAsString(fileSupplier.getTestInputFile(problemFolder, testGroupType, testNumber));
    }
    
    public String getSubmissionCode(Submission submission) {
        FileSupplier fileSupplier = applicationEJB.getFileSupplier();
        return getFileContentAsString(fileSupplier.getSubmissionSourceFile(submission.getFolderName()));
    }
    
    public String getFileContentAsString(Path path) {
        if (path == null) {
            return null;
        }
        try (ByteArrayOutputStream result = new ByteArrayOutputStream();
                InputStream inputStream = new FileInputStream(path.toFile())) {
            byte[] buffer = new byte[1024];
            int length;
            while (result.size() <= 50000 && (length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            if (inputStream.read() != -1) {
                result.write("...".getBytes());
            }
            return result.toString();
        } catch (IOException exception) {
            BusinessLogicLogging.logger.log(Level.FINE, "Exception while reading file: " + path.toString(), exception);
            return null;
        }
    }
    
}
