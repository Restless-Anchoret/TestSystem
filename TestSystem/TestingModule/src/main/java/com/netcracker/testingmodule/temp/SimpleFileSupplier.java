package com.netcracker.testingmodule.temp;

import com.netcracker.testingmodule.logging.TestingLogging;
import com.netcracker.testingmodule.system.TestGroupType;
import com.netcracker.testingmodule.system.TestingFileSupplier;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class SimpleFileSupplier implements TestingFileSupplier {
    
    private static final String fileSystemFolder = "file_system";
    private static final String submissionsMainFolder = "submissions";
    private static final String sourceFolder = "src";
    private static final String compilingFolder = "bin";
    private static final String tempFilesFolder = "temp";
    private static final String problemsMainFolder = "problems";
    private static final String testsFolder = "tests";
    private static final String inputFileName = "input.txt";
    private static final String answerFileName = "output.txt";

    @Override
    public Path getSubmissionFolder(String submissionFolder) {
        return Paths.get(fileSystemFolder, submissionsMainFolder, submissionFolder);
    }

    @Override
    public Path getSubmissionSourceFolder(String submissionFolder) {
        return Paths.get(fileSystemFolder, submissionsMainFolder, submissionFolder, sourceFolder);
    }

    @Override
    public Path getSubmissionSourceFile(String submissionFolder) {
        return Paths.get(fileSystemFolder, submissionsMainFolder, submissionFolder, sourceFolder, "A.java");
    }

    @Override
    public Path getSubmissionCompileFolder(String submissionFolder) {
        Path path = Paths.get(fileSystemFolder, submissionsMainFolder, submissionFolder, compilingFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFile(String submissionFolder) {
        return Paths.get(fileSystemFolder, submissionsMainFolder, submissionFolder, compilingFolder, "A.class");
    }

    @Override
    public Path getTempFile() {
        Path tempFilesPath = Paths.get(fileSystemFolder, tempFilesFolder);
        try {
            if (!Files.exists(tempFilesPath)) {
                Files.createDirectory(tempFilesPath);
            }
            return Files.createTempFile(tempFilesPath, null, ".txt");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteTempFile(Path path) {
        if (path == null) {
            return;
        }
        try {
            Files.deleteIfExists(path);
        } catch (IOException exception) {
            TestingLogging.logger.log(Level.FINE, "IOException while deleting temp file", exception);
        }
    }

    @Override
    public void deleteAllTempFiles() {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(Paths.get(fileSystemFolder, tempFilesFolder))) {
            for (Path path: entries) {
                Files.deleteIfExists(path);
            }
        } catch (IOException exception) {
            TestingLogging.logger.log(Level.FINE, "IOException while deleting temp file", exception);
        }
    }

    @Override
    public Path getTestInputFile(String problemFolder, TestGroupType type, int testNumber) {
        return Paths.get(fileSystemFolder, problemsMainFolder, problemFolder, testsFolder, type.name().toLowerCase(),
                Integer.toString(testNumber), inputFileName);
    }

    @Override
    public Path getTestAnswerFile(String problemFolder, TestGroupType type, int testNumber) {
        return Paths.get(fileSystemFolder, problemsMainFolder, problemFolder, testsFolder, type.name().toLowerCase(),
                Integer.toString(testNumber), answerFileName);
    }

}