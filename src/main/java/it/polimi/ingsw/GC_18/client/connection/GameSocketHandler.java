package it.polimi.ingsw.GC_18.client.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.model.Game;

/**
 * This class handles the connection with server using sockets that send and receive object of Game type
 */
class GameSocketHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(CommandsSocketHandler.class.getName());
    private static Socket socket;
    private static ObjectInputStream inReader;

    /**
     * Creates connection with server, creates a new thread for the incoming
     * asynchronous inputs and sets readers to it
     * @param serverIp - the server IP address
     * @param serverPort - the server port
     * @throws IOException - for sockets error
     */
    GameSocketHandler(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        inReader = new ObjectInputStream(socket.getInputStream());
        Main.getMainThreadPool().submit(this);
    }

    /**
     * Registers on the mainThreadPool a new thread for asynchronous inputs from
     * server and listens for them
     */
    @Override
    public void run() {
        try {
            Object objectRead;
            while ((objectRead = inReader.readObject()) != null) {
                Controller.handleModelUpdate((Game) objectRead);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "SOCKET - CONNECTION ERROR", e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "CLASS CAST PROBLEM", e);
        }
    }

}
