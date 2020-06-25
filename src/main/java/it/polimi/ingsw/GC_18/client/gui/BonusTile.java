package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * This class represents the area where are contained: the bonus tiles of the player,
 * the pawn left of the player and the pass and save buttons.
 */
class BonusTile extends JPanel {

    private static final long serialVersionUID = 1187602057518762802L;

    private String viewPlayer;
    private LeftTile leftTile;
    private RightTile rightTile;
    private Pass pass;
    private Save save;
    private Pawns pawns;

    /**
     * Creates a new BonusTile for the player given his username and his index.
     * @param username the username.
     * @param index the player index.
     */
    BonusTile(String username, int index) {

        this.viewPlayer = username;
        setBackground(new Color(94,73,42));
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridBagLayout);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 2;
        leftTile = new LeftTile(index);
        add(leftTile, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        pass = new Pass(username);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(pass, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        save = new Save();
        c.fill = GridBagConstraints.HORIZONTAL;
        add(save, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        pawns = new Pawns(viewPlayer);
        c.fill = GridBagConstraints.BOTH;
        add(pawns, c);

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        rightTile = new RightTile(index);
        c.fill = GridBagConstraints.BOTH;
        add(rightTile, c);
    }

    /**
     * @return the player's pawn.
     */
    public Pawns getPawns() {
        return pawns;
    }
}
