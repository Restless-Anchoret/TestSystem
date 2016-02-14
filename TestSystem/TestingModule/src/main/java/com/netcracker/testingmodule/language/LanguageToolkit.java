package com.netcracker.testingmodule.language;

import java.nio.file.Path;

public interface LanguageToolkit {
    
    int compile(Path sourceFile, Path compileFolder) throws FailException;
    ExecutionInfo execute(Path compileFile, Path inputFile, Path outputFile,
            int timeLimit, short memoryLimit) throws FailException, TimeLimitException;

    public class ExecutionInfo {
        
        private int exitCode;
        private Integer decisionTime;
        private Short decisionMemory;

        public ExecutionInfo(int exitCode, Integer decisionTime, Short decisionMemory) {
            this.exitCode = exitCode;
            this.decisionTime = decisionTime;
            this.decisionMemory = decisionMemory;
        }

        public int getExitCode() {
            return exitCode;
        }

        public Integer getDecisionTime() {
            return decisionTime;
        }

        public Short getDecisionMemory() {
            return decisionMemory;
        }
        
    }
    
}