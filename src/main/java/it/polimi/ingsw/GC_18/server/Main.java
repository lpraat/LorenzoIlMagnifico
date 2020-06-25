package it.polimi.ingsw.GC_18.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.server.rmi.RmiServer;
import it.polimi.ingsw.GC_18.server.rmi.RmiServerInterface;
import it.polimi.ingsw.GC_18.server.socket.SocketServer;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Starter of the server
 */
public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String GAMECONFIGPATH = "resources/properties/gameConfig.properties";
    private static final String SERVERCONFIGPATH = "resources/properties/serverConfig.properties";
    private static final String COMMANDS_LOG_PATH = "resources/test/commands_log";

    private static ExecutorService serverThreadPool;
    private static SocketServer socketServer;
    private static RmiServerInterface rmiServer;
    private static Properties serverProperties;
    private static List<CommandLogger> commandLoggers = new ArrayList<>();// Note: the main command logger is at index 0

    /**
     * Hidden constructor.
     */
    private Main() {
    }// hiding constructor, since this class doesn't have to be instantiated

    /**
     * Main entrance of the server program.
     * Starts a thread pool that handles:
     * connections with clients
     * games updates
     * rooms for finding game
     * generation and update of game statistics
     * @param args - unused
     */
    public static void main(String[] args) {
        System.out.println("Server started");
        // loading & setting configurable server parameters
        serverProperties = Utils.loadProperties(SERVERCONFIGPATH);
        // starting server thread pool handler
        serverThreadPool = Executors.newCachedThreadPool();
        // starting RMI server Thread
        serverThreadPool.submit(() -> {
            try { // special exception handler for registry creation
                LocateRegistry.createRegistry(Integer.parseInt(serverProperties.getProperty("RMISERVERPORT")));
                rmiServer = new RmiServer();
                Naming.rebind(serverProperties.getProperty("RMISERVERBINDING"), rmiServer);
            } catch (RemoteException e) {// error means registry already exists
                LOGGER.log(Level.WARNING, "ERROR WITH BINDING - REGISTRY ALREADY EXISTS", e);
            } catch (MalformedURLException e) {
                LOGGER.log(Level.SEVERE, "ERROR WITH RMI ADDRESS OF CONNECTION", e);
            }
        });
        // starting socket server Thread
        serverThreadPool.submit(() -> {
            try {
                socketServer = new SocketServer(Integer.parseInt(serverProperties.getProperty("SOCKETSERVERPORT0")),
                        Integer.parseInt(serverProperties.getProperty("SOCKETSERVERPORT1")));
                socketServer.acceptClients();
            } catch (IOException ioe) {
                LOGGER.log(Level.SEVERE, "ERROR CREATING SERVER SOCKET", ioe);
            }
        });
        // setting game properties
        Game.setGameProperties(Utils.loadProperties(GAMECONFIGPATH));
        // initializing main command logger
        commandLoggers.add(0, new CommandLogger(COMMANDS_LOG_PATH));
        // initializing statistics
        UserStats.loadStats();
    }

    /**
     * @return the server properties
     */
    static Properties getServerProperties() {
        return serverProperties;
    }

    /**
     * @return the server thread pool
     */
    public static ExecutorService getServerThreadPool() {
        return serverThreadPool;
    }

    /**
     * @return the commands loggers list
     */
    public static List<CommandLogger> getCommandLoggers() {
        return commandLoggers;
    }

}
