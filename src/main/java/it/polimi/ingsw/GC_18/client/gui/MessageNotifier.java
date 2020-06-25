package it.polimi.ingsw.GC_18.client.gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import it.polimi.ingsw.GC_18.client.User;

/**
 * Interface used for notifying of bad behaviors.
 */
public interface MessageNotifier {

    /**
     * Notifies a user of an error or an invalid command using the console and
     * if activated the GUI.
     * @param message - the description of what triggered this method
     */
    static void notifyUser(String message) {
        System.out.println(message);
        if (User.isUsingGuiInterface()) {
            EventQueue.invokeLater(() ->{
                JOptionPane op = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = op.createDialog("WARNING!");
                dialog.setAlwaysOnTop(true);
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);      
                dialog.setVisible(true);
            });
        }
    }

}
