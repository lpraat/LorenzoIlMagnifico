package it.polimi.ingsw.GC_18;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class SimulateClientDebug {

    private static final String DEBUG_FILE_PATH = "resources/test/debug.txt";
    
    /**
     * Starts a simulated client instance passing the arguments and 
     * redirects the System.out to the file indicated by DEBUG_FILE_PATH
     * @param args - commands to send to the mocked client
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File file = new File(DEBUG_FILE_PATH);
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
        new SimulateClient(args);
    }

}