package it.polimi.ingsw.GC_18.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.GameMode;
import it.polimi.ingsw.GC_18.server.rmi.ClientRmi;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * This abstract class represents a player and exposes some useful methods to handle
 * the interaction between the client and the server
 * The implementations are ClientSocket and ClientRmi
 */
public abstract class Client extends Observable {

    private static final String CLIENTS_LIST_PATH = "resources/clients/registeredClients.txt";

    private static volatile Map<String, Client> clients = new HashMap<>();

    private Credentials credentials;
    private Room room;
    private GameMode gameMode = GameMode.ADVANCED;
    private ClientState state;
    private boolean isConnected = false;
    private boolean votedForSave = false;

    /**
     * Instantiates this class setting the state of the client to connected
     */
    protected Client() {
        setState(ClientState.CONNECTED);
    }

    /**
     * Closes gracefully the connection
     */
    protected abstract void closeConnection();

    /**
     * Outputs the message to the client
     * @param output - the message to send
     */
    public abstract void outputToClient(String output);

    /**
     * Outputs the game to the client
     * @param game - the game instance to send to the client
     */
    public abstract void sendGameUpdate(Game game);

    /**
     * Handles the commands coming from the client side
     * @param input - the input from the client to be parsed
     */
    public void handleInput(String input) {
        System.out.println("Received input from client " + input);
        String[] inputParts = input.split(" - ", 2);
        String command = inputParts[0];
        String description = inputParts.length == 2 ? inputParts[1] : "";
        if ("LOGIN".equalsIgnoreCase(command)) {
            login(description);
            Main.getCommandLoggers().forEach(cl -> cl.registerOnLog(credentials,this instanceof ClientRmi));
        } else if ("SHOW_STATS".equalsIgnoreCase(command)) {
            outputToClient("STATS - "+Utils.loadFileAsString(UserStats.STATS_PATH));
        } else if ("FIND_GAME".equalsIgnoreCase(command)) {
            setGameMode(description);
            enterRoom();
        } else if ("CHAT".equalsIgnoreCase(command) || "CHOOSE".equalsIgnoreCase(command) || "PLACE".equalsIgnoreCase(command) || "LEADER".equalsIgnoreCase(command) ||
                "BOARD_STATUS".equalsIgnoreCase(command) || "PLAYERS_STATUS".equalsIgnoreCase(command)) {
            room.handleIn(this, command, description);
        } else if ("SAVE".equals(command)) {
            room.saveGame(this);
        } else if ("DISCONNECTING".equalsIgnoreCase(command)) {
            disconnect();
        } else if ("PASS".equalsIgnoreCase(command) && room.getRoomState() == RoomState.IN_GAME) {
            pass();
        } else if ("EXIT_ROOM".equalsIgnoreCase(command) && (room != null)) {
            room.exitRoom(this);
        }
        Main.getCommandLoggers().forEach(cl -> cl.logCommand(credentials,input));
    }

    /**
     * Handles the login of a player, notifying it if whether it is successful or not
     * @param description - the credentials
     */
    protected void login(String description) {
        String[] descriptionParts = description.split(" - ");
        if (descriptionParts.length != 2)
            return;
        credentials = new Credentials(descriptionParts[0], descriptionParts[1]);
        // checking if credentials are valid and don't collide with other users
        if (!(registerClient(getUsername(), getPassword()))) {
            outputToClient("INVALID_LOGIN - " + description);
            return;
        }
        isConnected = true;
    }

    /**
     * Tries to register a client on server
     * @param username - the player user name
     * @param password - the player password
     * @return true if succeeds to login the client, false otherwise
     */
    private boolean registerClient(String username, String password) {
        if (clients.containsKey(username)) {// client is already registered
            return reconnect(username, password);
        }
        // clients is new
        if (!checkCredentials(username, password))
            return false;
        clients.put(username, this);
        outputToClient("LOGGED_IN");
        return true;
    }

    /**
     * Checks if credentials are OK for the login
     * @param username - the player's user name
     * @param password - the player's password
     * @return true if the check succeeds, false otherwise
     */
    private boolean checkCredentials(String username, String password) {
        String[] clientsCredentials = Utils.loadFileAsString(CLIENTS_LIST_PATH).split("\n");
        for (String credentialsLogged : clientsCredentials) {
            String[] credentialsParts = credentialsLogged.split(" - ");
            if (credentialsParts[0].equals(username)) {
                return Utils.hash(password).equals(credentialsParts[1]);
            }
        }
        // client is not registered yet, logging its credentials
        Utils.appendToFile(CLIENTS_LIST_PATH, username + " - " + Utils.hash(password));
        return true;
    }

    /**
     * Tries to reconnect a client
     * @param username - the player's user name
     * @param password - the player's password
     * @return true if the player can reconnect, false otherwise
     */
    private boolean reconnect(String username, String password) {
        //PRE: clients.containsKey(userName) is true
        if (clients.get(username).isConnected) { // the client with this name is already connected, can't reconnect
            return false;
        }
        // checking if client has reconnected to a running game
        if (clients.get(username).getPassword().equals(password) && clients.get(username).room != null
                && clients.get(username).room.getRoomState() == RoomState.IN_GAME) {
            // retrieving data needed from previous instance of this client
            Client client = clients.get(username);
            credentials = client.getCredentials();
            gameMode = client.getGameMode();
            setRoom(client.getRoom());
            setState(ClientState.IN_GAME);
            for (int i = 0; i < room.getClients().size(); i++) {
                if (room.getClients().get(i).getUsername().equals(username)) {
                    room.getClients().set(i, this);
                    break;
                }
            }
            outputToClient("RECONNECTED - "+ room.getClients().stream().map(cli -> cli.getUsername()).collect(Collectors.joining(" - ")));
            outputToClient("TURN_OF - "+ room.getGame().getTurnHandler().getTurnOf());

            for (Client cli : room.getClients()) {
                if (cli.getState() == ClientState.AFK) {
                    outputToClient("USER_IS_AFK - " + cli.getUsername());
                }
            }
            room.reconnect(this);
            clients.put(username, this);
            return true;
        }
        return false;
    }

    /**
     * Tells the turn handler to pass if it's this user turn
     */
    private void pass() {
        if (!room.getGame().getGameController().isOccupied() && room.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(getUsername()) && p.isYourTurn())) {
            room.getGame().getTurnHandler().changeTurn();
        } else {
            outputToClient("NOTIFY - YOU CANNOT PASS");
        }
    }

    /**
     * Sets the game mode based on the parameter
     * @param mode - the game mode to string
     */
    private void setGameMode(String mode) {
        if ("ADVANCED".equals(mode)) {
            gameMode = GameMode.ADVANCED;
        }
    }

    /**
     * Finds a room for the client
     */
    private void enterRoom() {
        if (clients.get(getUsername()).getRoom() != null) {
            return;
        }
        room = Room.findRoom(this);
        setState(ClientState.IN_ROOM);
        setRoom(room);
    }

    /**
     * Exits from the room
     */
    void exitRoom() {
        deleteObservers();
        setState(ClientState.LOGGED_IN);
        room = null;
        outputToClient("ROOM_DESTROYED");
    }

    /**
     * Closes connection with client and unregisters it
     */
    protected void disconnect() {
        isConnected = false;
        if ((room == null) || (room.getRoomState() != RoomState.IN_GAME)) {
            clients.remove(getUsername());
        } else {// just go AFK, client can reconnect to game later
            setState(ClientState.AFK);
        }
        if (room != null)
            room.exitRoom(this);
        deleteObservers();
        closeConnection();
    }

    /**
     * @return the Map containing all clients
     */
    public static Map<String, Client> getClients() {
        return clients;
    }

    /**
     * @return the player user name
     */
    public String getUsername() {
        return credentials.getUsername();
    }

    /**
     * @return the player password
     */
    private String getPassword() {
        return credentials.getPassword();
    }

    /**
     * @return this client credentials (user name, password)
     */
    private Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets this client credentials
     * @param username - client's user name
     * @param password - client's password
     */
    protected void setCredentials(String username, String password) {
        if (credentials == null) {
            credentials = new Credentials(username, password);
            return;
        }
        credentials.setUsername(username);
        credentials.setPassword(password);
    }

    /**
     * @return the room in which this client is in
     */
    private Room getRoom() {
        return room;
    }

    /**
     * Sets the room in which this client is in
     * @param room - the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
        if (room != null)
            addObserver(room);
    }

    /**
     * @return the state of this client instance
     */
    public ClientState getState() {
        return state;
    }

    /**
     * Sets this client state and notfies observers
     * @param state - the state to set
     */
    public void setState(ClientState state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }

    /**
     * @return this client game mode
     */
    GameMode getGameMode() {
        return gameMode;
    }

    /**
     * @return true if this client has already voted for saving the game
     */
    boolean hasVotedForSave() {
        return votedForSave;
    }

    /**
     * Sets the saving vote of this client
     * @param votedForSave - the boolean that indicates if the client has already vote for save
     */
    void setVotedForSave(boolean votedForSave) {
        this.votedForSave = votedForSave;
    }

    /**
     * @return true if this client is not AFK
     */
    boolean isConnected() {
        return isConnected;
    }

}
