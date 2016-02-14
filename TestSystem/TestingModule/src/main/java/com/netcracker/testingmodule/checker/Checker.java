package com.netcracker.testingmodule.checker;

import com.netcracker.testingmodule.system.Verdict;
import java.nio.file.Path;

public interface Checker {

    Verdict check(Path inputPath, Path outputPath, Path answerPath);

}