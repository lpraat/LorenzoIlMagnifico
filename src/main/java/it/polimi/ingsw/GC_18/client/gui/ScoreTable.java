package it.polimi.ingsw.GC_18.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;

/**
 * This class represents the area where are contained all the player scores and the turn info.
 */
class ScoreTable extends JPanel {

    private static final long serialVersionUID = 2770665337470403807L;
    private static final String BLUE = "blue";
    private static final String GREEN = "green";
    private static final String RED = "red";
    private static final String YELLOW = "yellow";
    private static final String VIOLET = "violet";

    private List<PlayerScore> playerScores;
    private RoundInfo turnInfo;

    /**
     * Creates a new ScoreTable given the list of players.
     * @param players the list of players.
     */
    ScoreTable(List<Player> players) {
        playerScores = new ArrayList<>();
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();


        addSeparator(0);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        turnInfo = new RoundInfo();
        add(turnInfo, c);


        String username0 = players.stream().filter(player -> player.getColor().equals(PlayerColor.BLUE)).collect(Collectors.toList()).get(0).getUsername();
        addSeparator(2);
        addUsername(username0, 3);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        PlayerScore playerScore1 = new PlayerScore(username0, BLUE);
        playerScores.add(playerScore1);
        add(playerScore1, c);

        String username1 = players.stream().filter(player -> player.getColor().equals(PlayerColor.GREEN)).collect(Collectors.toList()).get(0).getUsername();

        addSeparator(5);
        addUsername(username1, 6);

        c.gridy = 7;
        PlayerScore playerScore2 = new PlayerScore(username1, GREEN);
        playerScores.add(playerScore2);
        add(playerScore2, c);
        addSeparator(8);

        if (players.size() > 2) {
            String username2 = players.stream().filter(player -> player.getColor().equals(PlayerColor.RED)).collect(Collectors.toList()).get(0).getUsername();

            addUsername(username2, 9);

            c.gridy = 10;
            PlayerScore playerScore3 = new PlayerScore(username2, RED);
            playerScores.add(playerScore3);
            add(playerScore3, c);
            addSeparator(11);

        }

        if (players.size() > 3) {
            String username3 = players.stream().filter(player -> player.getColor().equals(PlayerColor.YELLOW)).collect(Collectors.toList()).get(0).getUsername();

            addUsername(username3, 12);

            c.gridy = 13;
            PlayerScore playerScore4 = new PlayerScore(username3, YELLOW);
            playerScores.add(playerScore4);
            add(playerScore4, c);
            addSeparator(14);

        }

        if (players.size() > 4) {
            String username4 = players.stream().filter(player -> player.getColor().equals(PlayerColor.VIOLET)).collect(Collectors.toList()).get(0).getUsername();

            addUsername(username4, 15);

            c.gridy = 16;
            PlayerScore playerScore4 = new PlayerScore(username4, VIOLET);
            playerScores.add(playerScore4);
            add(playerScore4, c);
            addSeparator(17);
        }
    }

    /**
     * @return the player scores.
     */
    List<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    /**
     * @return the turn info.
     */
    RoundInfo getTurnInfo() {
        return turnInfo;
    }

    /**
     * Adds a new username the in the layout.
     * @param username the player username
     * @param y the y coordinate in the grid bag layout grid.
     */
    private void addUsername(String username, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy= y;
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new JLabel("<html><font color='red'>"+ username + "</font></html>", SwingConstants.CENTER), c);
    }

    /**
     * Adds a new separator in the layout.
     * @param y the y coordinate in the grid bag layout grid.
     */
    private void addSeparator(int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy= y;
        c.weighty = 1;
        c.weightx =1;
        c.ipady = 10;
        c.fill = GridBagConstraints.BOTH;
        add(new JSeparator(), c);
    }
    
}
