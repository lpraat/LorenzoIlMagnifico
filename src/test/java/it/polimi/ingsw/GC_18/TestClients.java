package it.polimi.ingsw.GC_18;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides utilities for mocking clients processes
 */
class TestClients {

    private static final int PORT_OFFSET = 10058;

    /**
     * Sets up a mocking of clients specified as arguments starting a process for each client
     * (done using the class SimulateClientDebug's main)
     * @param args - a list of clients name, used for accessing the file which
     * contains the commands to send to the mocked client instance
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = SimulateClientDebug.class.getCanonicalName();
        List<String> clientsName = new ArrayList<>();
        clientsName.addAll(Arrays.asList(args));
        for (int i = 1; i < clientsName.size(); i++) {
            String port = String.valueOf(PORT_OFFSET + i);
            new Thread() {
                @Override
                public void run() {
                    ProcessBuilder builder;
                    String[] commands = new String[] { javaBin, "-cp", classpath, className, port };
                    builder = new ProcessBuilder(commands);
                    try {
                        builder.start().waitFor();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

}
