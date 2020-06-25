package it.polimi.ingsw.GC_18.client.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.GC_18.model.Game;

/**
 * RMI interface for the client side of RMI connection
 */
public interface RmiHandlerInterface extends Remote {

    /**
     * Called from server to send messages through RMI.
     * @param message -  the message sent
     * @throws RemoteException - due to RMI problems
     */
    void receiveFromServer(String message) throws RemoteException;

    /**
     * Called from server to send the game instance through RMI.
     * @param game - instance of the game running on server
     * @throws RemoteException - due to RMI problems
     */
    void receiveFromServer(Game game) throws RemoteException;

}
