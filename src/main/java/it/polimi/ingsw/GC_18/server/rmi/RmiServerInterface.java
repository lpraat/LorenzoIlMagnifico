package it.polimi.ingsw.GC_18.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.GC_18.client.connection.RmiHandlerInterface;

/**
 * RMI interface for the server side of RMI connection
 */
public interface RmiServerInterface extends Remote {

    /**
     * Register the client with the specified credential to the server RMI handler
     * This method has to be called from the client through RMI
     * @param username - the user name of the client to register
     * @param password - the password of the client to register
     * @param rmiHandler - the interface to register
     * @throws RemoteException
     */
    void registerOnServer(String username, String password, RmiHandlerInterface rmiHandler) throws RemoteException;

    /**
     * Receives and handles a client request
     * This method has to be called from the client through RMI
     * @param username - the user name of the client that is making the request
     * @param request - a descriptor of the request
     * @throws RemoteException
     */
    void receiveClientRequest(String username, String request) throws RemoteException;

}