package com.netcracker.testingmodule.system;

import java.nio.file.Path;

public interface CodeFileSupplier {

    Path getFolder();
    Path getSourceFolder();
    Path getSourceFile();
    Path getCompileFolder();
    Path getCompileFile();
    
}
