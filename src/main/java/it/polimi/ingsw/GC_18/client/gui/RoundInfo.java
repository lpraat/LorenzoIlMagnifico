package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.GC_18.model.Game;

/**
 * This class represents the area where are contained info about the round.
 */
public class RoundInfo extends JPanel {

    private static final long serialVersionUID = -7006894212660609032L;

    private JLabel round;
    private JLabel period;

    /**
     * Creates a new TurnInfo.
     */
    RoundInfo() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();


        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        round = new JLabel("", SwingConstants.CENTER);
        round.setForeground(Color.BLUE);
        add(round, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        period = new JLabel("", SwingConstants.CENTER);
        period.setForeground(Color.BLUE);
        add(period, c);

    }

    /**
     * Updates the round and period text.
     * @param game the game.
     */
    public void update(Game game) {
        if (game.getTurnInfo()[0] == 7) {
            round.setText("ROUND " + 6);
        } else {
            round.setText("ROUND: " + game.getTurnInfo()[0]);
        }
        period.setText("PERIOD: " +  game.getTurnInfo()[1]);
    }
}
