package com.netcracker.testingmodule.system;

import java.nio.file.Path;

public interface ProblemFileSupplier {

    Path getTestInputFile(TestGroupType type, int testNumber);
    Path getTestAnswerFile(TestGroupType type, int testNumber);
    
}