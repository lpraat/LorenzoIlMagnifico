package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;

/**
 * This class represents the area where is contained the player's score.
 */
class PlayerScore extends JPanel {

    private static final long serialVersionUID = -759584909472874297L;
    private static final String MONEY_IMG = "money.png";
    private static final String STONES_IMG = "stones.png";
    private static final String WOODS_IMG = "woods.png";
    private static final String SERVANTS_IMG = "servants.png";
    private static final String VICTORY_IMG = "victory.png";
    private static final String MILITARY_IMG = "military.png";
    private static final String FAITH_IMG = "faith.png";

    // the score of the player of this client
    private static PlayerScore userScore;
    
    private JLabel money;
    private JLabel moneyPoints;
    private JLabel stones;
    private JLabel stonesPoints;
    private JLabel woods;
    private JLabel woodsPoints;
    private JLabel servants;
    private JLabel servantsPoints;
    private JLabel avatar;
    private JLabel victory;
    private JLabel victoryPoints;
    private JLabel faith;
    private JLabel faithPoints;
    private JLabel military;
    private JLabel militaryPoints;
    private String viewPlayer;

    /**
     * Creates a new PlayerScore given the player's username and the color.
     * @param username the username.
     * @param color the color.
     */
    PlayerScore(String username, String color) {
        if (username.equals(User.getUsername())) {
            userScore = this;
        }
        this.viewPlayer = username;
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx=0;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=2;
        c.fill = GridBagConstraints.BOTH;
        avatar = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(color+".png")).getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH)));
        avatar.setPreferredSize(new Dimension(70, 50));
        add(avatar, c);

        c.gridx=1;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=1;
        money = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(MONEY_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        money.setPreferredSize(new Dimension(40, 40));
        add(money, c);

        c.gridy = 1;
        moneyPoints = new JLabel("0", SwingConstants.CENTER);
        add(moneyPoints, c);

        c.gridx=2;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=1;
        stones = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(STONES_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        stones.setPreferredSize(new Dimension(40, 40));
        add(stones, c);

        c.gridy = 1;
        stonesPoints = new JLabel("0", SwingConstants.CENTER);
        add(stonesPoints, c);

        c.gridx=3;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=1;
        woods = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(WOODS_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        woods.setPreferredSize(new Dimension(40, 40));
        add(woods, c);

        c.gridy = 1;
        woodsPoints = new JLabel("0", SwingConstants.CENTER);
        add(woodsPoints, c);

        c.gridx=4;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=1;
        servants = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(SERVANTS_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        servants.setPreferredSize(new Dimension(40, 40));
        add(servants, c);

        c.gridy = 1;
        servantsPoints = new JLabel("0", SwingConstants.CENTER);
        add(servantsPoints, c);

        c.gridx=5;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        c.gridheight=1;
        victory = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(VICTORY_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        victory.setPreferredSize(new Dimension(40, 40));
        add(victory, c);

        c.gridy = 1;
        victoryPoints = new JLabel("0", SwingConstants.CENTER);
        add(victoryPoints, c);

        c.gridx=6;
        c.gridy=0;
        military = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(MILITARY_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        military.setPreferredSize(new Dimension(40, 40));
        add(military, c);

        c.gridy=1;
        militaryPoints =  new JLabel("0", SwingConstants.CENTER);
        add(militaryPoints, c);

        c.gridx=7;
        c.gridy=0;
        faith = new JLabel(new ImageIcon(new ImageIcon(AssetsLoader.getAssets().get(FAITH_IMG)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        faith.setPreferredSize(new Dimension(40, 40));
        add(faith, c);

        c.gridy=1;
        faithPoints= new JLabel("0", SwingConstants.CENTER);
        add(faithPoints,c);
    }

    /**
     * @return the servants point.
     */
    String getServantsPoints() {
        return servantsPoints.getText();
    }


    /**
     * @return the user score.
     */
    static PlayerScore getUserScore() {
        return userScore;
    }

    /**
     * Updates the gui according to the player's pawns left.
     * @param players the list of players.
     */
    void setScores(List<Player> players) {
        for (Player player : players) {
            if (player.getUsername().equals(viewPlayer)) {
                moneyPoints.setText(String.valueOf(player.getMoney()));
                stonesPoints.setText(String.valueOf(player.getStones()));
                woodsPoints.setText(String.valueOf(player.getWoods()));
                servantsPoints.setText(String.valueOf(player.getServants()));
                victoryPoints.setText(String.valueOf(player.getVictoryPoints()));
                faithPoints.setText(String.valueOf(player.getFaithPoints()));
                militaryPoints.setText(String.valueOf(player.getMilitaryPoints()));
            }
        }
    }
    
}
