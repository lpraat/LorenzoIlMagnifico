package it.polimi.ingsw.GC_18.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.polimi.ingsw.GC_18.server.Main;

/**
 * Handler of clients connections through sockets
 */
public class SocketServer {

    private ServerSocket serverSocket0;
    private ServerSocket serverSocket1;

    /**
     * Starts two server sockets for I/O with clients
     * @param port0 - port on which clients connect for string IO
     * @param port1 - port on which clients connect for Game object IO
     * @throws IOException - for socket problems
     */
    public SocketServer(int port0, int port1) throws IOException {
        serverSocket0 = new ServerSocket(port0);
        serverSocket1 = new ServerSocket(port1);
    }

    /**
     * Accepts client sockets connections
     * @throws IOException
     */
    public void acceptClients() throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket0 = serverSocket0.accept();
                Socket clientSocket1 = serverSocket1.accept();
                ClientSocket client = new ClientSocket(clientSocket0, clientSocket1);
                Main.getServerThreadPool().submit(client);
            }
        } finally {
            Main.getServerThreadPool().shutdown();
        }
    }

}
