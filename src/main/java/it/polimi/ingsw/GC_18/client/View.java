package it.polimi.ingsw.GC_18.client;

import javax.swing.JPanel;

import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;
import it.polimi.ingsw.GC_18.client.gui.*;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * Represents the view of the MVC of the client side. 
 * Interacts directly with user receiving input and showing stuff.
 * Handles user inputs, implements some client side logic, 
 * notifies the controller of commands and shows model changes.
 */
public final class View {

    private static MainFrame mainFrame;
    static GameGui gameGui;

    /**
     * Hiding  constructor, since this class doen't have to be instantiated.
     */
    private View() {
    }

    /**
     * Starts the view setting the JFrame and showing the login screen
     */
    static void startView() {
        System.out.println("WELCOME TO LORENZO IL MAGNIFICO!");
        System.out.println("In every moment you can type:"
                + "\nHELP -> to get information about the commands you can send"
                + "\nSET_USER_INTERFACE -> to toggle the gui/cli"
                + "\nEXIT -> to close the application"
                + "\nIf you are in Room or in game you can type"
                + "\nCHAT - message -> for sending messages to the other users"
                + "\nEvery commands for using the cli is case insensitive");
        mainFrame = new MainFrame("LORENZO IL MAGNIFICO");
        changeMainFrameContent(new Login());
    }

    /**
     * Displays the menu
     */
    public static void enterMenu() {
        if (Controller.getState() == State.LOGGING_IN || Controller.getState() == State.GAME_ENDED || Controller.getState() == State.VIEWING_OPTIONS) {
            View.changeMainFrameContent(new Menu(User.getUsername()));
        }
    }

    /**
     * Enters the room
     * @param reconnecting - true if the user is reconnecting to an existing game
     */
    public static void enterRoom(boolean reconnecting) {
        if (Controller.getState()!=State.IN_MENU) {
            MessageNotifier.notifyUser("You can only join a room from the menu state");
            return;
        }
        Controller.setState(State.IN_ROOM);
        changeMainFrameContent(new Room());
        getMainFrame().setExtendedState(MainFrame.MAXIMIZED_BOTH);
        if (!reconnecting)
            ConnectionHandler.outputToServer("FIND_GAME - " + User.getGameMode());
    }

    /**
     * Shows the game GUI
     * @param game - the game instance sent from server to this client
     */
    static void showGame(Game game) {
        Controller.setState(State.IN_GAME);
        //Waiting for loading of game assets
        while(!AssetsLoader.isDoneLoading()) {
            //Waiting for game assets to be loaded
        }
        gameGui = new GameGui(game);
        changeMainFrameContent(gameGui);
        getMainFrame().setExtendedState(MainFrame.MAXIMIZED_BOTH);
    }

    /**
     * Shows some game statistics asking them to server
     */
    public static void showStats() {
        // the GUI is updated from the controller when it receives the server's response to this command

        if (Controller.getState() != State.IN_MENU) {
            MessageNotifier.notifyUser("You can't view stats if you are not in the menu");
            return;
        }
        ConnectionHandler.outputToServer("SHOW_STATS");
    }

    /**
     * Changes the main frame content with a new JPanel
     * @param content - the new content to be displayed in the main frame's content pane
     */
    public static void changeMainFrameContent(JPanel content) {
        View.getMainFrame().getContentPane().removeAll();
        View.getMainFrame().getContentPane().add(content);
        View.getMainFrame().pack();
        View.getMainFrame().setLocationRelativeTo(null);// center on the screen
    }

    /**
     * @return the main frame
     */
    public static MainFrame getMainFrame() {
        return mainFrame;
    }

}
