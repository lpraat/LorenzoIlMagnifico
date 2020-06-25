package it.polimi.ingsw.GC_18.client.gui;

import java.util.List;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.Game;

/**
 * This class represents the gui dispatcher. It has the reference of all of the gui components.
 * Its goal is to dispatch game objects to the gui components
 * so that every piece can update itself according to the game model status.
 */
public class Dispatcher {

    private Towers towers;
    private SecondBoard board;
    private ScoreTable scoreTable;
    private FightArea fightArea;
    private List<DevelopmentCardBoard> developmentCardBoards;
    private List<LeaderAndResources> leaderAndResources;
    private List<BonusTile> bonusTiles;


    /**
     * Creates a new Dispatcher that will handle the update of the gui view.
     * @param towers the towers tab.
     * @param board the board tab.
     * @param scoreTable the score table tab.
     * @param fightArea the fight area tab.
     * @param developmentCardBoards the development cards board tab.
     * @param leaderAndResources the leaderAndResources tab.
     * @param bonusTiles the bonusTiles tab.
     */
    Dispatcher(Towers towers, SecondBoard board, ScoreTable scoreTable, FightArea fightArea, List<DevelopmentCardBoard> developmentCardBoards, List<LeaderAndResources> leaderAndResources, List<BonusTile> bonusTiles) {
        this.towers = towers;
        this.board = board;
        this.scoreTable = scoreTable;
        this.fightArea = fightArea;
        this.developmentCardBoards = developmentCardBoards;
        this.leaderAndResources = leaderAndResources;
        this.bonusTiles = bonusTiles;
    }

    /**
     * Initializes the gui.
     * @param game the game model object.
     */
    void init(Game game) {
        bonusTiles.forEach(bonusTile -> bonusTile.getPawns().setPawnsLeft(game.getPlayers()));
    }

    /**
     * This method is called for updating the gui given a game object.
     * @param game the game object.
     */
    public void updateGui(Game game) {
        // Updates Towers

        towers.setPawnsInTower(game.getBoard());
        towers.setCardsInTower(game.getBoard());
        towers.repaint();

        // Updates SecondBoard
        board.setProductionPawns(game.getBoard());
        board.setHarvestPawns(game.getBoard());
        board.setMarket(game.getBoard());
        board.setCouncilPawns(game.getBoard());
        board.setDicesValues(game.getBoard());
        board.setExcomunicationTiles(game.getBoard());

        board.setFaithTrack(game);
        board.setTurnTrack(game);
        board.setupCubes(game);
        board.repaint();

        // Updates DevelopmentCardBoards
        developmentCardBoards.forEach(developmentCardBoard -> developmentCardBoard.setDevelopmentCards(game.getPlayers()));
        developmentCardBoards.forEach(DevelopmentCardBoard::repaint);

        // Updates LeaderAndResources
        leaderAndResources.forEach(leader -> leader.setLeaders(game.getPlayers()));
        leaderAndResources.forEach(leaderAndResources1 -> leaderAndResources1.updateOnce(game.getPlayers()
                .stream().filter(player -> player.getUsername().equals(leaderAndResources1.getViewPlayer()))
                .collect(Collectors.toList())
                .get(0).getPersonalBoard().getLeadersPlayed()));

        leaderAndResources.forEach(LeaderAndResources::repaint);

        // Updates ScoreTable
        scoreTable.getPlayerScores().forEach(playerScore -> playerScore.setScores(game.getPlayers()));
        scoreTable.getPlayerScores().forEach(PlayerScore::repaint);
        scoreTable.getTurnInfo().update(game);
        scoreTable.getTurnInfo().repaint();
        scoreTable.repaint();

        // Updates FightArea
        if (fightArea != null)
            fightArea.update(game);

        // Updates BonusTile
        bonusTiles.forEach(bonusTile -> bonusTile.getPawns().setPawnsLeft(game.getPlayers()));
        bonusTiles.forEach(BonusTile::repaint);
    }

}
