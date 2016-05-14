package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.filesystem.supplier.FileSupplier;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import java.io.InputStream;
import java.nio.file.Files;

public class SubmissionCodeEJB {

    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    private FileSupplier fileSupplier;

    public String getSubmissionCode(Integer ID) throws IOException {
        File file = new File("");//Удалить
        fileSupplier = applicationEJB.getFileSupplier();
        // fileSupplier.getSubmissionCompileFile(submissionFolder);   
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = Files.readAllBytes(file.toPath());
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            inputStream.close();            
            return result.toString("UTF-8");
        }
    }
;
}
