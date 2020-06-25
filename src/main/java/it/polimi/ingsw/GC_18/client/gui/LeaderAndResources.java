package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Rectangle;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.cards.Leader;

/**
 * This class represents the area where are contained the leader cards of a player.
 */

public class LeaderAndResources extends GameGuiComponent {

    private static final long serialVersionUID = 7015252884825204210L;
    private static final String LEADER_AND_RESOURCES_IMG = "leaderAndResources.png";
    private static final String HANDLEADER_1 = "handleader1";
    private static final String HANDLEADER_2 = "handleader2";
    private static final String HANDLEADER_3 = "handleader3";
    private static final String HANDLEADER_4 = "handleader4";
    private static final String COVER_1 = "cover1";
    private static final String COVER_2 = "cover2";
    private static final String PLAYEDLEADER_1 = "playedleader1";
    private static final String PLAYEDLEADER_2 = "playedleader2";
    private static final String PLAYEDLEADER_3 = "playedleader3";
    private static final String PLAYEDLEADER_4 = "playedleader4";
    private static final String COVER_3 = "cover3";
    private static final String COVER_4 = "cover4";
    private static final String BACK_IMG = "back.png";
    private static final int FIRST_LEADER_X = 10;
    private static final int SECOND_LEADER_X = 237;
    private static final int THIRD_LEADER_X = 462;
    private static final int FOURTH_LEADER_X = 687;
    private static final int FIFTH_LEADER_X = 917;
    private static final int SIXTH_LEADER_X = 1147;
    private static final int HAND_LEADERS_Y = 0;
    private static final int PLAYED_LEADERS_Y = 327;
    private static final int LEADER_WIDTH = 208;
    private static final int LEADER_HEIGTH = 323;

    // array that represents which leader played has a once per turn effect
    // for example if the player has 4 leaders and the second and the third have a
    // once effect this array would be [0, 1, 2, 0]
    private int[] once = {0, 0, 0, 0};

    // keeps track of all once the player has and changes the array value accordingly
    private int oncePlayed = 0;

    private String viewPlayer;

    /**
     * Creates a new LeaderAndResources for the player named username.
     * @param username the username.
     */
    public LeaderAndResources(String username) {
        super(LEADER_AND_RESOURCES_IMG);
        this.viewPlayer = username;
    }

    /**
     * Sets up all the rectangles to the image handler.
     */
    @Override
    protected void fillImageHandler() {
        setRectangle();
        setCovers();
    }

    /**
     * Updates the once per turn effects in the list of leaders played.
     * @param leadersPlayed the leaders played.
     */
    void updateOnce(List<Leader> leadersPlayed) {
        for (int i = 0; i < leadersPlayed.size(); i++) {
            if (leadersPlayed.get(i).getOncePerTurnEffect() != null) {
                if (once[i] == 0) {
                    once[i] = oncePlayed + 1;
                    oncePlayed += 1;
                }
            }
        }
    }

    /**
     * @return the array that keeps track of the once played.
     */
    public int[] getOnce() {
        return once;
    }

    /**
     * @return the player for which this component is created.
     */
    String getViewPlayer() {
        return viewPlayer;
    }

    /**
     * Adds the rectangles of the leader cards to the image handler.
     */
    private void setRectangle() {
        imageHandler.getImagesByName().put(HANDLEADER_1, new Image(null, null, new Rectangle(FIRST_LEADER_X, HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(HANDLEADER_2, new Image(null, null, new Rectangle(SECOND_LEADER_X,  HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(HANDLEADER_3, new Image(null, null, new Rectangle(THIRD_LEADER_X, HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(HANDLEADER_4, new Image(null, null, new Rectangle(FOURTH_LEADER_X, HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(COVER_1, new Image(null, null, new Rectangle(FIFTH_LEADER_X, HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(COVER_2, new Image(null, null, new Rectangle(SIXTH_LEADER_X, HAND_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));

        imageHandler.getImagesByName().put(PLAYEDLEADER_1, new Image(null, null, new Rectangle(FIRST_LEADER_X, PLAYED_LEADERS_Y,LEADER_WIDTH,LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(PLAYEDLEADER_2, new Image(null, null, new Rectangle(SECOND_LEADER_X,  PLAYED_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(PLAYEDLEADER_3, new Image(null, null, new Rectangle(THIRD_LEADER_X, PLAYED_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(PLAYEDLEADER_4, new Image(null, null, new Rectangle(FOURTH_LEADER_X, PLAYED_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(COVER_3, new Image(null, null, new Rectangle(FIFTH_LEADER_X, PLAYED_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
        imageHandler.getImagesByName().put(COVER_4, new Image(null, null, new Rectangle(SIXTH_LEADER_X, PLAYED_LEADERS_Y, LEADER_WIDTH, LEADER_HEIGTH)));
    }


    /**
     * Updates the player's leader cards images.
     * @param playerList the list of the players in the game.
     */
    void setLeaders(List<Player> playerList) {
        for (Player player : playerList) {
            if (player.getUsername().equals(viewPlayer)) {
                imageHandler.getImagesByName().get(HANDLEADER_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersHand(), 0));
                imageHandler.getImagesByName().get(HANDLEADER_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersHand(), 1));
                imageHandler.getImagesByName().get(HANDLEADER_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersHand(), 2));
                imageHandler.getImagesByName().get(HANDLEADER_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersHand(), 3));

                imageHandler.getImagesByName().get(PLAYEDLEADER_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersPlayed(), 0));
                imageHandler.getImagesByName().get(PLAYEDLEADER_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersPlayed(), 1));
                imageHandler.getImagesByName().get(PLAYEDLEADER_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersPlayed(), 2));
                imageHandler.getImagesByName().get(PLAYEDLEADER_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getLeadersPlayed(), 3));
                break;
            }
        }
    }

    /**
     * Sets the images of the leader covers in the image handler.
     */
    private void setCovers() {
        imageHandler.getImagesByName().get(COVER_1).setImage(BACK_IMG);
        imageHandler.getImagesByName().get(COVER_2).setImage(BACK_IMG);
        imageHandler.getImagesByName().get(COVER_3).setImage(BACK_IMG);
        imageHandler.getImagesByName().get(COVER_4).setImage(BACK_IMG);
    }
    
}
