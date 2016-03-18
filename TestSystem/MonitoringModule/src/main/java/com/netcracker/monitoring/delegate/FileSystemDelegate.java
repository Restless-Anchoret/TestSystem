/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.delegate;

import java.nio.file.Path;

/**
 *
 * @author Магистраж
 */
public interface FileSystemDelegate {

    Path getCompetitionVisibleResults(String competitionFolder);
}
