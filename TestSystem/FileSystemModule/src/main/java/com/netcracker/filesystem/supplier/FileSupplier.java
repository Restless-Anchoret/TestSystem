/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.filesystem.supplier;

import java.nio.file.Path;

/**
 *
 * @author �������������
 */

public interface FileSupplier {
    
 
boolean addProblemFolder(String problemFolder);
Path getProblemFolder(String problemFolder);
Path getProblemStatement(String problemFolder);
Path getProblemCheckerFolder(String problemFolder);

Path getTestInputFile(String problemFolder, String TestGroupType, int testNumber);
Path getTestAnswerFile(String problemFolder, String TestGroupType, int testNumber);

boolean addAuthorDecisionFolder(String problemFolder, String authorDecisionFolder);
Path getAuthorDecisionFolder(String problemFolder, String authorDecisionFolder);
Path getAuthorDecisionSourceFolder(String problemFolder, String authorDecisionFolder);
Path getAuthorDecisionSourceFile(String problemFolder, String authorDecisionFolder);
Path getAuthorDecisionCompileFolder(String submiproblemFolder, String authorDecisionFolderssionFolder);
Path getAuthorDecisionCompileFile(String submisproblemFolder, String authorDecisionFoldersionFolder);

boolean addSubmissionFolder(String submissionFolder);
Path getSubmissionFolder(String submissionFolder);
Path getSubmissionSourceFolder(String submissionFolder);
Path getSubmissionSourceFile(String submissionFolder);
Path getSubmissionCompileFolder(String submissionFolder);
Path getSubmissionCompileFile(String submissionFolder);

boolean addCompetitionFolder(String competitionFolder);
Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting);

Path getTempFile();
void deleteTempFile(Path path);
void deleteAllTempFiles();

Path getConfigurationFolder();
}