package com.netcracker.businesslogic.support;

import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.monitoring.delegate.FileSystemDelegate;
import java.nio.file.Path;

public class FileSystemDelegateImpl implements FileSystemDelegate {

    private FileSupplier fileSupplier;

    public FileSystemDelegateImpl(FileSupplier fileSupplier) {
        this.fileSupplier = fileSupplier;
    }
    
    @Override
    public Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting) {
        return fileSupplier.getCompetitionVisibleResults(competitionFolder, checkExisting);
    }
    
}