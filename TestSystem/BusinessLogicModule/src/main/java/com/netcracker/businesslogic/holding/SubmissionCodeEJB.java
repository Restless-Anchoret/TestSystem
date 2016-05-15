package com.netcracker.businesslogic.holding;

import com.netcracker.businesslogic.application.ApplicationEJB;
import com.netcracker.database.dal.SubmissionFacadeLocal;
import com.netcracker.database.entity.Submission;
import com.netcracker.filesystem.supplier.FileSupplier;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import java.io.InputStream;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class SubmissionCodeEJB {

    @EJB(beanName = "ApplicationEJB")
    private ApplicationEJB applicationEJB;
    private FileSupplier fileSupplier;

    public String getSubmissionCode(Submission submission) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            fileSupplier = applicationEJB.getFileSupplier();
            InputStream inputStream = new FileInputStream(fileSupplier.getSubmissionSourceFile(submission.getFolderName()).toFile());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            inputStream.close();
            return result.toString();
        }
    }
}
