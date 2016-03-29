package com.netcracker.filesystem.supplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import com.netcracker.filesystem.logging.FileSystemLogging;
import java.nio.file.DirectoryStream;
import static java.nio.file.Files.deleteIfExists;

public class StandardFileSupplier implements FileSupplier {

    private static final String FILE_SYSTEM = "file_system";
    private static final String PROBLEMS = "problems";
    private static final String TESTS = "tests";
    private static final String CHECKER = "checker";
    private static final String AUTHOR_DECISIONS = "author_decisions";
    private static final String SUBMISSIONS = "submissions";
    private static final String COMPETITIONS = "competitions";
    private static final String TEMP = "temp";
    private static final String CONFIG = "config";
    private static final String BIN = "bin";
    private static final String SRC = "src";
    private static final String ANSWER = "answer.txt";
    private static final String INPUT = "input.txt";
    private static final String VISIBLE_RESULTS = "visible_results.xml";
    private static final String STATEMENT = "statement.pdf";

    private Path nameFile(Path path) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                return entry;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        return null;
    }

    private Path allFileInFolder(Path path, String nameFile) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                String name = entry.getFileName().toString();
                if (getNameFile(name).equals(nameFile)) {
                    return entry;
                } else {
                    FileSystemLogging.logger.fine("Not Have This File");
                }
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        return null;
    }

    public static String getNameFile(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }
    private Path pathFile;
    private static FileSupplier fileSupplier = null;

    public static FileSupplier getDefault() {
        if (fileSupplier == null) {
            fileSupplier = new StandardFileSupplier(Paths.get(System.getProperty("user.dir")));
        }
        return fileSupplier;
    }

    public StandardFileSupplier(Path get) {
        pathFile = get;
    }

    private void checkFileStructure() {

        try {
            if (!Files.exists(pathFile)) {
                Files.createDirectories(pathFile);
            }
            Path path = Paths.get(pathFile.toString(), FILE_SYSTEM);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), FILE_SYSTEM, COMPETITIONS);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            path = Paths.get(pathFile.toString(), FILE_SYSTEM, TEMP);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), FILE_SYSTEM, CONFIG);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
    }

    @Override
    public boolean addProblemFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, TESTS);
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, CHECKER);
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS);
                Files.createDirectory(path);
                return true;

            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        return false;
    }

    @Override
    public Path getProblemFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Folder");
            return null;
        }
    }

    @Override
    public Path getProblemStatement(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, STATEMENT);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not File Statement ");
            return null;
        }
    }

    @Override
    public Path getProblemCheckerFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, CHECKER);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not checker Folder");
            return null;
        }
    }

    @Override
    public Path getTestInputFile(String problemFolder, String testGroupType, int testNumber) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, TESTS, testGroupType, Integer.toString(testNumber), INPUT);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not input File");
            return null;
        }
    }

    @Override
    public Path getTestAnswerFile(String problemFolder, String testGroupType, int testNumber) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, TESTS, testGroupType, Integer.toString(testNumber), ANSWER);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not answer File");
            return null;
        }
    }

    @Override
    public boolean addAuthorDecisionFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, BIN);
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, SRC);
                Files.createDirectory(path);
                return true;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        return false;
    }

    @Override
    public Path getAuthorDecisionFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not autorDecision Folder");
            return null;
        }

    }

    @Override
    public Path getAuthorDecisionSourceFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, SRC);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not authorDecision Folder");
            return null;
        }
    }

    @Override
    public Path getAuthorDecisionSourceFile(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, SRC);
        path = nameFile(path);
        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission scr File");
            return null;
        }
    }

    @Override
    public Path getAuthorDecisionCompileFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, BIN);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not autorDecision Folder");
            return null;
        }
    }

    @Override
    public Path getAuthorDecisionCompileFile(String submisproblemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path pathSourceFile = getAuthorDecisionSourceFile(submisproblemFolder, authorDecisionFolder);
        if (pathSourceFile != null) {
            String sourceFile = getNameFile(pathSourceFile.getFileName().toString());
            Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, submisproblemFolder, AUTHOR_DECISIONS, authorDecisionFolder, BIN);
            path = allFileInFolder(path, sourceFile);
            if (path != null && Files.exists(path)) {
                return path;
            } else {
                FileSystemLogging.logger.fine("Not authorDecision bin File");
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean addSubmissionFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, BIN);
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, SRC);
                Files.createDirectory(path);
                return true;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        return false;
    }

    @Override
    public Path getSubmissionFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionSourceFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, SRC);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionSourceFile(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, SRC);
        path = nameFile(path);

        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission scr File");
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, BIN);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFile(String submissionFolder) {
        checkFileStructure();
        Path pathSourceFile = getSubmissionSourceFile(submissionFolder);
        if (pathSourceFile != null) {
            String sourceFile = getNameFile(pathSourceFile.getFileName().toString());
            Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, BIN);
            path = allFileInFolder(path, sourceFile);
            if (path != null && Files.exists(path)) {
                return path;
            } else {
                FileSystemLogging.logger.fine("Not Submission bin File");
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean addCompetitionFolder(String competitionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, COMPETITIONS, competitionFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                return true;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

        return false;
    }

    @Override
    public Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, COMPETITIONS, competitionFolder, VISIBLE_RESULTS);
        if (!checkExisting) {
            return path;
        } else if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission scr File");
            return null;
        }

    }

    @Override
    public Path getTempFile() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, TEMP);

        try {
            path = Files.createTempFile(path, null, ".txt");
            return path;
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        FileSystemLogging.logger.fine("Not Temp  File");
        return null;
    }

    @Override
    public void deleteTempFile(Path path) {
        checkFileStructure();

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

    }

    @Override
    public void deleteAllTempFiles() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, TEMP);
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                deleteIfExists(entry);
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

    }

    @Override
    public Path getConfigurationFolder() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, CONFIG);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

}
