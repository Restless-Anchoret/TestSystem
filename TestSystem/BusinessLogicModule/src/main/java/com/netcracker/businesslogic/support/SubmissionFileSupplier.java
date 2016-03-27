package com.netcracker.businesslogic.support;

import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.testingmodule.system.CodeFileSupplier;
import java.nio.file.Path;

public class SubmissionFileSupplier implements CodeFileSupplier {

    private String submissionFolder;
    private FileSupplier fileSupplier;

    public SubmissionFileSupplier(String submissionFolder, FileSupplier fileSupplier) {
        this.submissionFolder = submissionFolder;
        this.fileSupplier = fileSupplier;
    }
    
    @Override
    public Path getFolder() {
        return fileSupplier.getSubmissionFolder(submissionFolder);
    }

    @Override
    public Path getSourceFolder() {
        return fileSupplier.getSubmissionSourceFolder(submissionFolder);
    }

    @Override
    public Path getSourceFile() {
        return fileSupplier.getSubmissionSourceFile(submissionFolder);
    }

    @Override
    public Path getCompileFolder() {
        return fileSupplier.getSubmissionCompileFolder(submissionFolder);
    }

    @Override
    public Path getCompileFile() {
        return fileSupplier.getSubmissionCompileFile(submissionFolder);
    }

}