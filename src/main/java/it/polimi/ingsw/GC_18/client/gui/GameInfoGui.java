package it.polimi.ingsw.GC_18.client.gui;

import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.PlayerColor;
import it.polimi.ingsw.GC_18.model.cards.Card;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.gamepieces.VaticanSpot;

/**
 * This class is used from the gui components for updating the images in their image handlers.
 */

final class GameInfoGui {

    private static final String PAWN = "Pawn";
    private static final String PNG = ".png";
    private static final String CUBE = "cube";
    private static final String RED = "Red";
    private static final String BLUE = "Blue";
    private static final String GREEN = "Green";
    private static final String YELLOW = "Yellow";
    private static final String VIOLET = "Violet";
    private static final String BLACK = "black";
    private static final String ORANGE = "orange";
    private static final String WHITE = "white";
    private static final String NEUTRAL = "neutral";


    /**
     * Hidden constructor.
     */
    private GameInfoGui() {
        // hide constructor
    }
    /**
     * Gets the image of a pawn among a list of pawns given the index.
     * @param pawns the list of pawns.
     * @param index the index.
     * @return the image of the pawn at the index position in the list if found, null otherwise
     */
    static String getPawnfromList(List<Pawn> pawns, int index) {
        if (pawns.size() > index && pawns.get(index) != null) {
            Pawn pawn = pawns.get(index);
            if (pawn != null) {
                Player player = pawn.getPlayer();
                return getPawnColor(pawn) + getPlayerColor(player) + PAWN + PNG;
            }
        }
        return null;
    }

    /**
     * Gets the image of a pawn if it is not already placed.
     * @param player the player of the pawn.
     * @param pawn the pawn.
     * @return the image of the not yet placed pawn.
     */
    static String getPawnAvailable(Player player, Pawn pawn) {
        if (!pawn.isPlaced()) {
            return getPawnColor(pawn) + getPlayerColor(player) + PAWN + PNG;
        }
        return null;
    }

    /**
     * Gets the image of a pawn placed in an action place.
     * @param actionPlace the action place.
     * @return the image of the pawn in the place.
     */
    static String getPawn(ActionPlace actionPlace) {
        if (actionPlace.getPawns().isEmpty()) {
            return null;
        }
        Pawn pawn = actionPlace.getPawns().get(actionPlace.getPawns().size() - 1);
        if (pawn != null) {
            Player player = pawn.getPlayer();
            return getPawnColor(pawn) + getPlayerColor(player) + PAWN + PNG;
        } else {
            return null;
        }
    }

    /**
     * Gets the image of a card from a list of cards.
     * @param cards the cards collection.
     * @param index the index of the card.
     * @return the image of the card.
     */
    static String getCardFromPersonalBoard(List<? extends Card> cards, int index) {
        if (cards.size() > index && cards.get(index) != null) {
            return cards.get(index).getName() + PNG;
        }
        return null;

    }


    /**
     * Gets the image of a card contained in a floor.
     * @param floor the floor.
     * @return the image of the card.
     */
    static String getCardFromFloor(Floor<? extends DevelopmentCard> floor) {
        if (floor.getCard() != null) {
            return floor.getCard().getName() + PNG;
        } else {
            return null;
        }
    }

    /**
     * Gets the image of the excomunication tile in a vatican spot.
     * @param vaticanSpot the vatican spot.
     * @return the image of the excomunication tile.
     */
    static String getExcomunicationTile(VaticanSpot vaticanSpot) {
        if (vaticanSpot.getExcomunicationTile() != null) {
            return vaticanSpot.getExcomunicationTile().getName() + PNG;
        } else {
            return null;
        }
    }

    /**
     * Gets the image of the excomunication cube of a player.
     * @param player the player.
     * @return the image of the excomunication cube.
     */
    static String getCube(Player player) {
        return CUBE +  getPlayerColor(player) + PNG;
    }

    /**
     * Gets the string representing the number of a player.
     * @param player the player.
     * @return the string representing the number.
     */
    static String getPlayerNumber(Player player) {
        String color = getPlayerColor(player);

        switch (color) {

            case RED:
                return "0";

            case BLUE:
                return "1";


            case GREEN:
                return "2";

            case YELLOW:
                return "3";

            case VIOLET:
                return "4";

            default:
                return "";
        }

    }

    /**
     * Return the current faith track position of the player.
     * @param player the player.
     * @return the string representing the position.
     */
    static String getFaithTrackPosition(Player player) {
        int playerFaith = player.getFaithPoints();
        if (playerFaith > 15) {
            playerFaith = 15;
        }
        return playerFaith + getPlayerNumber(player);
    }

    /**
     * Gets the image of the marker of the player.
     * @param player the player.
     * @return the image of the marker.
     */
    static String getMarker(Player player) {
        return getPlayerColor(player).toLowerCase() + ".png";
    }

    /**
     * Gets the string representing the color of a player.
     * @param player the player.
     * @return the string representing the color.
     */
    static String getPlayerColor(Player player) {
        PlayerColor playerColor = player.getColor();
        switch (playerColor) {
            case RED:
                return RED;
            case GREEN:
                return GREEN;
            case BLUE:
                return BLUE;
            case YELLOW:
                return YELLOW;
            case VIOLET:
                return VIOLET;
            default:
                return "";
        }
    }

    /**
     * The string used in images name for denoting the color of a pawn.
     * @param pawn the pawn.
     * @return the string.
     */
    static String getPawnColor(Pawn pawn) {
        switch (pawn.getColor()) {
            case BLACK:
                return BLACK;
            case ORANGE:
                return ORANGE;
            case WHITE:
                return WHITE;
            case NEUTRAL:
                return NEUTRAL;
            default:
                return null;
        }
    }
}
