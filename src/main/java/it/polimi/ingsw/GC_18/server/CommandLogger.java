package it.polimi.ingsw.GC_18.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class logs Strings to file in append mode.
 * It's used to log the commands received from the server.
 */
public class CommandLogger {

    private static final Logger LOGGER = Logger.getLogger(CommandLogger.class.getName());
    private FileWriter fileWriter;
    private PrintWriter printWriter;

    /**
     * Instantiates a logger to the file specified as parameter that appends string to it
     * @param basePath - the path to the file to use for logging
     */
    public CommandLogger(String basePath) {
        try {
            File file=new File(basePath + ".txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                if (!file.createNewFile())
                    return;
            }
            fileWriter = new FileWriter(basePath + ".txt");
            printWriter = new PrintWriter(new BufferedWriter(fileWriter));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"Error while starting command logger",e);
            close();
        }
    }

    /**
     * Registers a client for the logging
     * @param credentials - client's credentials (user name, password)
     * @param connectionFlag - true if RMI, false if socket
     */
    void registerOnLog(Credentials credentials, boolean connectionFlag) {
        printWriter.write(credentials.getUsername()+" - "+
                "LOGIN - " + credentials.getUsername() + " - " + credentials.getPassword() + " - " + connectionFlag + " - GUI\n");
        printWriter.flush();
    }

    /**
     * Logs the commands specified, stating from whom it comes
     * @param credentials - the client's that log credentials
     * @param commandToLog - the command to log
     */
    void logCommand(Credentials credentials, String commandToLog) {
        printWriter.write(credentials.getUsername()+" - "+commandToLog + "\n");
        printWriter.flush();
    }

    /**
     * Closes the printWriter used for the logging
     */
    private void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"Error while closing command logger",e);
        }
        printWriter.close();
    }

}
