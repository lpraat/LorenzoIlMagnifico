package it.polimi.ingsw.GC_18.client.gui;

import javax.swing.JButton;

import it.polimi.ingsw.GC_18.client.Controller;

/**
 * This class represents the area where is contained the button save.
 */
class Save extends JButton {

    private static final long serialVersionUID = 5878228488159593673L;

    /**
     * Creates a new Save.
     */
    Save() {
        setText("Save");
        addActionListener(e -> Controller.save());
    }

}
