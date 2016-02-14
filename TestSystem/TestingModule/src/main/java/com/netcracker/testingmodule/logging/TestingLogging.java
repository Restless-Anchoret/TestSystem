package com.netcracker.testingmodule.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingLogging {

    public static final String loggerName = "testing";
    public static final Logger logger = Logger.getLogger(loggerName);
    static {
        logger.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler("testing.log");
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException exception) {
            logger.fine("Cannot create FileHandler");
        }
    }
    
}