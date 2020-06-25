package it.polimi.ingsw.GC_18.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.Main;

/**
 * This class handles the connection with server using sockets that send and receive strings
 */
final class CommandsSocketHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(CommandsSocketHandler.class.getName());
    private static Socket socket;
    private static BufferedReader inReader;
    private static PrintWriter outWriter;

    /**
     * Creates connection with server, creates a new thread for the incoming asynchronous inputs and sets readers and writers to it
     * @param serverIp - the server IP address
     * @param serverPort - the server port
     * @throws IOException - for sockets error
     */
    CommandsSocketHandler(String serverIp, int serverPort) throws IOException {
        socket=new Socket(serverIp,serverPort);
        outWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8), true);
        inReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Main.getMainThreadPool().submit(this);
    }

    /**
     * Registers on the mainThreadPool a new thread for asynchronous inputs from server and listens for them
     */
    @Override
    public void run() {
        try {
            String inputFromServer;
            while((inputFromServer=inReader.readLine())!=null) {
                Controller.handleInputFromServer(inputFromServer.replaceAll("backslashn", "\n"));// check that the RegEx is the same as the replacement on the server
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "SOCKET - CONNECTION ERROR", e);
        } finally {
            disconnect();
        }
    }

    /**
     * Writes to server a message
     * @param output - the message to send to server
     */
    public static void outputToServer(String output) {
        outWriter.println(output);
    }

    /**
     * Closes connection with server
     */
    static void disconnect() {
        try {
            inReader.close();
            outWriter.close();
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "PROBLEM CLOSING SOCKET", e);
        }
    }

}