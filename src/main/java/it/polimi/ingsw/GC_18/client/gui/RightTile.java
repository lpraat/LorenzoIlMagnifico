package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * This class represents the area where is contained the production bonus tile of the player.
 */
class RightTile extends JPanel {

    private static final long serialVersionUID = -3784071784113891365L;

    private int index;

    /**
     * Creates a new RightTile given the tile index.
     * @param index the index;
     */
    RightTile(int index) {
        this.index = index;
        setBackground(new Color(94,73,42));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(AssetsLoader.getAssets().get("bonustile"+index+"production.jpeg"), getWidth()-50, 0, 50, getHeight(), this);
    }
    
}
