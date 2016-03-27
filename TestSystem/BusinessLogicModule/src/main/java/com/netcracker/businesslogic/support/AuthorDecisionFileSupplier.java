package com.netcracker.businesslogic.support;

import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.testingmodule.system.CodeFileSupplier;
import java.nio.file.Path;

public class AuthorDecisionFileSupplier implements CodeFileSupplier {

    private String problemFolder;
    private String authorDecisionFolder;
    private FileSupplier fileSupplier;

    public AuthorDecisionFileSupplier(String problemFolder, String authorDecisionFolder, FileSupplier fileSupplier) {
        this.problemFolder = problemFolder;
        this.authorDecisionFolder = authorDecisionFolder;
        this.fileSupplier = fileSupplier;
    }
    
    @Override
    public Path getFolder() {
        return fileSupplier.getAuthorDecisionFolder(problemFolder, authorDecisionFolder);
    }

    @Override
    public Path getSourceFolder() {
        return fileSupplier.getAuthorDecisionSourceFolder(problemFolder, authorDecisionFolder);
    }

    @Override
    public Path getSourceFile() {
        return fileSupplier.getAuthorDecisionSourceFile(problemFolder, authorDecisionFolder);
    }

    @Override
    public Path getCompileFolder() {
        return fileSupplier.getAuthorDecisionCompileFolder(problemFolder, authorDecisionFolder);
    }

    @Override
    public Path getCompileFile() {
        return fileSupplier.getAuthorDecisionCompileFile(problemFolder, authorDecisionFolder);
    }

}