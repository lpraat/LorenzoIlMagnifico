package it.polimi.ingsw.GC_18.client.connection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.server.rmi.RmiServerInterface;

/**
 * This class handles the connection with server using java's RMI
 */
public final class RmiHandler extends UnicastRemoteObject implements RmiHandlerInterface {

    private static final Logger LOGGER = Logger.getLogger(RmiHandler.class.getName());
    private static final long serialVersionUID = -3992123533878149436L;
    private static RmiServerInterface rmiServerInterface;

    /**
     * Initializes this handler for RMI connection
     * @throws RemoteException - RMI errors
     */
    RmiHandler() throws RemoteException {
        super();
    }

    /**
     * Sends to server a message using RMI connection
     * @param message - the message to send to server
     */
    public static void outputToServer(String message) {
        try {
            rmiServerInterface.receiveClientRequest(User.getUsername(), message);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "RMI UNABLE TO OUTPUT TO SERVER", e);
        }
    }

    /**
     * Receives the current game state from server and handles it
     * @param game - the server current game instance
     * @throws RemoteException - for RMI problems
     */
    @Override
    public synchronized void receiveFromServer(Game game) throws RemoteException {
        Controller.handleModelUpdate(game);
    }

    /**
     * Receives messages from the server and handles them
     * @param inputFromServer - the message received from the server
     */
    @Override
    public synchronized void receiveFromServer(String inputFromServer) throws RemoteException {
        Controller.handleInputFromServer(inputFromServer);
    }

    /**
     * Sets the server RMI interface
     * @param rmiServerInterface the rmi server interface.
     */
    static void setRmiServerInterface(RmiServerInterface rmiServerInterface) {
        RmiHandler.rmiServerInterface = rmiServerInterface;
    }

}
