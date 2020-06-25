package it.polimi.ingsw.GC_18.server.controller;

import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.gamepieces.Tower;

/**
 * This class provides method for getting info about the game status.
 */
final class GameInfoCli {


    /**
     * Hidden constructor.
     */
    private GameInfoCli() {
        // hide constructor
    }
    /**
     * @param game the game to get information about.
     * @return the players' game status.
     */
    static String playersStatus(Game game) {
        StringBuilder status = new StringBuilder();
        status.append("Current players status\n");
        status.append("it's turn of player ").append(game.getPlayers().stream().filter(Player::isYourTurn).collect(Collectors.toList()).get(0).getUsername()).append("\n");
        for (Player player : game.getPlayers()) {
            status.append("Player ").append(player.getUsername()).append(" status").append("\n");
            status.append("Position in turn: ").append(game.getPlayers().indexOf(player)).append("\n");
            status.append("Resources:\n");
            status.append("Money: ").append(player.getMoney()).append(", ");
            status.append("Stones: ").append(player.getStones()).append(", ");
            status.append("Woods: ").append(player.getWoods()).append(", ");
            status.append("Servants: ").append(player.getServants()).append(", ");
            status.append("Victory: ").append(player.getVictoryPoints()).append(", ");
            status.append("Military: ").append(player.getMilitaryPoints()).append(", ");
            status.append("Faith: ").append(player.getFaithPoints()).append("\n");
            status.append("Cards:\n");
            status.append("Territory Cards:\n");
            player.getPersonalBoard().getTerritories().forEach(territory -> status.append(territory.toString()).append(" "));
            status.append("\n");
            status.append("Character Cards:\n");
            player.getPersonalBoard().getCharacters().forEach(character -> status.append(character.toString()).append(" "));
            status.append("\n");
            status.append("Building Cards:\n");
            player.getPersonalBoard().getBuildings().forEach(building -> status.append(building.toString()).append(" "));
            status.append("\n");
            status.append("Venture Cards:\n");
            player.getPersonalBoard().getVentures().forEach(venture -> status.append(venture.toString()).append(" "));
            status.append("\n");
            status.append("Leader Cards in Hand:\n");
            player.getPersonalBoard().getLeadersHand().forEach(leader -> status.append(leader.toString()).append("\n"));
            status.append("\n");
            status.append("Leader Cards Played:\n");
            player.getPersonalBoard().getLeadersPlayed().forEach(leader -> status.append(leader.toString()).append("\n"));
            status.append("Suffering the following malus for not having support the church: ");
            player.getPersonalBoard().getExcomunicationTiles().forEach(excomunicationTile -> status.append(excomunicationTile.toString()).append(" "));
            status.append("\n");
        }
        status.append("\n");
        return status.toString();
    }


    /**
     * Appends the status information about a floor.
     * @param status the status.
     * @param floor the floor to get information about.
     * @param floorStr the name of the floor.
     */
    private static void floorStatus(StringBuilder status, Floor<? extends DevelopmentCard> floor, String floorStr) {
        status.append(floorStr).append("\n");
        status.append("The value you need for placing the pawn here: ").append(floor.getPlaceValue()).append("\n");
        status.append("The bonus you get for placing the pawn here: ").append(floor.getBonus().toString()).append("\n");
        floor.getPawns().forEach(pawn -> status.append(pawn.getColor().name()).append(" Pawn of ").append(pawn.getPlayer().getUsername()).append(" "));
        status.append("\n").append("Card: ");
        if (floor.getCard() != null)
            status.append(floor.getCard().toString());
        status.append("\n");
    }


    /**
     * Appends to the status information about towers.
     * @param status the status.
     * @param tower the tower to get information about.
     */
    private static void towerStatus(StringBuilder status, Tower<? extends DevelopmentCard> tower) {
        floorStatus(status, tower.getFirstFloor(), "First Floor");
        floorStatus(status, tower.getSecondFloor(), "Second Floor");
        floorStatus(status, tower.getThirdFloor(), "Third Floor");
        floorStatus(status, tower.getFourthFloor(), "Fourth Floor");

    }

    /**
     * Appends to the status information about an action place.
     * @param status the status.
     * @param actionPlace the action place.
     */
    private static void placeStatus(StringBuilder status, ActionPlace actionPlace) {
        status.append("The value you need for placing the pawn here: ").append(actionPlace.getPlaceValue()).append("\n");
        status.append("The bonus you get for placing the pawn here: ").append(actionPlace.getBonus().toString()).append("\n");
        actionPlace.getPawns().forEach(pawn -> status.append(pawn.getColor().name()).append(" Pawn of").append(pawn.getPlayer().getUsername()).append(" "));
    }


    /**
     * @param board the board to get information about.
     * @return the board's game status.
     */
    static String boardStatus(Board board) {
        StringBuilder status = new StringBuilder();
        status.append("Towers Status").append("\n");

        status.append("Territory Tower").append("\n");
        towerStatus(status, board.getTerritoryTower());
        status.append("Character Tower").append("\n");
        towerStatus(status, board.getCharacterTower());
        status.append("Building Tower").append("\n");
        towerStatus(status, board.getBuildingTower());
        status.append("Venture Tower").append("\n");
        towerStatus(status, board.getVentureTower());

        status.append("Council Palace").append("\n");
        placeStatus(status, board.getCouncilPalace());
        status.append("Market").append("\n");
        status.append("Coin Spot").append("\n");
        placeStatus(status, board.getMarket().getCoinSpot());
        status.append("Servants Spot").append("\n");
        placeStatus(status, board.getMarket().getServantSpot());

        if (board.getMarket().getMilitarySpot() != null) {
            status.append("Military Spot").append("\n");
            placeStatus(status, board.getMarket().getMilitarySpot());
        }

        if (board.getMarket().getCouncilSpot() != null) {
            status.append("Council Spot").append("\n");
            placeStatus(status, board.getMarket().getCouncilSpot());
        }

        status.append("Harvest Area").append("\n");
        placeStatus(status, board.getHarvestSpace().getHarvestArea());

        if (board.getHarvestSpace().getLargeHarvestArea() != null) {
            status.append("Large Harvest Area").append("\n");
            placeStatus(status, board.getHarvestSpace().getLargeHarvestArea());
        }

        status.append("Production Area").append("\n");
        placeStatus(status, board.getProductionSpace().getProductionArea());

        if (board.getProductionSpace().getLargeProductionArea() != null) {
            status.append("Large Production Area").append("\n");
            placeStatus(status, board.getProductionSpace().getLargeProductionArea());
        }

        return status.toString();
    }

}
