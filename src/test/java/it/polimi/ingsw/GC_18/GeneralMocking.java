package it.polimi.ingsw.GC_18;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.client.gui.Terminal;
import it.polimi.ingsw.GC_18.server.CommandLogger;
import it.polimi.ingsw.GC_18.server.Room;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Utility class for testing the entire application with commands saved in files.
 */
class GeneralMocking {

    private static final int PORT_OFFSET = 10058;
    private static final String baseLogPath = "resources/test/commands_log_";
    private static final long sleepPerCommand = 1000L;

    private static String[] clientsName;
    private static String commandsToExecutePath;
    private static String expectedLogPath;
    private static String testLogPath;
    private static int commandsNumber;

    /**
     * Sets up a general test for the application. Starts a server and a client
     * on the Test JVM and creates other processes for the other clients to mock
     * @param clientsName - the names of the clients under test
     * @return true if the log created running the mocking is the same as the expected log,
     * obtained manually running the application
     */
    static boolean doGeneralMocking(String[] clientsName) {
        GeneralMocking.clientsName = clientsName;
        commandsToExecutePath = baseLogPath + String.join("_", clientsName) + ".txt";
        expectedLogPath = baseLogPath + "expected_" + String.join("_", clientsName) + ".txt";
        testLogPath = baseLogPath + "test_" + String.join("_", clientsName);// doesn't have final ".txt" because commands logger adds it itself
        new File(testLogPath+".txt").delete();
        start();
        try {
            return compareLogs(testLogPath + ".txt", expectedLogPath);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Starts this test and waits for its completion
     */
    private static void start() {
        try {
            startMocking();
            sendCommands();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        // waiting for completion of task
        try {
            Thread.sleep(sleepPerCommand * (commandsNumber + 2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a server and some fake clients
     * @throws InterruptedException
     */
    private static void startMocking() throws InterruptedException {
        startServer();
        startOtherClients();
        startClientUnderTest();
        Thread.sleep(5000L);
    }

    /**
     * Start a game server
     */
    private static void startServer() {
        it.polimi.ingsw.GC_18.server.Main.main(null);
        File testLog = new File(testLogPath);
        if (testLog.exists()) {
            testLog.delete();
        }
        it.polimi.ingsw.GC_18.server.Main.getCommandLoggers().add(new CommandLogger(testLogPath));
        Room.setGameStartingTimeout(3000L);
    }

    /**
     * Starts a client in this process
     */
    private static void startClientUnderTest() {
        it.polimi.ingsw.GC_18.client.Main.main(null);
    }

    /**
     * Starts other clients, each in a new process
     */
    private static void startOtherClients() {
        try {
            TestClients.main(clientsName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends commands to the client under test and the others
     * @throws IOException
     * @throws InterruptedException
     */
    private static void sendCommands() throws IOException, InterruptedException {
        String[] logLines = Utils.loadFileAsString(commandsToExecutePath).split("\n");
        commandsNumber = logLines.length;
        Socket[] sockets = new Socket[clientsName.length];
        PrintWriter[] printWriters = new PrintWriter[clientsName.length];
        for (int i = 1; i < clientsName.length; i++) {
            sockets[i] = new Socket("localhost", PORT_OFFSET + i);
            printWriters[i] = new PrintWriter(
                    new OutputStreamWriter(sockets[i].getOutputStream(), StandardCharsets.UTF_8), true);
        }
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            for (String logLine : logLines) {
                String[] logLineParts = logLine.split(" - ", 2);
                String clientName = logLineParts[0];
                int i = -1;
                for (int j = 0; j < clientsName.length; j++) {
                    if (clientsName[j].equals(clientName)) {
                        i = j;
                        break;
                    }
                }
                if (i == 0) {
                    Terminal.handleInputFromUser(logLineParts[1]);
                } else if (i != -1)
                    printWriters[i].println(logLineParts[1]);
                try {
                    Thread.sleep(sleepPerCommand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Socket socket : sockets) {
                if (socket == null)
                    continue;
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Compares two log files based on their contents, cleaning the first file
     * @param pathToLog1 - abstract path to the log file that need to be cleaned before comparing
     * @param pathToLog2 - abstract path to the second log file to compare
     * @return true if the two logs match exactly after cleaning
     * @throws IOException - for file reading errors
     */
    private static boolean compareLogs(String pathToLog1, String pathToLog2) throws IOException {
        String log1 = cleanLogFile(pathToLog1);
        String log2 = Utils.loadFileAsString(pathToLog2);
        return log1.trim().equalsIgnoreCase(log2.trim());
    }

    /**
     * Cleans a file content in order to make it comparable with the expected
     * @param path - path to the file to clean
     * @return the cleaned log file content
     */
    private static String cleanLogFile(String path) {
        String[] logLines = Utils.loadFileAsString(path).split("\n");
        List<String> logLinesCleaned = new ArrayList<>();
        for (String line : logLines) {
            String command = line.split(" - ")[1];
            if (!("FIND_GAME".equalsIgnoreCase(command) || "DISCONNECTING".equalsIgnoreCase(command) || "CHOOSE".equalsIgnoreCase(command) || "PASS".equalsIgnoreCase(command) || "CHAT".equalsIgnoreCase(command))) {
                logLinesCleaned.add(line);
            }
        }
        return logLinesCleaned.stream().collect(Collectors.joining("\n"));
    }

}
