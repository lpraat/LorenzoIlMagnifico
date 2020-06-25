package it.polimi.ingsw.GC_18.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;
import it.polimi.ingsw.GC_18.client.gui.AudioHandler;
import it.polimi.ingsw.GC_18.client.gui.GameEndedFrame;
import it.polimi.ingsw.GC_18.client.gui.InteractionFrame;
import it.polimi.ingsw.GC_18.client.gui.Login;
import it.polimi.ingsw.GC_18.client.gui.Menu;
import it.polimi.ingsw.GC_18.client.gui.MessageNotifier;
import it.polimi.ingsw.GC_18.client.gui.Room;
import it.polimi.ingsw.GC_18.client.gui.StatsPane;
import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.utils.EasterEggs;

/**
 * Represents the controller of the MVC of the client side. 
 * Parses user commands, implements some client side logic, 
 * notifies the model of commands and the view of model changes.
 */
public final class Controller extends Observable {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static State state = State.LOGGING_IN;
    private static boolean gameInstantiated = false;

    /**
     * Hiding constructor, since this class doesn't have to be instantiated
     */
    private Controller() {
    }

    /**
     * Checks the login credentials, creates the User instance, starts the connection to the server and sends to server the login command
     * @param username the username
     * @param password the password
     * @param rmiFlag - true if user choose to use RMI connection, false for socket
     * @param guiFlag - true if user choose to use the GUI, false for console
     */
    public static void login(String username, String password, boolean rmiFlag, boolean guiFlag) {
        // checking credentials
        if (checkLoginCredentials(username, password)) {
            User.createUser(username, password, guiFlag);
            View.getMainFrame().setVisible(guiFlag);
            try {
                ConnectionHandler.startConnectionHandler(rmiFlag);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "UNABLE TO CONNECT TO SERVER", e);
                MessageNotifier.notifyUser("UNABLE TO CONNECT TO SERVER, PLEASE CHECK YOUR CONNECTION!");
                return;
            }
            if (!rmiFlag)
                ConnectionHandler.outputToServer("LOGIN - " + User.getUsername() + " - " + User.getPassword());
        }
    }

    /**
     * Checks if the login credentials are OK
     * @param username - must be alphanumeric
     * @param password - must be alphanumeric
     * @return true if the credentials are OK, false otherwise
     */
    private static boolean checkLoginCredentials(String username, String password) {
        User.setUsingGuiInterface(true);
        if (username.matches("[a-zA-Z0-9]*") && !"".equals(username) && password.matches("[a-zA-Z0-9]*") && !("".equals(password))) {
            if (EasterEggs.containsAdjacentCarlosAnagram(username)
                    || EasterEggs.containsAdjacentCarlosAnagram(password)) {
                MessageNotifier.notifyUser("INVALID USERNAME OR PASSWORD! THEY CANT CONTAIN ANAGRAMS OF CARLOS: WE HATE FEEDERS!");
                return false;
            }
            return true;
        } else {
            MessageNotifier.notifyUser("INVALID USERNAME OR PASSWORD! THEY CAN ONLY BE ALPHANUMERIC NOT NULL STRINGS!");
            return false;
        }
    }

    /**
     * Handles case server refused login credentials
     */
    private static void invalidLogin() {
        MessageNotifier.notifyUser("INVALID LOGIN CREDENTIALS");
        View.changeMainFrameContent(new Login());
    }

    /**
     * Notifies the room GUI that a user joined the room, if the state is valid
     * @param username - the user name of the user that entered the room
     */
    private static void userEnteredRoom(String username) {
        if (!(state == State.IN_GAME || state == State.IN_ROOM) || "".equals(username))
            return;
        User.getRoom().joined(username);
    }

    /**
     * Notifies the room GUI that a user left the room, if the state is valid
     * @param username - the user name of the user that left the room
     */
    private static void userLeftRoom(String username) {
        if (!(state == State.IN_GAME || state == State.IN_ROOM))
            return;
        User.getRoom().left(username);
    }

    /**
     * Notifies the room GUI that a user went AFK in the room, if the state is valid
     * @param username - the user name of the user that went AFK
     */
    private static void userWentAfk(String username) {
        if (!(state == State.IN_GAME || state == State.IN_ROOM))
            return;
        User.getRoom().afk(username);
    }

    /**
     * Notifies the room GUI that a user reconnected to the game, if the state is valid
     * @param username - the user name of the user that reconnected
     */
    private static void userReconnected(String username) {
        if (!(state == State.IN_GAME || state == State.IN_ROOM))
            return;
        User.getRoom().reconnected(username);
    }

    /**
     * Handles the case when the client reconnects to a existing game
     * @param description - a string that represents the list of user names of users that are in the same room
     */
    private static void reconnected(String description) {
        View.enterMenu();
        View.enterRoom(true);
        Arrays.asList(description.split(" - ")).forEach(Controller::userEnteredRoom);
        gameInstantiated=false;
        User.getChat().receiveMessage("Wait while the application reloads the game assets, this could take long if you are using RMI", true);
    }

    /**
     * Handles the case when the game is finished and the room server side is destroyed, notifies the GUI
     */
    private static void roomDestroyed() {
        if (!(state == State.IN_GAME || state == State.IN_ROOM))
            return;
        User.getRoom().roomDestroyed();
        User.setRoom(null);
    }

    /**
     * Handles model update and start the GUI of the game if needed.
     * @param game the game.
     */
    public static void handleModelUpdate(Game game) {
        if (!gameInstantiated) {
            gameInstantiated = true;
            View.showGame(game);
            View.gameGui.getDispatcher().updateGui(game);
            for (Player player : game.getPlayers()) {
                if (player.getUsername().equals(User.getUsername()) && player.getController().getWaitingCommand()!= null) {
                    handleInputFromServer("NOTIFY - "+Blocking.switchDescription(player, player.getController().getWaitingCommand()));
                }
            }
        } else {
            View.gameGui.getDispatcher().updateGui(game);
        }
    }

    /**
     * Notifies the server that the user voted for saving the game
     */
    public static void save() {
        if (state != State.IN_GAME) {
            MessageNotifier.notifyUser("CAN'T SUSPEND NOW THE GAME!");
        } else {
            ConnectionHandler.outputToServer("SAVE");
        }
    }

    /**
     * Handles the case when the user choose to pass his turn before the timeout if it is possible
     * and notifies the GUI in the chat of the action
     */
    public static void pass() {
        if (User.isYourTurn()) {
            ConnectionHandler.outputToServer("PASS");
        } else {
            User.getChat().receiveMessage("YOU CANNOT PASS", true);
        }
    }

    /**
     * Handles the turn changed input from server, notifies the GUI
     * @param command the command from the server
     * @param description the description of the command
     */
    private static void handleTurn(String command, String description) {
        User.getChat().receiveMessage("LOG: " + command + " " + description, true);
        if (description.equals(User.getUsername())) {
            User.setYourTurn(true);
        } else {
            User.setYourTurn(false);
        }
    }

    /**
     * Handles the case of game end and notifies the GUI
     * @param winnerUsername the username of the winner.
     */
    private static void gameEnded(String winnerUsername) {
        gameInstantiated = false;
        setState(State.GAME_ENDED);
        new GameEndedFrame(winnerUsername);
    }

    /**
     * Notifies the GUI of a chat event
     * @param message - the message to send in chat
     */
    private static void chat(String message) {
        if (state == State.IN_ROOM || state == State.IN_GAME)
            User.getChat().receiveMessage(message, false);
    }

    /**
     * Called when the server has successfully saved the game
     * The User is sent to the menu screen
     * @param description - information message of this action to display to the user
     */
    private static void gameSaved(String description) {
        MessageNotifier.notifyUser(description);
        Controller.setState(State.GAME_ENDED);
        gameInstantiated = false;
        View.enterMenu();
    }

    /**
     * Handles the notify command sent from server to this client and parses it
     * calling the right functions
     * @param description - other arguments for this command to be parsed
     */
    private static void handleNotify(String description) {
        if (description.toUpperCase().startsWith("CHOOSE - ")) {
            InteractionFrame.handleChoice(description.split(" - ", 2)[1]);
        } else if (description.toUpperCase().startsWith("CLOSE_FRAMES")) {
            InteractionFrame.closeFrames();
        } else if (description.toUpperCase().startsWith("GAME SAVED")) {
            gameSaved(description);
        } else {
            if (!"NULL".equalsIgnoreCase(description))
                User.getChat().receiveMessage(description, true);
        }
    }

    /**
     * Closes all connections with server.
     */
    private static void disconnect() {
        if (state != State.LOGGING_IN)
            ConnectionHandler.disconnect();
    }

    /**
     * Handles the closing of the application
     */
    public static void exit() {
        disconnect();
        View.getMainFrame().dispose();
        // Calling System.exit since this function is called to close the application
        System.exit(0);// NOSONAR
    }

    /**
     * Prompts to user the action that he can do based on the state of the application
     * @param description the command description
     */
    public static void help(String description) {
        switch (state) {
        case LOGGING_IN:
            MessageNotifier.notifyUser(Login.getActionsThatCanBeDone());
            break;
        case IN_GAME:
            handleGameHelp(description);
            break;
        case IN_MENU:
            MessageNotifier.notifyUser(Menu.getActionsThatCanBeDone());
            break;
        case IN_ROOM:
            MessageNotifier.notifyUser(Room.getActionsThatCanBeDone());
            break;
        case GAME_ENDED:
            MessageNotifier.notifyUser("MENU - EXIT");
            break;
        default:
            break;
        }
    }

    /**
     * Handles the help when the user is in game prompting an help message
     * based on the description
     * @param description - describes the command which the user asked help on
     */
    private static void handleGameHelp(String description) {
        if ("CHOOSE".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("USE THIS COMMAND WHEN PROMPTED TO MAKE A CHOICE AND FOLLOW THE INSTRUCTIONS");
        } else if ("PLACE".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("PLACE - actionPlace - pawnColor - servants -> to place the pawn of color pawnColor in place actionPlace spending servants servants"
                    +"\nactionPlace can be:"
                    +"\nCHARACTERi -> with i in range [1,4], to place in character tower"
                    +"\nBUILDINGi -> with i in range [1,4], to place in building tower"
                    +"\nTERRITORYi -> with in range [1,4], to palce in territory tower"
                    +"\nVENTUREi -> with i in range [1,4], to place in venture tower"
                    +"\nCOIN_SPOT"
                    +"\nSERVANTS_SPOT"
                    +"\nMILITARY_SPOT"
                    +"\nCOUNCIL_SPOT"
                    +"\nHARVEST_AREA"
                    +"\nLARGEHARVEST_AREA"
                    +"\nPRODUCTION_AREA"
                    +"\nFIGHT_SPOT");
        } else if ("LEADER".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("LEADER - SHOW_HAND -> to show leader cards in the hand"
                    +"\nLEADER - SHOW_PLAYED -> to show leader cards played"
                    +"\nLEADER - SHOW_ONCE -> to show the once per turn effects"
                    +"\nLEADER - number denoting leader card - ACTIVATE -> to activate a leader card"
                    +"\nLEADER - int denoting the once effect - ACTIVATE_ONCE -> to activate a once per turn effect"
                    +"\nLEADER - int denoting the leader card - DISCARD -> to discard a leader card");
        } else if ("SAVE".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("SAVE -> to send the save request to the server"
                    + "\nThe game will be saved as soon as a new round starts after that all the players have voted for the saving"
                    + "\nThe game is then reloaded when the players reconnect in the same room and in the same number");
        } else if ("PASS".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("PASS -> use this command when is your turn to pass");
        } else if ("FIGHT".equalsIgnoreCase(description)) {
            MessageNotifier.notifyUser("You can place any number of pawns you want per round in the fight spot."
                    + "\nEach round there's a fight that has a prize in resources"
                    + "\nYou can win a share of the prize if you place at least one pawn in the fight spot that round."
                    +"\nThe share depends on the percentage of battle points you have accumulated");
        } else {
            MessageNotifier.notifyUser("TO GET INFO ABOUT THE GAME STATE TYPE PLAYERS_STATUS OR BOARD_STATUS\n"
                    + (     "YOU CAN CHOOSE TO DO ONE OF THE FOLLOWING ACTIONS:"
                            + "\nCHOOSE - choice"
                            + "\nPLACE - placeCommand"
                            + "\nLEADER - action"
                            + "\nSAVE"
                            + "\nPASS"
                            + "\nFIGHT"
                            + "\nFOR FURTHER INFORMATION TYPE \"HELP - COMMAND NAME\" (e.g.: HELP - CHOOSE)"
                            ));
        }
    }

    /**
     * Parses the server input and decides what action to take based on the parameter
     * @param input - the input received from server. 
     * NOTE: the input is composed of multiple parts separated by the string " - "
     */
    public static void handleInputFromServer(String input) {
        // parsing input from the server!
        String[] inputParts = input.split(" - ", 2);
        String command = inputParts[0].toUpperCase();
        String description = inputParts.length == 2 ? inputParts[1] : "";
        if ("LOGGED_IN".equals(command)) {
            View.enterMenu();
        } else if ("INVALID_LOGIN".equals(command)) {
            invalidLogin();
        } else if ("RECONNECTED".equals(command)) {
            reconnected(description);
        } else if ("IN_ROOM_WITH".equals(command)) {
            Arrays.asList(description.split(" - ")).forEach(Controller::userEnteredRoom);
        } else if ("CHAT".equals(command)) {
            chat(description);
        } else if ("USER_LEFT_ROOM".equals(command)) {
            userLeftRoom(description);
        } else if ("USER_IS_AFK".equals(command)) {
            userWentAfk(description);
        } else if ("USER_RECONNECTED".equals(command)) {
            userReconnected(description);
        } else if ("GAME_ENDED".equals(command)) {
            gameEnded(description);
        } else if ("LOG".equals(command)) {
            User.getChat().receiveMessage(description, true);
        } else if ("ROOM_DESTROYED".equals(command)) {
            roomDestroyed();
        } else if ("TURN_OF".equals(command)) {
            InteractionFrame.closeFrames();
            handleTurn(command, description);
        } else if ("NOTIFY".equalsIgnoreCase(command)) {
            handleNotify(description);
        } else if ("STATS".equalsIgnoreCase(command)) {
            StatsPane.updateAndShowStats(description);
        } else {
            MessageNotifier.notifyUser(input);
        }
    }

    /**
     * @return the state of the client application
     */
    public static State getState() {
        return state;
    }

    /**
     * Sets the application state
     * @param state the state to be set
     */
    public static void setState(State state) {
        Controller.state = state;
        AudioHandler.updateAudio(state);
    }

}
