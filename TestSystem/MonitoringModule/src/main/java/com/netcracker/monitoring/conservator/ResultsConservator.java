/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.monitoring.conservator;

import com.netcracker.monitoring.info.TotalResultInfo;
import java.util.List;

/**
 *
 * @author Магистраж
 */
public interface ResultsConservator {

    List<TotalResultInfo> getVisibleResults(String competitionFolder);

    boolean persistVisibleResults(String competitionFolder, List<TotalResultInfo> results);
}
