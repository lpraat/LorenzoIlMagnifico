package it.polimi.ingsw.GC_18.client.gui;

import javax.swing.JButton;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.User;

/**
 * This class represents the area where is contained the button pass.
 */
class Pass extends JButton {

    private static final long serialVersionUID = -44911958063326961L;
    private String viewPlayer;

    /**
     * Creates a new pass button if the username is the client's username, else
     * just fills the button with the username and the click won't produce a pass command.
     * @param username the username.
     */
    Pass(String username) {
        this.viewPlayer = username;
        if (!username.equals(User.getUsername())) {
            setText(username);
        } else {
            setText("Pass");
        }

        addActionListener(e -> {
            if (viewPlayer.equals(User.getUsername())) {
                Controller.pass();
            }
        });
    }




    
}
