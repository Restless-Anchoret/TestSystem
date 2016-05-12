package com.netcracker.filesystem.supplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import com.netcracker.filesystem.logging.FileSystemLogging;
import java.nio.file.DirectoryStream;

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
    private static final String ANSWER = "output.txt";
    private static final String INPUT = "input.txt";
    private static final String VISIBLE_RESULTS = "visible_results.xml";
    private static final String STATEMENT = "statement.pdf";

    private static FileSupplier fileSupplier = null;

    public static FileSupplier getDefault() {
        if (fileSupplier == null) {
            fileSupplier = new StandardFileSupplier(Paths.get(System.getProperty("user.dir")));
        }
        return fileSupplier;
    }
    
    private static Path getFirstFileInDirectory(Path path) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                if (!Files.isDirectory(entry)) {
                    return entry;
                }
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while watching folder content: " + path.toString(), e);
        }
        return null;
    }

    private static Path getFirstFileWithName(Path path, String fileName) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                String name = entry.getFileName().toString();
                if (getFileNameWithoutExtension(name).equals(fileName)) {
                    return entry;
                }
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while watching folder content: " + path.toString(), e);
        }
        return null;
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }
    
    private Path pathFile;

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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating file system structure", e);
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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folder for problem: " + problemFolder, e);
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
            FileSystemLogging.logger.log(Level.FINE, "Problem folder \"{0}\" does not exist", problemFolder);
            return null;
        }
    }
    
    @Override
    public Path getProblemStatement(String problemFolder, boolean checkExisting) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, STATEMENT);
        if (!checkExisting || Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Statement does not exist for problem: {0}", problemFolder);
            return null;
        }
    }
    
    @Override
    public Path getProblemStatement(String problemFolder) {
        return getProblemStatement(problemFolder, true);
    }

    @Override
    public Path getProblemCheckerFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, CHECKER);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Checker folder does not exist for problem: {0}", problemFolder);
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
            FileSystemLogging.logger.log(Level.FINE, "Input file does not exist for problem ({0}, {1}, {2})",
                    new Object[] {problemFolder, testGroupType, testNumber});
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
            FileSystemLogging.logger.log(Level.FINE, "Answer file does not exist for problem ({0}, {1}, {2})",
                    new Object[] {problemFolder, testGroupType, testNumber});
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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folder for author decision (" +
                    problemFolder + ", " + authorDecisionFolder + ")", e);
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
            FileSystemLogging.logger.log(Level.FINE, "Author decision folder does not exist ({0}, {1})",
                    new Object[] {problemFolder, authorDecisionFolder});
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
            FileSystemLogging.logger.log(Level.FINE, "Author decision source folder does not exist ({0}, {1})",
                    new Object[] {problemFolder, authorDecisionFolder});
            return null;
        }
    }

    @Override
    public Path getAuthorDecisionSourceFile(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, SRC);
        path = getFirstFileInDirectory(path);
        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Author decision source file does not exist ({0}, {1})",
                    new Object[] {problemFolder, authorDecisionFolder});
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
            FileSystemLogging.logger.log(Level.FINE, "Author decision compile folder does not exist ({0}, {1})",
                    new Object[] {problemFolder, authorDecisionFolder});
            return null;
        }
    }

    @Override
    public Path getAuthorDecisionCompileFile(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path pathSourceFile = getAuthorDecisionSourceFile(problemFolder, authorDecisionFolder);
        if (pathSourceFile == null) {
            return null;
        }
        String sourceFileName = getFileNameWithoutExtension(pathSourceFile.getFileName().toString());
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, PROBLEMS, problemFolder, AUTHOR_DECISIONS, authorDecisionFolder, BIN);
        path = getFirstFileWithName(path, sourceFileName);
        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Author decision compile file does not exist ({0}, {1})",
                    new Object[] {problemFolder, authorDecisionFolder});
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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folder for submission (" +
                    submissionFolder + ")", e);
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
            FileSystemLogging.logger.log(Level.FINE, "Submission folder does not exist ({0})", submissionFolder);
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
            FileSystemLogging.logger.log(Level.FINE, "Submission source folder does not exist ({0})", submissionFolder);
            return null;
        }
    }

    @Override
    public Path getSubmissionSourceFile(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, SRC);
        path = getFirstFileInDirectory(path);
        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Submission source file does not exist ({0})", submissionFolder);
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
            FileSystemLogging.logger.log(Level.FINE, "Submission compile folder does not exist ({0})", submissionFolder);
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFile(String submissionFolder) {
        checkFileStructure();
        Path pathSourceFile = getSubmissionSourceFile(submissionFolder);
        if (pathSourceFile == null) {
            return null;
        }
        String sourceFileName = getFileNameWithoutExtension(pathSourceFile.getFileName().toString());
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, SUBMISSIONS, submissionFolder, BIN);
        path = getFirstFileWithName(path, sourceFileName);
        if (path != null && Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Submission compile file does not exist ({0})", submissionFolder);
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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folder for competition: " + competitionFolder, e);
        }
        return false;
    }

    @Override
    public Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, COMPETITIONS, competitionFolder, VISIBLE_RESULTS);
        if (!checkExisting) {
            return path;
        }
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.log(Level.FINE, "Competition visible results file does not exist: {0}", competitionFolder);
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
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating temp file", e);
            return null;
        }
    }

    @Override
    public void deleteTempFile(Path path) {
        checkFileStructure();
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while trying to delete temp file: " + path, e);
        }
    }

    @Override
    public void deleteAllTempFiles() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, TEMP);
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
            for (Path entry : entries) {
                Files.deleteIfExists(entry);
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while trying to delete all temp files", e);
        }
    }

    @Override
    public Path getConfigurationFolder() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), FILE_SYSTEM, CONFIG);
        if (Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Configuration folder does not exist");
            return null;
        }
    }

}
