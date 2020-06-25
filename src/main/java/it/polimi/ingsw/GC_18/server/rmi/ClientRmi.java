package it.polimi.ingsw.GC_18.server.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.connection.RmiHandlerInterface;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.server.Client;

/**
 * Handles the communication with a client using RMI
 */
public class ClientRmi extends Client {

    private static final Logger LOGGER = Logger.getLogger(ClientRmi.class.getName());

    /**
     * Instantiates this class setting the client's user name and its password
     * @param username - the client's user name
     * @param password - the client's password
     */
    ClientRmi(String username, String password) {
        super();
        setCredentials(username, password);
        login(username + " - " + password);
    }

    @Override
    public synchronized void closeConnection() {
        RmiServer.disconnectFromServer(getUsername());
    }

    @Override
    public void outputToClient(String output) {
        try {
            RmiHandlerInterface clientTmp = RmiServer.getClientRmiHandlers().get(getUsername());
            if (clientTmp == null)
                return;
            clientTmp.receiveFromServer(output);
        } catch (RemoteException e) {
            disconnect();
            LOGGER.log(Level.WARNING, "ERROR WITH RMI CONNECTION, CLOSING", e);
        }
    }

    @Override
    public void sendGameUpdate(Game game) {
        try {
            RmiHandlerInterface clientTmp = RmiServer.getClientRmiHandlers().get(getUsername());
            if (clientTmp == null)
                return;
            clientTmp.receiveFromServer(game);
        } catch (RemoteException e) {
            disconnect();
            LOGGER.log(Level.WARNING, "ERROR WITH RMI CONNECTION, CLOSING", e);
        }
    }

}
