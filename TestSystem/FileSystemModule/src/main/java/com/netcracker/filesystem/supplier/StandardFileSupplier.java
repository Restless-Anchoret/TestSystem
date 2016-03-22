/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.filesystem.supplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import com.netcracker.filesystem.logging.FileSystemLogging;
import java.nio.file.DirectoryStream;

/**
 *
 * @author Администратор
 */
public class StandardFileSupplier implements FileSupplier {

    StandardFileSupplier(Path get) {
        pathFile = Paths.get(System.getProperty("user.dir"));

    }

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

    private Path pathFile;
    private static FileSupplier fileSupplier = null;

    public static FileSupplier getDefault() {
        if (fileSupplier == null) {
            return new StandardFileSupplier(Paths.get(System.getProperty("user.dir")));
        } else {
            return fileSupplier;
        }
    }

    private void checkFileStructure() {

        try {
            Path path = Paths.get(pathFile.toString(), "file_system");
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), "file_system", "problems");
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), "file_system", "submissions");
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), "file_system", "competitions");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            path = Paths.get(pathFile.toString(), "file_system", "temp");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            path = Paths.get(pathFile.toString(), "file_system", "config");

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
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "tests");
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "checker");
                Files.createDirectory(path);
            } else {
                return false;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getProblemFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder);
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Folder");
            return null;
        }
    }

    @Override
    public Path getProblemStatement(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "statement.pdf");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not File Statement ");
            return null;
        }
    }

    @Override
    public Path getProblemCheckerFolder(String problemFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "checker");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not checker Folder");
            return null;
        }
    }

    @Override
    public Path getTestInputFile(String problemFolder, String testGroupType, int testNumber) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "tests", testGroupType, Integer.toString(testNumber), "input.txt");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not input File");
            return null;
        }
    }

    @Override
    public Path getTestAnswerFile(String problemFolder, String testGroupType, int testNumber) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "tests", testGroupType, Integer.toString(testNumber), "answer.txt");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not answer File");
            return null;
        }
    }

    @Override
    public boolean addAuthorDecisionFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, authorDecisionFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "author_decisions", authorDecisionFolder, "bin");
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "author_decisions", authorDecisionFolder, "scr");
                Files.createDirectory(path);
            } else {
                return false;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getAuthorDecisionFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "problems", problemFolder, "author_decisions", authorDecisionFolder);
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not autorDecision Folder");
            return null;
        }

    }

    @Override
    public Path getAuthorDecisionSourceFolder(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getAuthorDecisionSourceFile(String problemFolder, String authorDecisionFolder) {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getAuthorDecisionCompileFolder(String submiproblemFolder, String authorDecisionFolderssionFolder) {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getAuthorDecisionCompileFile(String submisproblemFolder, String authorDecisionFoldersionFolder) {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addSubmissionFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "bin");
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "src");
                Files.createDirectory(path);
            } else {
                return false;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getSubmissionFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder);
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionSourceFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "src");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionSourceFile(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "src");
        path = nameFile(path);

        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission scr File");
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFolder(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "bin");
        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission Folder");
            return null;
        }
    }

    @Override
    public Path getSubmissionCompileFile(String submissionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "submissions", submissionFolder, "bin");
        path = nameFile(path);

        if (!Files.exists(path)) {
            return path;
        } else {
            FileSystemLogging.logger.fine("Not Submission scr File");
            return null;
        }
    }

    @Override
    public boolean addCompetitionFolder(String competitionFolder) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "competitions");
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                path = Paths.get(pathFile.toString(), "file_system", "competitions", competitionFolder);
                Files.createDirectory(path);
            } else {
                return false;
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting) {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "competitions", competitionFolder, "visible_results.xml");
        if (!checkExisting) {
            return path;
        } else {
            try {
                if (!Files.exists(path)) {
                    Files.createDirectory(path);
                } else {
                    FileSystemLogging.logger.fine("Not Submission scr File");
                    return null;
                }

            } catch (IOException e) {
                FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
            }

            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public Path getTempFile() {
        checkFileStructure();
        Path path = Paths.get(pathFile.toString(), "file_system", "temp");

        try {
            path = Files.createTempFile(path, null, ".txt");
            return path;
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTempFile(Path path) {
        checkFileStructure();

        try {
            if (Files.exists(path)) {
                Files.delete(path);
            } else {
                FileSystemLogging.logger.fine("Not Temp File");
            }
        } catch (IOException e) {
            FileSystemLogging.logger.log(Level.FINE, "IOException while creating folders", e);
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAllTempFiles() {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getConfigurationFolder() {
        checkFileStructure();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
