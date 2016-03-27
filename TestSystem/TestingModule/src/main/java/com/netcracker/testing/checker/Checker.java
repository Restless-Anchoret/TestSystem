package com.netcracker.testing.checker;

import com.netcracker.testing.system.Verdict;
import java.nio.file.Path;

public interface Checker {

    Verdict check(Path inputPath, Path outputPath, Path answerPath);

}