package it.polimi.ingsw.GC_18.server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.GameMode;

/**
 * Handler of the game finding mechanism and the communication between model on server and clients, but also between only clients.
 */
public class Room implements Observer {

    private static final Logger LOGGER = Logger.getLogger(Room.class.getName());
    private static final int MAX_SIZE = Integer.parseInt(Main.getServerProperties().getProperty("ROOMMAXSIZE"));

    private static Long gameStartingTimeout = Long.parseLong(Main.getServerProperties().getProperty("ROOMTIMEOUT"));
    private static volatile Set<Room> rooms = new HashSet<>();

    private RoomState roomState;
    private GameMode gameMode;
    private int id;
    private int currentSize;
    private Timer startTimer = new Timer();// timer for staring a game if the minimum number of players is reached but not the max size
    private Game game;
    private int numberOfVoteForSave;
    private volatile List<Client> clients = new ArrayList<>();

    /**
     * Creates a new room putting in it the specified client
     * @param client - the client to add to the new room
     */
    private Room(Client client) {
        id = hashCode();// or it could be time stamp based like:
        // System.nanoTime()
        currentSize = 0;
        roomState = RoomState.WAITING_SECOND_PLAYER;
        gameMode = client.getGameMode();
        enter(client);
        rooms.add(this);
    }

    /**
     * Tries to put a client inside this room
     * @param client - the client that enters the room
     * @return true if the client was able to enter this room, false otherwise
     */
    private synchronized boolean enter(Client client) {
        if (gameMode != client.getGameMode()) {
            return false;
        }
        if (!(roomState.equals(RoomState.WAITING_SECOND_PLAYER) || roomState.equals(RoomState.WAITING_OTHER_PLAYERS)))
            return false;
        // ensuring that no client in the room has the same name
        for (Client cli : clients) {
            if (cli.getUsername().equals(client.getUsername()))
                return false;
        }
        if (currentSize < MAX_SIZE) {// ensuring there's space for other clients
            clients.add(client);
            client.setState(ClientState.IN_ROOM);
            currentSize++;
            propagateInRoom("IN_ROOM_WITH",
                    clients.stream().map(cli -> cli.getUsername()).collect(Collectors.joining(" - ")));
            // case no client in the room
            if (currentSize == 1) {
                roomState = RoomState.WAITING_SECOND_PLAYER;
                return true;
            }
            // case only one client in the room
            if (currentSize == 2 && roomState.equals(RoomState.WAITING_SECOND_PLAYER)) {
                roomState = RoomState.WAITING_OTHER_PLAYERS;
                // Starting timer before the game starts
                startTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentSize > 1) {
                            startGame();
                        }
                    }
                }, gameStartingTimeout);
                return true;
            } else if (currentSize > 2 && roomState.equals(RoomState.WAITING_OTHER_PLAYERS)) {// case more than 1 clients in the room
                if (currentSize == MAX_SIZE) {// reached max number of players: starting the game!
                    startTimer.cancel();// canceling timer -> game starts anyway
                    startGame();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Reconnects a client to an existing room updating the references on both sides
     * @param client - the client that reconnects
     */
    synchronized void reconnect(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getUsername().equals(client.getUsername())) {
                clients.set(i, client);
                propagateInRoom(client, "USER_RECONNECTED", client.getUsername());
                client.sendGameUpdate(game);
                return;
            }
        }
    }

    /**
     * Kicks a client from this room
     * @param client - the client to kick
     */
    synchronized void exitRoom(Client client) {
        if (roomState == RoomState.IN_GAME && currentSize>1) {
            propagateInRoom(client, "USER_IS_AFK", client.getUsername());
            return;
        }
        currentSize--;
        client.setState(ClientState.LOGGED_IN);
        client.exitRoom();
        clients.remove(client);
        propagateInRoom(client, "USER_LEFT_ROOM", client.getUsername());
        if (currentSize > 1 && roomState != RoomState.IN_GAME) {
            roomState = RoomState.WAITING_OTHER_PLAYERS;
        }
        if (currentSize == 1 && roomState != RoomState.IN_GAME) {
            roomState = RoomState.WAITING_SECOND_PLAYER;
        } else if (currentSize == 0) {
            deleteRoom();
        }
    }

    /**
     * Deletes this room notifying clients and updating references
     */
    public void deleteRoom() {
        clients.forEach(cli->cli.setRoom(null));
        List<Client> tmpClients = new ArrayList<>(clients);
        clients.clear();
        tmpClients.forEach(cli -> {
            if (cli.getState() == ClientState.AFK) {
                cli.disconnect();
            } else {
                cli.setState(ClientState.LOGGED_IN);
            }
        });
        rooms.remove(this);
    }

    /**
     * Starts the game
     */
    private synchronized void startGame() {
        roomState = RoomState.IN_GAME;
        for (Client client : clients)
            client.setState(ClientState.IN_GAME);
        game = findGame();
        for (Client client : clients)
            client.sendGameUpdate(game);
        Main.getServerThreadPool().submit(game);
    }

    /**
     * Creates a new game for the clients in the room unless there's a saved instance of a game for this clients
     * @return the new game or the game loaded
     */
    private synchronized Game findGame() {
        String gameId="resources/gameSaves/model_" + clients.stream().sorted((a,b)->a.getUsername().compareTo(b.getUsername())).map(player->player.getUsername()).collect(Collectors.joining(" - "));
        if (new File(gameId).exists()) {
            return Game.loadGame(this,gameId);
        }
        return new Game(this, gameMode);
    }

    /**
     * Sends the game from the model on the server to the clients
     * The clients can modify the object, but it will not be propagated to the model on the server
     * for safety
     * @param game - the game instance to send
     */
    public void sendGame(Game game) {
        clients.stream().forEach(cli -> {if (cli.isConnected()) cli.sendGameUpdate(game);});
    }

    /**
     * Handles the voting for the saving of a game and if all the clients voted for the save
     * the game will be saved on the beginning of the next round. The game is loaded
     * as soon as this clients join in a room altogether
     * @param client - the client that voted for the save
     */
    synchronized void saveGame(Client client) {
        if (roomState!=RoomState.IN_GAME || client.hasVotedForSave()) {
            client.outputToClient("LOG - CAN'T SAVE NOW!");
            return;
        }
        numberOfVoteForSave++;
        client.setVotedForSave(true);
        propagateInRoom("LOG", "SAVING GAME VOTES:"+numberOfVoteForSave+"/"+clients.size());
        if (numberOfVoteForSave==clients.size()) {
            propagateInRoom("LOG","THE GAME WILL BE SAVES AS SOON AS A NEW ROUND STARTS!");
            game.getTurnHandler().tryToSave();
        }
    }

    /**
     * Notifies the clients that the game ended and deletes the room
     * @param winnerUsername - the user name of the player that won the game
     */
    public synchronized void gameEnded(String winnerUsername) {
        roomState = RoomState.GAME_OVER;
        propagateInRoom("GAME_ENDED", winnerUsername);
        deleteRoom();
    }

    /**
     * Handles the input from the clients
     * @param client - the client that invoked this method
     * @param command - the command to be handled
     * @param description - the description to be handled
     */
    synchronized void handleIn(Client client, String command, String description) {
        if ("CHAT".equalsIgnoreCase(command)) {
            propagateInRoom(client, command, client.getUsername() + ": " + description);
        } else if (roomState == RoomState.IN_GAME && game != null && ("CHOOSE".equalsIgnoreCase(command) || "PLACE".equalsIgnoreCase(command) || "LEADER".equalsIgnoreCase(command) ||
                "BOARD_STATUS".equalsIgnoreCase(command) || "PLAYERS_STATUS".equalsIgnoreCase(command))) {
            try {
                game.getGameController().handleInput(client.getUsername(), command, description);
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "Illegal argument in client request", e);
            }
        }
    }

    /**
     * Propagates to the other clients in the room the message received from the sender
     * @param sender - the Client that sends the message to the room
     * @param command - the command to be propagated to the other clients in the room
     * @param description - the message to be propagated to the other clients in the room
     */
    private synchronized void propagateInRoom(Client sender, String command, String description) {
        clients.stream().filter(cli -> !cli.equals(sender)).forEach(cli -> cli.outputToClient(command + " - " + description));
    }

    /**
     * Propagates to all the clients in the room a command and a description
     * @param command - the command to be propagated to the other clients in the room
     * @param description - the description to be propagated to the other clients in the room
     */
    public synchronized void propagateInRoom(String command, String description) {
        clients.stream().forEach(cli -> cli.outputToClient(command + " - " + description));
    }

    /**
     * Tries to find a room that is available for specified client. If no room is found a new one is created
     * @param client - the client that is seeking a room
     * @return the room found
     */
    static Room findRoom(Client client) {
        synchronized (rooms) {
            Iterator<Room> it = rooms.iterator();
            while (it.hasNext()) {
                Room r = it.next();
                if (r.enter(client)) {
                    return r;
                }
            }
            // If we reach here it means that there are no rooms available->create a new one
            return new Room(client);
        }
    }

    @Override
    public synchronized void update(Observable observable, Object notifyObject) {
        Client client = (Client) observable;
        if (ClientState.DISCONNECTED.equals(client.getState())) {
            exitRoom(client);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    /**
     * @return the room state
     */
    RoomState getRoomState() {
        return roomState;
    }

    /**
     * @return the list of the clients in this room
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * @return the game associated to this room
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the timeout for staring a game if the minimum number of players is reached but not the max size
     * @param gameStartingTimeout - the time out to set in milliseconds
     */
    public static void setGameStartingTimeout(Long gameStartingTimeout) {
        Room.gameStartingTimeout = gameStartingTimeout;
    }

}
