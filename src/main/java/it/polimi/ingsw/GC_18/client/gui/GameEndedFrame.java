package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.client.View;
import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;

/**
 * Displays a frame showing that the game ended and it's winner
 */
public class GameEndedFrame extends JFrame {

    private static final long serialVersionUID = 6037606778778889446L;
    private static final String ICON_URL = "/GC_18/resources/gameAssets/lorenzo_materiale_grafico_compr/Lorenzo_Leaders_compressed/leaders_b_c_00.jpg";
    private static final int WIDTH = 480;

    /**
     * Displays a frame that prompts to the user that the game ended and who's winner of it
     * On close or on button click the user is sent back to the menu screen
     * @param winnerUsername - the username of the winner of the game
     */
    public GameEndedFrame(String winnerUsername) {
        super("GAME ENDED!");
        System.out.println((winnerUsername.equals(User.getUsername()) ? "YOU" : winnerUsername) + " WON THE GAME! THANKS FOR PLAYING!");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                returnToMenu();
            }
        });
        JPanel panel = new JPanel(new GridLayout(0,1));
        JLabel winnerLbl = new JLabel((winnerUsername.equals(User.getUsername()) ? "YOU" : winnerUsername) + " WON THE GAME! THANKS FOR PLAYING!");
        panel.add(winnerLbl);
        JButton btnMenu = new JButton("RETURN TO MENU");
        btnMenu.addActionListener(e -> returnToMenu());
        panel.add(btnMenu);
        add(panel);
        setIconImage(new ImageIcon(ICON_URL).getImage());
        setFocusable(false);
        setVisible(User.isUsingGuiInterface());
        pack();
        setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
        setLocationRelativeTo(null);
        toFront();
    }

    /**
     * Closes this frames and sends the user back to the menu screen
     */
    private void returnToMenu() {
        dispose();
        ConnectionHandler.outputToServer("EXIT_ROOM");
        View.enterMenu();
    }

}
