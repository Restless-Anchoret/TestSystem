package com.netcracker.monitoring.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitoringLogging {

    public static final String loggerName = "monitoring";
    public static final Logger logger = Logger.getLogger(loggerName);
    static {
        logger.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler(loggerName + ".log");
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException exception) {
            logger.log(Level.FINE, "Cannot create FileHandler", exception);
        }
    }
    
}
