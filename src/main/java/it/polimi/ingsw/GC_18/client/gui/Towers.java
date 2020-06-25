package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Rectangle;

import it.polimi.ingsw.GC_18.model.Board;

/**
 * This class represents the area where are contained the towers.
 */
public class Towers extends GameGuiComponent {

    private static final long serialVersionUID = -4737584532923061256L;
    private static final int FIRST_TOWER_X = 4;
    private static final int FLOOR4_Y = 53;
    private static final int FLOOR_WIDTH = 170 - 4;
    private static final int FLOOR_HEIGHT = 320 - 53;
    private static final int FLOOR3_Y = 336;
    private static final int FLOOR2_Y = 620;
    private static final int FLOOR1_Y = 902;
    private static final int SECOND_TOWER_X = 309;
    private static final int THIRD_TOWER_X = 613;
    private static final int FOURTH_TOWER_X = 918;
    private static final int FIRST_TOWER_PAWN_X = 252;
    private static final int FLOOR4_PAWN_Y = 154;
    private static final int PAWN_WIDTH = 281 - 252;
    private static final int PAWN_HEIGHT = 210 - 167;
    private static final int FLOOR3_PAWN_Y = 438;
    private static final int FLOOR2_PAWN_Y = 722;
    private static final int FLOOR1_PAWN_Y = 1006;
    private static final int SECOND_TOWER_PAWN_X = 557;
    private static final int THIRD_TOWER_PAWN_X = 862;
    private static final int FOURTH_TOWER_PAWN_X = 1167;
    private static final int FIRST_TOWER_CLICK_X = 178;
    private static final int SECOND_TOWER_CLICK_X = 478;
    private static final int THIRD_TOWER_CLICK_X = 778;
    private static final int FOURTH_TOWER_CLICK_X = 1078;
    private static final int FLOOR4_CLICK_Y = 122;
    private static final int FLOOR3_CLICK_Y = 408;
    private static final int FLOOR2_CLICK_Y = 694;
    private static final int FLOOR1_CLICK_Y = 980;
    private static final int FLOOR_CLICK_WIDTH = 296 - 178;
    private static final int FLOOR_CLICK_HEIGHT = 234 - 122;
    private static final String PAWN_1 = "pawn1";
    private static final String PAWN_2 = "pawn2";
    private static final String PAWN_3 = "pawn3";
    private static final String PAWN_4 = "pawn4";
    private static final String PAWN_5 = "pawn5";
    private static final String PAWN_6 = "pawn6";
    private static final String PAWN_7 = "pawn7";
    private static final String PAWN_8 = "pawn8";
    private static final String PAWN_9 = "pawn9";
    private static final String PAWN_10 = "pawn10";
    private static final String PAWN_11 = "pawn11";
    private static final String PAWN_12 = "pawn12";
    private static final String PAWN_13 = "pawn13";
    private static final String PAWN_14 = "pawn14";
    private static final String PAWN_15 = "pawn15";
    private static final String PAWN_16 = "pawn16";
    private static final String FLOOR_1 = "floor1";
    private static final String FLOOR_2 = "floor2";
    private static final String FLOOR_3 = "floor3";
    private static final String FLOOR_4 = "floor4";
    private static final String FLOOR_5 = "floor5";
    private static final String FLOOR_6 = "floor6";
    private static final String FLOOR_7 = "floor7";
    private static final String FLOOR_8 = "floor8";
    private static final String FLOOR_9 = "floor9";
    private static final String FLOOR_10 = "floor10";
    private static final String FLOOR_11 = "floor11";
    private static final String FLOOR_12 = "floor12";
    private static final String FLOOR_13 = "floor13";
    private static final String FLOOR_14 = "floor14";
    private static final String FLOOR_15 = "floor15";
    private static final String FLOOR_16 = "floor16";
    private static final String TERRITORY_4 = "TERRITORY4";
    private static final String TERRITORY_3 = "TERRITORY3";
    private static final String TERRITORY_2 = "TERRITORY2";
    private static final String TERRITORY_1 = "TERRITORY1";
    private static final String CHARACTER_4 = "CHARACTER4";
    private static final String CHARACTER_3 = "CHARACTER3";
    private static final String CHARACTER_2 = "CHARACTER2";
    private static final String CHARACTER_1 = "CHARACTER1";
    private static final String BUILDING_4 = "BUILDING4";
    private static final String BUILDING_3 = "BUILDING3";
    private static final String BUILDING_2 = "BUILDING2";
    private static final String BUILDING_1 = "BUILDING1";
    private static final String VENTURE_4 = "VENTURE4";
    private static final String VENTURE_3 = "VENTURE3";
    private static final String VENTURE_2 = "VENTURE2";
    private static final String VENTURE_1 = "VENTURE1";
    private static final String BCKG_IMG = "board.jpeg";

    /**
     * Creates a new Towers.
     */
    public Towers() {
        super(BCKG_IMG);
    }


    /**
     * Updates the pawn images in the towers's floors.
     * @param board the game board.
     */
    void setPawnsInTower(Board board) {
        imageHandler.getImagesByName().get(PAWN_1).setImage(GameInfoGui.getPawn(board.getTerritoryTower().getFourthFloor()));
        imageHandler.getImagesByName().get(PAWN_2).setImage(GameInfoGui.getPawn(board.getTerritoryTower().getThirdFloor()));
        imageHandler.getImagesByName().get(PAWN_3).setImage(GameInfoGui.getPawn(board.getTerritoryTower().getSecondFloor()));
        imageHandler.getImagesByName().get(PAWN_4).setImage(GameInfoGui.getPawn(board.getTerritoryTower().getFirstFloor()));
       
        imageHandler.getImagesByName().get(PAWN_5).setImage(GameInfoGui.getPawn(board.getCharacterTower().getFourthFloor()));
        imageHandler.getImagesByName().get(PAWN_6).setImage(GameInfoGui.getPawn(board.getCharacterTower().getThirdFloor()));
        imageHandler.getImagesByName().get(PAWN_7).setImage(GameInfoGui.getPawn(board.getCharacterTower().getSecondFloor()));
        imageHandler.getImagesByName().get(PAWN_8).setImage(GameInfoGui.getPawn(board.getCharacterTower().getFirstFloor()));
       
        imageHandler.getImagesByName().get(PAWN_9).setImage(GameInfoGui.getPawn(board.getBuildingTower().getFourthFloor()));
        imageHandler.getImagesByName().get(PAWN_10).setImage(GameInfoGui.getPawn(board.getBuildingTower().getThirdFloor()));
        imageHandler.getImagesByName().get(PAWN_11).setImage(GameInfoGui.getPawn(board.getBuildingTower().getSecondFloor()));
        imageHandler.getImagesByName().get(PAWN_12).setImage(GameInfoGui.getPawn(board.getBuildingTower().getFirstFloor()));

        imageHandler.getImagesByName().get(PAWN_13).setImage(GameInfoGui.getPawn(board.getVentureTower().getFourthFloor()));
        imageHandler.getImagesByName().get(PAWN_14).setImage(GameInfoGui.getPawn(board.getVentureTower().getThirdFloor()));
        imageHandler.getImagesByName().get(PAWN_15).setImage(GameInfoGui.getPawn(board.getVentureTower().getSecondFloor()));
        imageHandler.getImagesByName().get(PAWN_16).setImage(GameInfoGui.getPawn(board.getVentureTower().getFirstFloor()));
    }

    /**
     * Updates the card images in the towers's floors.
     * @param board the game board.
     */
    void setCardsInTower(Board board) {
        imageHandler.getImagesByName().get(FLOOR_1).setImage(GameInfoGui.getCardFromFloor(board.getTerritoryTower().getFourthFloor()));
        imageHandler.getImagesByName().get(FLOOR_2).setImage(GameInfoGui.getCardFromFloor(board.getTerritoryTower().getThirdFloor()));
        imageHandler.getImagesByName().get(FLOOR_3).setImage(GameInfoGui.getCardFromFloor(board.getTerritoryTower().getSecondFloor()));
        imageHandler.getImagesByName().get(FLOOR_4).setImage(GameInfoGui.getCardFromFloor(board.getTerritoryTower().getFirstFloor()));

        imageHandler.getImagesByName().get(FLOOR_5).setImage(GameInfoGui.getCardFromFloor(board.getCharacterTower().getFourthFloor()));
        imageHandler.getImagesByName().get(FLOOR_6).setImage(GameInfoGui.getCardFromFloor(board.getCharacterTower().getThirdFloor()));
        imageHandler.getImagesByName().get(FLOOR_7).setImage(GameInfoGui.getCardFromFloor(board.getCharacterTower().getSecondFloor()));
        imageHandler.getImagesByName().get(FLOOR_8).setImage(GameInfoGui.getCardFromFloor(board.getCharacterTower().getFirstFloor()));

        imageHandler.getImagesByName().get(FLOOR_9).setImage(GameInfoGui.getCardFromFloor(board.getBuildingTower().getFourthFloor()));
        imageHandler.getImagesByName().get(FLOOR_10).setImage(GameInfoGui.getCardFromFloor(board.getBuildingTower().getThirdFloor()));
        imageHandler.getImagesByName().get(FLOOR_11).setImage(GameInfoGui.getCardFromFloor(board.getBuildingTower().getSecondFloor()));
        imageHandler.getImagesByName().get(FLOOR_12).setImage(GameInfoGui.getCardFromFloor(board.getBuildingTower().getFirstFloor()));

        imageHandler.getImagesByName().get(FLOOR_13).setImage(GameInfoGui.getCardFromFloor(board.getVentureTower().getFourthFloor()));
        imageHandler.getImagesByName().get(FLOOR_14).setImage(GameInfoGui.getCardFromFloor(board.getVentureTower().getThirdFloor()));
        imageHandler.getImagesByName().get(FLOOR_15).setImage(GameInfoGui.getCardFromFloor(board.getVentureTower().getSecondFloor()));
        imageHandler.getImagesByName().get(FLOOR_16).setImage(GameInfoGui.getCardFromFloor(board.getVentureTower().getFirstFloor()));
    }

    /**
     * Adds the click rectangles to the image handler.
     */
    private void setupClickRectangles() {
        imageHandler.getImagesByName().put(TERRITORY_4, new Image(null, null, new Rectangle(FIRST_TOWER_CLICK_X, FLOOR4_CLICK_Y, FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_3, new Image(null, null, new Rectangle(FIRST_TOWER_CLICK_X, FLOOR3_CLICK_Y,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_2, new Image(null, null, new Rectangle(FIRST_TOWER_CLICK_X, FLOOR2_CLICK_Y,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(TERRITORY_1, new Image(null, null, new Rectangle(FIRST_TOWER_CLICK_X, FLOOR1_CLICK_Y,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));

        imageHandler.getImagesByName().put(CHARACTER_4, new Image(null, null, new Rectangle(SECOND_TOWER_CLICK_X, FLOOR4_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_3, new Image(null, null, new Rectangle(SECOND_TOWER_CLICK_X, FLOOR3_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_2, new Image(null, null, new Rectangle(SECOND_TOWER_CLICK_X, FLOOR2_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(CHARACTER_1, new Image(null, null, new Rectangle(SECOND_TOWER_CLICK_X, FLOOR1_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));

        imageHandler.getImagesByName().put(BUILDING_4, new Image(null, null, new Rectangle(THIRD_TOWER_CLICK_X, FLOOR4_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_3, new Image(null, null, new Rectangle(THIRD_TOWER_CLICK_X, FLOOR3_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_2, new Image(null, null, new Rectangle(THIRD_TOWER_CLICK_X, FLOOR2_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(BUILDING_1, new Image(null, null, new Rectangle(THIRD_TOWER_CLICK_X, FLOOR1_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));

        imageHandler.getImagesByName().put(VENTURE_4, new Image(null, null, new Rectangle(FOURTH_TOWER_CLICK_X, FLOOR4_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_3, new Image(null, null, new Rectangle(FOURTH_TOWER_CLICK_X, FLOOR3_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_2, new Image(null, null, new Rectangle(FOURTH_TOWER_CLICK_X, FLOOR2_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));
        imageHandler.getImagesByName().put(VENTURE_1, new Image(null, null, new Rectangle(FOURTH_TOWER_CLICK_X, FLOOR1_CLICK_Y ,FLOOR_CLICK_WIDTH, FLOOR_CLICK_HEIGHT)));

    }


    /**
     * Adds the rectangles of floors' cards and pawns to the image handler.
     */
    private void setupRectangles() {
        imageHandler=new ImagesHandler();
        imageHandler.getImagesByName().put(FLOOR_1, new Image(null, null, new Rectangle(FIRST_TOWER_X, FLOOR4_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_2, new Image(null, null, new Rectangle(FIRST_TOWER_X, FLOOR3_Y,FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_3, new Image(null, null, new Rectangle(FIRST_TOWER_X, FLOOR2_Y,FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_4, new Image(null, null, new Rectangle(FIRST_TOWER_X, FLOOR1_Y,FLOOR_WIDTH, FLOOR_HEIGHT)));


        imageHandler.getImagesByName().put(FLOOR_5, new Image(null, null, new Rectangle(SECOND_TOWER_X, FLOOR4_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_6, new Image(null, null, new Rectangle(SECOND_TOWER_X, FLOOR3_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_7, new Image(null, null, new Rectangle(SECOND_TOWER_X, FLOOR2_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_8, new Image(null, null, new Rectangle(SECOND_TOWER_X, FLOOR1_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));


        imageHandler.getImagesByName().put(FLOOR_9, new Image(null, null, new Rectangle(THIRD_TOWER_X, FLOOR4_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_10, new Image(null, null, new Rectangle(THIRD_TOWER_X, FLOOR3_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_11, new Image(null, null, new Rectangle(THIRD_TOWER_X, FLOOR2_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_12, new Image(null, null, new Rectangle(THIRD_TOWER_X, FLOOR1_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));

        imageHandler.getImagesByName().put(FLOOR_13, new Image(null, null, new Rectangle(FOURTH_TOWER_X, FLOOR4_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_14, new Image(null, null, new Rectangle(FOURTH_TOWER_X, FLOOR3_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_15, new Image(null, null, new Rectangle(FOURTH_TOWER_X, FLOOR2_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));
        imageHandler.getImagesByName().put(FLOOR_16, new Image(null, null, new Rectangle(FOURTH_TOWER_X, FLOOR1_Y, FLOOR_WIDTH, FLOOR_HEIGHT)));

        imageHandler.getImagesByName().put(PAWN_1, new Image(null, null, new Rectangle(FIRST_TOWER_PAWN_X, FLOOR4_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_2, new Image(null, null, new Rectangle(FIRST_TOWER_PAWN_X, FLOOR3_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_3, new Image(null, null, new Rectangle(FIRST_TOWER_PAWN_X, FLOOR2_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_4, new Image(null, null, new Rectangle(FIRST_TOWER_PAWN_X, FLOOR1_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));

        imageHandler.getImagesByName().put(PAWN_5, new Image(null, null, new Rectangle(SECOND_TOWER_PAWN_X, FLOOR4_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_6, new Image(null, null, new Rectangle(SECOND_TOWER_PAWN_X, FLOOR3_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_7, new Image(null, null, new Rectangle(SECOND_TOWER_PAWN_X, FLOOR2_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_8, new Image(null, null, new Rectangle(SECOND_TOWER_PAWN_X, FLOOR1_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));

        imageHandler.getImagesByName().put(PAWN_9, new Image(null, null, new Rectangle(THIRD_TOWER_PAWN_X, FLOOR4_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_10, new Image(null, null, new Rectangle(THIRD_TOWER_PAWN_X, FLOOR3_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_11, new Image(null, null, new Rectangle(THIRD_TOWER_PAWN_X, FLOOR2_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_12, new Image(null, null, new Rectangle(THIRD_TOWER_PAWN_X, FLOOR1_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));

        imageHandler.getImagesByName().put(PAWN_13, new Image(null, null, new Rectangle(FOURTH_TOWER_PAWN_X, FLOOR4_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_14, new Image(null, null, new Rectangle(FOURTH_TOWER_PAWN_X, FLOOR3_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_15, new Image(null, null, new Rectangle(FOURTH_TOWER_PAWN_X, FLOOR2_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(PAWN_16, new Image(null, null, new Rectangle(FOURTH_TOWER_PAWN_X, FLOOR1_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        setupClickRectangles();
    }

    /**
     * Sets up all the rectangles to the image handler.
     */
    @Override
    protected void fillImageHandler() {
        setupRectangles();
    }

}
