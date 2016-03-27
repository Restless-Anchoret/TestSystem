package com.netcracker.businesslogic.support;

import com.netcracker.filesystem.supplier.FileSupplier;
import com.netcracker.testingmodule.system.TestingFileSupplier;
import java.nio.file.Path;

public class TestingFileSupplierImpl implements TestingFileSupplier {

    private FileSupplier fileSupplier;

    public TestingFileSupplierImpl(FileSupplier fileSupplier) {
        this.fileSupplier = fileSupplier;
    }
    
    @Override
    public Path getTempFile() {
        return fileSupplier.getTempFile();
    }

    @Override
    public void deleteTempFile(Path path) {
        fileSupplier.deleteTempFile(path);
    }

    @Override
    public void deleteAllTempFiles() {
        fileSupplier.deleteAllTempFiles();
    }

    @Override
    public Path getConfigurationFolder() {
        return fileSupplier.getConfigurationFolder();
    }

}