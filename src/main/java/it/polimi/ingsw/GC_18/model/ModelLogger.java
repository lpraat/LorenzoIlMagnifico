package it.polimi.ingsw.GC_18.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The logger used by the model for logging. It logs everything in modelLogs files.
 */
public class ModelLogger {
    private static final Logger LOGGER = Logger.getLogger(ModelLogger.class.getName());
    private static ModelLogger instance = null;
    private Logger fileLogger;
    private FileHandler fileHandler;


    /**
     * Creates a new ModelLogger.
     */
    private ModelLogger() {
        fileLogger = Logger.getLogger(ModelLogger.class.getName());
        fileLogger.setUseParentHandlers(false);
        try {
            fileHandler = new FileHandler("logs/modelLogs.log", true);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not initialize ModelLogger", e);
        }
        fileHandler.setFormatter(new SimpleFormatter());
        fileLogger.addHandler(fileHandler);
    }

    /**
     * @return the model logger instance if present, else it creates a new one.
     */
    public static ModelLogger getInstance() {
        if (instance == null) {
            instance = new ModelLogger();
        }
        return instance;
    }

    /**
     * Logs an exception.
     * @param level the level of the log.
     * @param msg the message.
     * @param e the exception to be logged.
     */
    public void log(Level level, String msg, Exception e) {
        fileLogger.log(level, msg, e);
    }

    /**
     * Logs a message.
     * @param msg the message to be logged.
     */
    public void logInfo(String msg) {
        fileLogger.info(msg);
    }

}
