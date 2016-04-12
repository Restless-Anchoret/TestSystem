package com.netcracker.monitoring.delegate;

import java.nio.file.Path;

public interface FileSystemDelegate {

    Path getCompetitionVisibleResults(String competitionFolder, boolean checkExisting);
    
}
