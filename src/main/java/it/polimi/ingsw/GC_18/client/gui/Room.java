package it.polimi.ingsw.GC_18.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.Main;
import it.polimi.ingsw.GC_18.client.State;
import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.client.View;

/**
 * This class displays a screen that is showed where the player is seeking for a game.
 * In the screen are displayed the user names of the players in the same room and a chat
 * for messaging between players
 */
public class Room extends JPanel {

    private static final long serialVersionUID = 8849358036626481955L;
    private static final String ACTIONS_THAT_CAN_BE_DONE = "CHAT - EXIT";
    private static final String PLACEHOLDER = " ";

    private ArrayList<JLabel> clients;
    private JPanel topPanel;

    /**
     * Creates a screen that displays the user names of the players in the same room and a chat
     * for messaging between players
     */
    public Room() {
        System.out.println("YOU ENTERED ROOM");
        Controller.setState(State.IN_ROOM);
        int maxSize = Integer.parseInt(Main.getClientProperties().getProperty("ROOMMAXSIZE"));
        clients = new ArrayList<>(maxSize);
        setLayout(new GridLayout());
        // setting the top panel that will hold the game
        topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createTitledBorder("Room details"));
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JLabel welcomeLabel = new JLabel("Welcome to the wait room!\nPlayers in room:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        topPanel.add(welcomeLabel, gridBagConstraints);
        // setting bottom panel for holding the chat
        for (int i = 0; i < maxSize; i++) {
            JLabel playerLabel = new JLabel();// for place holding!
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i + 1;
            playerLabel.setVisible(true);
            playerLabel.setFocusable(false);
            playerLabel.setText(PLACEHOLDER);
            topPanel.add(playerLabel, gridBagConstraints);
            clients.add(playerLabel);
        }
        User.setChat(new Chat());
        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, User.getChat()));
        // setting room to player
        User.setRoom(this);
    }

    /**
     * Finds the label that contains the user name
     * @param username - the user name to seek
     * @return the searched label if found, other wise null
     */
    private JLabel findInClients(String username) {
        return clients.stream().filter(cliLabel -> cliLabel.getText().equals(username) || cliLabel.getText().equals("<html><font color='gray'>" + username + "</font></html>")).findFirst().orElse(null);
    }

    /**
     * Notifies the room that a user has joined
     * This method should be called when a user joins the room
     * @param username - the user name of the player that joined the room
     */
    public void joined(String username) {
        System.out.println("User joined " + username);
        // checking that user name is not already registered and is valid            
        if ("".equals(username) || username == null || findInClients(username) != null) {
            return;
        }
        findInClients(PLACEHOLDER).setText(username);
        User.getChat().receiveMessage(username + ": JOINED THE ROOM", true);
    }

    /**
     * Notifies the room that a user went AFK
     * This method should be called when a user has gone AFK
     * @param username - the user name of the player that went AFK
     */
    public void afk(String username) {
        // checking that user name is not already registered and is valid  
        if ("".equals(username) || username == null || findInClients(username) == null) {
            return;
        }
        findInClients(username).setText("<html><font color='gray'>" + username + "</font></html>");
        // filter
        if (!User.getChat().getMessages()[User.getChat().getMessages().length-1].contains(username + " IS AFK"))
            User.getChat().receiveMessage(username + " IS AFK", true);
    }

    /**
     * Notifies the room that a user has reconnected
     * This method should be called when a user reconnects to the room
     * @param username - the user name of the player that reconnected to the room
     */
    public void reconnected(String username) {
        // checking that user name is not already registered and is valid
        if ("".equals(username) || username == null || findInClients(username) == null) {
            return;
        }
        findInClients(username).setText(username);
        User.getChat().receiveMessage(username + " RECONNECTED", true);
    }

    /**
     * Notifies the room that a user has left
     * This method should be called when a user left the room
     * @param username - the user name of the player that left the room
     */
    public void left(String username) {
        // checking that user name is not already registered and is valid  
        if ("".equals(username) || username == null || (findInClients(username) == null)) {
            return;
        }
        findInClients(username).setText(PLACEHOLDER);
        // sorting the list so that there are no gray spaces between labels
        clients.sort((JLabel cli1, JLabel cli2) -> {
            if (cli1.getText().equals(PLACEHOLDER))
                return -1;
            return 1;
        });
        User.getChat().receiveMessage(username + ": LEFT THE ROOM", true);
    }

    /**
     * Destroys this room
     * This method should be called when the server destroys the room
     */
    public void roomDestroyed() {
        System.out.println("ROOM DESTROYED");
        topPanel.removeAll();
        topPanel.setBorder(BorderFactory.createTitledBorder("Room details"));
        JButton btnMenu = new JButton("RETURN TO MENU");
        btnMenu.addActionListener(e -> View.enterMenu());
        topPanel.add(btnMenu);
        revalidate();
        repaint();
    }

    /**
     * @return a String that states the actions that a user can do from this state
     */
    public static String getActionsThatCanBeDone() {
        return ACTIONS_THAT_CAN_BE_DONE;
    }

}
