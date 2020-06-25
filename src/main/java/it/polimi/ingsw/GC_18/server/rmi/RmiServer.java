package it.polimi.ingsw.GC_18.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.GC_18.client.connection.RmiHandlerInterface;
import it.polimi.ingsw.GC_18.server.Client;

/**
 * Handles the connection for the clients that choose to use RMI
 */
public class RmiServer extends UnicastRemoteObject implements RmiServerInterface {

    private static final long serialVersionUID = 2592026484180445574L;

    private static volatile Map<String, RmiHandlerInterface> clientRmiHandlers = new HashMap<>();// Keeps track of the connected clients

    /**
     * Sets up a handler for the RMI connections using the UnicastRemoteObject class
     * @throws RemoteException - for RMI problems
     */
    public RmiServer() throws RemoteException {
        super(0);
    }

    @Override
    public void receiveClientRequest(String username, String request) throws RemoteException {
        Client.getClients().get(username).handleInput(request);
    }

    @Override
    public void registerOnServer(String username, String password, RmiHandlerInterface rmiHandler)
            throws RemoteException {
        clientRmiHandlers.put(username, rmiHandler);
        new ClientRmi(username, password);
    }

    /**
     * Disconnects the client that as the user name passed as parameter
     * @param username - the user name of the client to disconnect
     */
    static void disconnectFromServer(String username) {
        clientRmiHandlers.remove(username);
    }

    /**
     * @return the map of clients that has as key the user name and as value the
     * RMI handler interface
     */
    static Map<String, RmiHandlerInterface> getClientRmiHandlers() {
        return clientRmiHandlers;
    }

}
