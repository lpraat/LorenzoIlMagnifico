package it.polimi.ingsw.GC_18.client.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.State;
import it.polimi.ingsw.GC_18.client.View;

/**
 * A JPanel for displaying a menu of choices that a user can do.
 */
public class Menu extends JPanel {

    private static final long serialVersionUID = 955026899404680804L;
    private static final String ACTIONS_THAT_CAN_BE_DONE = "FIND_GAME - SHOW_STATS - OPTIONS - EXIT";

    /**
     * Welcomes the user to the game and shows him the action he can do (find a game, show statistics, exit).
     * @param username - the user's name, used to greet after login
     */
    public Menu(String username) {
        System.out.println("WELCOME IN MENU " + username);
        Controller.setState(State.IN_MENU);
        setLayout(new GridLayout(0, 1));
        JLabel lblWelcome = new JLabel("Welcome to LorenzoIlMagnifico " + username);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblWelcome, "0,0");
        JButton btnFindGame = new JButton("Find game");
        add(btnFindGame, "0,1");
        btnFindGame.addActionListener(e -> View.enterRoom(false));
        JButton btnShowStats = new JButton("Show Stats");
        add(btnShowStats, "0,2");
        btnShowStats.addActionListener(e -> View.showStats());
        JButton btnExit = new JButton("Exit");
        add(btnExit, "0,3");
        btnExit.addActionListener(e -> Controller.exit());
    }

    /**
     * @return a String that states the actions that a user can do from this state
     */
    public static String getActionsThatCanBeDone() {
        return ACTIONS_THAT_CAN_BE_DONE;
    }

}
