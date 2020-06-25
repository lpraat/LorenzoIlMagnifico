package it.polimi.ingsw.GC_18.client.connection;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.server.rmi.RmiServerInterface;

/**
 * This class handles the connection with the server in both cases of RMI and socket.
 */
public final class ConnectionHandler {

    private static final Logger LOGGER = Logger.getLogger(ConnectionHandler.class.getName());
    private static boolean rmiConnection;

    /**
     * Hiding constructor, since this class doen't have to be instantiated
     */
    private ConnectionHandler() {
    }

    /**
     * Starts the connection with server based on the parameter for the type.
     * @param rmiConnection - true if connection will happen with RMI, false otherwise
     * @throws IOException - connection error
     */
    public static void startConnectionHandler(boolean rmiConnection) throws IOException {
        ConnectionHandler.rmiConnection = rmiConnection;
        if (rmiConnection) {// RMI
            try {
                Registry reg = LocateRegistry.getRegistry(Main.getClientProperties().getProperty("RMISERVERIP"),
                        Integer.parseInt(Main.getClientProperties().getProperty("RMISERVERPORT")));
                RmiServerInterface rmiServer = (RmiServerInterface) reg.lookup("RmiServer");
                RmiHandler rmiHandler = new RmiHandler();
                RmiHandler.setRmiServerInterface(rmiServer);
                rmiServer.registerOnServer(User.getUsername(), User.getPassword(), rmiHandler);
            } catch (NotBoundException e) {
                LOGGER.log(Level.SEVERE, "RMI CONNECTION ERROR", e);
            }
        } else {// socket
            new CommandsSocketHandler(Main.getClientProperties().getProperty("SOCKETSERVERIP"),
                    Integer.parseInt(Main.getClientProperties().getProperty("SOCKETSERVERPORT0")));
            new GameSocketHandler(Main.getClientProperties().getProperty("SOCKETSERVERIP"),
                    Integer.parseInt(Main.getClientProperties().getProperty("SOCKETSERVERPORT1")));
        }
    }

    /**
     * Sends to server a message choosing the right type of connection
     * @param output - the message to send to server
     */
    public static void outputToServer(String output) {
        if (rmiConnection) {// RMI
            RmiHandler.outputToServer(output);
        } else {// socket
            CommandsSocketHandler.outputToServer(output);
        }
    }

    /**
     * After notifying server, cLoses all connections with it.
     */
    public static void disconnect() {
        outputToServer("DISCONNECTING");
        if (!rmiConnection) {
            CommandsSocketHandler.disconnect();
        }
    }

}
