package com.netcracker.testingmodule.language;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaLanguageToolkit implements LanguageToolkit {
    
    private static final OutputStream EMPTY_ERROR_STREAM = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
        }
    };
    
    @Override
    public int compile(Path sourceFile, Path compileFolder) throws FailException {
        if (Files.notExists(sourceFile) || Files.notExists(compileFolder)) {
            throw new FailException("Compilation failed because files not found.");
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.run(null, null, EMPTY_ERROR_STREAM, "-d", compileFolder.toString(), sourceFile.toString());
    }

    @Override
    public ExecutionInfo execute(Path compileFile, Path inputFile, Path outputFile,
            int timeLimit, short memoryLimit) throws FailException, TimeLimitException {
        if (Files.notExists(compileFile) || Files.notExists(inputFile) || Files.notExists(outputFile)) {
            throw new FailException("Execution failed because files not found.");
        }
        try {
            Path compileFolder = compileFile.getParent();
            String className = getClassName(compileFile);
            String memoryOption = "-Xmx" + memoryLimit + "M";
            ProcessBuilder processBuilder = new ProcessBuilder("java", memoryOption, "-Xss8M", className);
            processBuilder.directory(compileFolder.toFile());
            processBuilder.redirectInput(inputFile.toFile());
            processBuilder.redirectOutput(outputFile.toFile());
            Process process = null;
            try {
                long start = System.currentTimeMillis();
                process = processBuilder.start();
                process.waitFor(timeLimit, TimeUnit.MILLISECONDS);
                if (process.isAlive()) {
                    throw new TimeLimitException();
                }
                int decisionTime = (int)(System.currentTimeMillis() - start);
                return new ExecutionInfo(process.exitValue(), decisionTime, null);
            } finally {
                if (process != null) {
                    process.destroyForcibly();
                    process.waitFor();
                }
            }
        } catch (InterruptedException | IOException exception) {
            throw new FailException("InterruptedException or IOException while execution.", exception);
        }
    }
    
    private String getClassName(Path classFile) {
        String fileName = classFile.getFileName().toString();
        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return fileName;
    }

}