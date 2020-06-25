package it.polimi.ingsw.GC_18.client.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.User;

/**
 * Core JFrame used by the game GUI.
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = -1695333934943004294L;
    private static final String ICON_URL="/GC_18/resources/gameAssets/lorenzo_materiale_grafico_compr/Lorenzo_Leaders_compressed/leaders_b_c_00.jpg";

    /**
     * Sets up a JFrame that is used during the course of the games.
     * @param frameName - the name of the window.
     */
    public MainFrame(String frameName) {
        super(frameName);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon(ICON_URL).getImage());
        setFocusable(false);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Controller.exit();
            }
        });
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("GUI settings");
        JMenuItem menuItem = new JMenuItem("Audio on/off");
        menuItem.addActionListener(e -> User.setAudioEnabled(!User.isAudioEnabled()));
        menu.add(menuItem);
        menuBar.add(menu);
        menuItem = new JMenuItem("Switch to command line interface");
        menuItem.addActionListener(e -> User.setUsingGuiInterface(!User.isUsingGuiInterface()));
        menu.add(menuItem);
        setJMenuBar(menuBar);
    }

}
