package it.polimi.ingsw.GC_18.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.server.Client;

/**
 * Handles the communication with a client using sockets
 */
public class ClientSocket extends Client implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());

    private final Socket socket0;
    private final Socket socket1;
    private final BufferedReader inReader;
    private final PrintWriter outWriter;
    private final ObjectOutputStream objectOutputStream;

    /**
     * Handler of socket connections for a client.
     * @param socket0 - used for string IOs
     * @param socket1 - used for Game object output
     * @throws IOException - for sockets error
     */
    public ClientSocket(Socket socket0, Socket socket1) throws IOException {
        super();
        this.socket0 = socket0;
        this.socket1 = socket1;
        inReader = new BufferedReader(new InputStreamReader(socket0.getInputStream()));
        outWriter = new PrintWriter(new OutputStreamWriter(socket0.getOutputStream(),StandardCharsets.UTF_8), true);
        objectOutputStream = new ObjectOutputStream(socket1.getOutputStream());
    }

    @Override
    public void closeConnection() {
        try {
            inReader.close();
            outWriter.close();
            objectOutputStream.close();
            socket0.close();
            socket1.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "ERROR CLOSING SOCKET CONNECTION", e);
        }
    }

    @Override
    public void run() {
        try {
            String inputFromClient;
            while ((inputFromClient = inReader.readLine()) != null) {
                handleInput(inputFromClient);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "SOCKET OF CLIENT DISCONNECTED", e);
        } finally {
            disconnect();
        }
    }

    @Override
    public synchronized void outputToClient(String message) {
        outWriter.println(message.replaceAll("\\R", "backslashn"));// check that the the replacement is the same as the RegeEx on the client
    }

    @Override
    public synchronized void sendGameUpdate(Game game) {
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "UNABLE TO SEND GAME UPDATE THROUGH SOCKET", e);
        }
    }

}