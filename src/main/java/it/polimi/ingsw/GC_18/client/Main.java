package it.polimi.ingsw.GC_18.client;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.GC_18.client.gui.Terminal;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Main class for client side. Contains starting point of client application and
 * the client thread pool.
 */
public final class Main {

    private static final String CLIENTCONFIGPATH = "resources/properties/clientConfig.properties";
    private static final String IMAGES_PATH = "images/";
    private static Properties clientProperties;
    private static ExecutorService mainThreadPool;

    /**
     * hiding constructor, since this class doen't have to be instantiated
     */
    private Main() {
    }

    /**
     * Starts the thread pool, loads the configurations and the images from
     * file, starts the GUI.
     * @param args - unused
     */
    public static void main(String[] args) {
        // loading configuration
        clientProperties = Utils.loadProperties(CLIENTCONFIGPATH);
        // starting thread pool
        mainThreadPool = Executors.newCachedThreadPool();
        // starting GUI for login where user can set his credentials
        View.startView();
        // starting terminal for getting user input commands
        mainThreadPool.execute(new Terminal());
        // loading GUI components
        mainThreadPool.execute(new AssetsLoader(IMAGES_PATH));
    }

    /**
     * @return the properties of the client
     */
    public static Properties getClientProperties() {
        return clientProperties;
    }

    /**
     * @return the cached thread pool of client side that contains all threads
     *         started from client application
     */
    public static ExecutorService getMainThreadPool() {
        return mainThreadPool;
    }

}
