package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * This class represents the area where is contained the harvest bonus tile of the player.
 */
class LeftTile extends JPanel {

    private static final long serialVersionUID = -2714047701180185154L;
    
    private int index;

    /**
     * Creates a new LeftTile given the tile index.
     * @param index the index;
     */
    LeftTile(int index) {
        this.index = index;
        setBackground(new Color(94,73,42));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(AssetsLoader.getAssets().get("bonustile"+index+"harvest.jpeg"), 0, 0, 50, getHeight(), this);
    }
    
}
