package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Rectangle;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;


/**
 * This class represents the area where are contained the pawn a player has left for a placement.
 */
public class Pawns extends GameGuiComponent {

    private static final long serialVersionUID = -8461003006808306494L;
    private static final String PAWNS_IMG = "topback.png";
    private static final String PAWNLEFT_1 = "pawnleft1";
    private static final String PAWNLEFT_2 = "pawnleft2";
    private static final String PAWNLEFT_3 = "pawnleft3";
    private static final String PAWNLEFT_4 = "pawnleft4";
    private static final int PAWN_LEFT_1_X = 84;
    private static final int PAWN_LEFT_2_X = 114;
    private static final int PAWN_LEFT_3_X = 144;
    private static final int PAWN_LEFT_4_X = 174;
    private static final int PAWN_LEFT_Y = 127;
    private static final int PAWN_WIDTH = 281 - 252;
    private static final int PAWN_HEIGHT = 310 - 167;

    private String viewPlayer;

    /**
     * Creates a Pawns given the player's username.
     * @param username the username.
     */
    Pawns(String username) {
        super(PAWNS_IMG);
        this.viewPlayer = username;
    }

    /**
     * Sets up all the rectangles to the image handler.
     */
    @Override
    protected void fillImageHandler() {
        setupRectangles();
    }

    /**
     * Updates the player's pawns left images.
     * @param players the list of players.
     */
    void setPawnsLeft(List<Player> players) {
        for (Player player : players) {
            if (player.getUsername().equals(viewPlayer)) {
                imageHandler.getImagesByName().get(PAWNLEFT_1).setImage(GameInfoGui.getPawnAvailable(player, player.getBlackPawn()));
                imageHandler.getImagesByName().get(PAWNLEFT_2).setImage(GameInfoGui.getPawnAvailable(player, player.getOrangePawn()));
                imageHandler.getImagesByName().get(PAWNLEFT_3).setImage(GameInfoGui.getPawnAvailable(player, player.getWhitePawn()));
                imageHandler.getImagesByName().get(PAWNLEFT_4).setImage(GameInfoGui.getPawnAvailable(player, player.getNeutralPawn()));
                break;
            }
        }
    }

    /**
     * Adds the rectangles of the leader cards to the image handler.
     */
    private void setupRectangles() {
        imageHandler.getImagesByName().put(PAWNLEFT_1, new Image(null, null, new Rectangle(PAWN_LEFT_1_X, PAWN_LEFT_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWNLEFT_2, new Image(null, null, new Rectangle(PAWN_LEFT_2_X, PAWN_LEFT_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWNLEFT_3, new Image(null, null, new Rectangle(PAWN_LEFT_3_X, PAWN_LEFT_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWNLEFT_4, new Image(null, null, new Rectangle(PAWN_LEFT_4_X, PAWN_LEFT_Y, PAWN_WIDTH, PAWN_HEIGHT)));
    }

}
