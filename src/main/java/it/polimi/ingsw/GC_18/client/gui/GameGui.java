package it.polimi.ingsw.GC_18.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import it.polimi.ingsw.GC_18.client.User;
import it.polimi.ingsw.GC_18.client.View;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.Player;

/**
 * Handles the GUI handling of the game in a JPanel
 */
public class GameGui extends JPanel {

    private static final long serialVersionUID = 4706011403663622824L;
    private static final int NUM_PLAYERS_FOR_FIGHT_MODE = 5;

    private transient Dispatcher dispatcher;

    /**
     * Creates the game panel that contains: player's resources and cards the
     * game board the player's personal board
     * @param game the game.
     */
    public GameGui(Game game) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridBagLayout);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 350;

        add(User.getChat(), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.ipady = 0;

        c.fill = GridBagConstraints.BOTH;

        ScoreTable scoreTable = new ScoreTable(game.getPlayers());
        add(scoreTable, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 400;

        JTabbedPane jTabbedPane = new JTabbedPane();

        add(jTabbedPane, c);

        List<DevelopmentCardBoard> developmentCardBoardList = new ArrayList<>();
        List<LeaderAndResources> leaderAndResources = new ArrayList<>();
        List<BonusTile> bonusTiles = new ArrayList<>();

        ToolTipManager.sharedInstance().setInitialDelay(0);

        jTabbedPane.addTab("Your Leaders",
                addContainer(bonusTiles, leaderAndResources, User.getUsername(),
                        game.getPlayers().indexOf(game.getPlayers().stream()
                                .filter(player -> player.getUsername().equals(User.getUsername()))
                                .collect(Collectors.toList()).get(0))));

        DevelopmentCardBoard ofPlayer = new DevelopmentCardBoard(User.getUsername());
        developmentCardBoardList.add(ofPlayer);

        jTabbedPane.addTab("Your Cards", ofPlayer);

        Towers towers = new Towers();
        jTabbedPane.addTab("Towers", towers);

        SecondBoard secondBoard= new SecondBoard(game.getPlayers().size());
        jTabbedPane.addTab("Market-Council-Church", secondBoard);

        FightArea fightArea = null;
        if (game.getPlayers().size() == NUM_PLAYERS_FOR_FIGHT_MODE) {
            fightArea = new FightArea();
            jTabbedPane.addTab("FIGHT PIT", fightArea);
        }

        for (Player player: game.getPlayers()) {
            if (!player.getUsername().equals(User.getUsername())) {
                jTabbedPane.addTab(player.getUsername()+ " Leaders",
                        addContainer(bonusTiles, leaderAndResources, player.getUsername(), game.getPlayers().indexOf(player)));

                DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard(player.getUsername());
                developmentCardBoardList.add(developmentCardBoard);
                jTabbedPane.addTab(player.getUsername() + " Cards", developmentCardBoard);
            }
        }

        dispatcher = new Dispatcher(towers, secondBoard, scoreTable, fightArea, developmentCardBoardList, leaderAndResources, bonusTiles);
        dispatcher.init(game);

        View.getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    /**
     * @param bonusTiles the BonusTile object.
     * @param username the username of the player.
     * @param index the index of the player.
     * @param leaderAndResourcesList the list to add the leaderAndResources tab to.
     * @return a JPanel containing a BonusTile and a LeaderAndResource for the player.
     */
    private JPanel addContainer(List<BonusTile> bonusTiles, List<LeaderAndResources> leaderAndResourcesList, String username, int index) {
        JPanel container = new JPanel();

        BonusTile bonusTile = new BonusTile(username, index);
        bonusTiles.add(bonusTile);

        LeaderAndResources leaderAndResources = new LeaderAndResources(username);
        leaderAndResourcesList.add(leaderAndResources);

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        container.setLayout(gridBagLayout1);
        GridBagConstraints c1 = new GridBagConstraints();

        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1;
        c1.weighty = 1;
        c1.fill = GridBagConstraints.BOTH;
        container.add(bonusTile, c1);

        c1.gridx = 0;
        c1.gridy = 1;
        c1.weightx = 1;
        c1.weighty = 1;
        c1.fill = GridBagConstraints.BOTH;
        c1.ipady = 200;
        container.add(leaderAndResources, c1);

        return container;
    }

    /**
     * @return the gui view dispatcher.
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * @return the numPlayersForFightMode
     */
    static int getNumPlayersForFightMode() {
        return NUM_PLAYERS_FOR_FIGHT_MODE;
    }

}
