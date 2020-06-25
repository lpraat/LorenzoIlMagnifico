package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Rectangle;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This class represents the area where are contained the development cards of a player.
 */
public class DevelopmentCardBoard extends GameGuiComponent {

    private static final long serialVersionUID = -1913618360971941907L;
    private static final String DEVELOPMENT_BOARD_IMG = "developmentBoard.jpeg";
    private static final String CHARACTER_1 = "character1";
    private static final String CHARACTER_2 = "character2";
    private static final String CHARACTER_3 = "character3";
    private static final String CHARACTER_4 = "character4";
    private static final String CHARACTER_5 = "character5";
    private static final String CHARACTER_6 = "character6";
    private static final String VENTURE_1 = "venture1";
    private static final String VENTURE_2 = "venture2";
    private static final String VENTURE_3 = "venture3";
    private static final String VENTURE_4 = "venture4";
    private static final String VENTURE_5 = "venture5";
    private static final String VENTURE_6 = "venture6";
    private static final String BUILDING_1 = "building1";
    private static final String BUILDING_2 = "building2";
    private static final String BUILDING_3 = "building3";
    private static final String BUILDING_4 = "building4";
    private static final String BUILDING_5 = "building5";
    private static final String BUILDING_6 = "building6";
    private static final String TERRITORY_1 = "territory1";
    private static final String TERRITORY_2 = "territory2";
    private static final String TERRITORY_3 = "territory3";
    private static final String TERRITORY_4 = "territory4";
    private static final String TERRITORY_5 = "territory5";
    private static final String TERRITORY_6 = "territory6";
    private static final int FIRST_CARD_X = 10;
    private static final int FIRST_CARDS_Y = 0;
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 315;
    private static final int SECOND_CARD_X = 230;
    private static final int THIRD_CARD_X = 447;
    private static final int FOURTH_CARD_X = 668;
    private static final int FIFTH_CARD_X = 887;
    private static final int SIXTH_CARD_X = 1106;
    private static final int SECOND_CARDS_Y = 319;
    private static final int THIRD_CARDS_Y = 638;
    private static final int FOURTH_CARDS_Y = 1047;

    private String viewPlayer;

    /**
     * Creates a new space for the player named username.
     * @param username the username of the player.
     */
    DevelopmentCardBoard(String username) {
        super(DEVELOPMENT_BOARD_IMG);
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
     * Updates the player's development cards images.
     * @param playerList the list of the players in the game.
     */
    void setDevelopmentCards(List<Player> playerList) {
        for (Player player : playerList) {
            if (player.getUsername().equals(viewPlayer)) {
                imageHandler.getImagesByName().get(CHARACTER_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 0));
                imageHandler.getImagesByName().get(CHARACTER_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 1));
                imageHandler.getImagesByName().get(CHARACTER_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 2));
                imageHandler.getImagesByName().get(CHARACTER_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 3));
                imageHandler.getImagesByName().get(CHARACTER_5).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 4));
                imageHandler.getImagesByName().get(CHARACTER_6).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getCharacters(), 5));

                imageHandler.getImagesByName().get(VENTURE_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 0));
                imageHandler.getImagesByName().get(VENTURE_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 1));
                imageHandler.getImagesByName().get(VENTURE_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 2));
                imageHandler.getImagesByName().get(VENTURE_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 3));
                imageHandler.getImagesByName().get(VENTURE_5).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 4));
                imageHandler.getImagesByName().get(VENTURE_6).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getVentures(), 5));

                imageHandler.getImagesByName().get(BUILDING_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 0));
                imageHandler.getImagesByName().get(BUILDING_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 1));
                imageHandler.getImagesByName().get(BUILDING_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 2));
                imageHandler.getImagesByName().get(BUILDING_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 3));
                imageHandler.getImagesByName().get(BUILDING_5).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 4));
                imageHandler.getImagesByName().get(BUILDING_6).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getBuildings(), 5));

                imageHandler.getImagesByName().get(TERRITORY_1).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 0));
                imageHandler.getImagesByName().get(TERRITORY_2).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 1));
                imageHandler.getImagesByName().get(TERRITORY_3).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 2));
                imageHandler.getImagesByName().get(TERRITORY_4).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 3));
                imageHandler.getImagesByName().get(TERRITORY_5).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 4));
                imageHandler.getImagesByName().get(TERRITORY_6).setImage(GameInfoGui.getCardFromPersonalBoard(player.getPersonalBoard().getTerritories(), 5));
                break;
            }
        }


    }

    /**
     * Adds the rectangles to the image handler.
     */
    private void setupRectangles() {
        imageHandler.getImagesByName().put(CHARACTER_1, new Image(null, null, new Rectangle(FIRST_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_2, new Image(null, null, new Rectangle(SECOND_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_3, new Image(null, null, new Rectangle(THIRD_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_4, new Image(null, null, new Rectangle(FOURTH_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_5, new Image(null, null, new Rectangle(FIFTH_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_6, new Image(null, null, new Rectangle(SIXTH_CARD_X, FIRST_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));

        imageHandler.getImagesByName().put(VENTURE_1, new Image(null, null, new Rectangle(FIRST_CARD_X, SECOND_CARDS_Y,CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_2, new Image(null, null, new Rectangle(SECOND_CARD_X, SECOND_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_3, new Image(null, null, new Rectangle(THIRD_CARD_X, SECOND_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_4, new Image(null, null, new Rectangle(FOURTH_CARD_X, SECOND_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_5, new Image(null, null, new Rectangle(FIFTH_CARD_X, SECOND_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_6, new Image(null, null, new Rectangle(SIXTH_CARD_X, SECOND_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));

        imageHandler.getImagesByName().put(BUILDING_1, new Image(null, null, new Rectangle(FIRST_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_2, new Image(null, null, new Rectangle(SECOND_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_3, new Image(null, null, new Rectangle(THIRD_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_4, new Image(null, null, new Rectangle(FOURTH_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_5, new Image(null, null, new Rectangle(FIFTH_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_6, new Image(null, null, new Rectangle(SIXTH_CARD_X, THIRD_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));

        imageHandler.getImagesByName().put(TERRITORY_1, new Image(null, null, new Rectangle(FIRST_CARD_X, FOURTH_CARDS_Y,CARD_WIDTH,CARD_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_2, new Image(null, null, new Rectangle(SECOND_CARD_X, FOURTH_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_3, new Image(null, null, new Rectangle(THIRD_CARD_X, FOURTH_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_4, new Image(null, null, new Rectangle(FOURTH_CARD_X, FOURTH_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_5, new Image(null, null, new Rectangle(FIFTH_CARD_X, FOURTH_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_6, new Image(null, null, new Rectangle(SIXTH_CARD_X, FOURTH_CARDS_Y, CARD_WIDTH, CARD_HEIGHT)));
    }

}
