package it.polimi.ingsw.GC_18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.client.gui.Terminal;

/**
 * Mocks a client process
 */
class SimulateClient implements Runnable {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inReader;
    private static final Logger LOGGER = Logger.getLogger(SimulateClient.class.getName());

    /**
     * Mocks a client sending the commands received through socket
     * @param commands - the commands to send through socket to the mocked client instance
     * @throws IOException
     */
    SimulateClient(String[] commands) throws IOException {
        serverSocket = new ServerSocket(Integer.parseInt(commands[0]));
        socket = serverSocket.accept();
        inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Main.main(null);
        run();
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        try {
            String inputFromClient;
            while ((inputFromClient = inReader.readLine()) != null) {
                Terminal.handleInputFromUser(inputFromClient);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "SOCKET OF CLIENT DISCONNECTED", e);
        } finally {
            Terminal.handleInputFromUser("EXIT");
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "PROBLEMS CLOSING SOCKET", e);
            }
        }
    }

}