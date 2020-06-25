package it.polimi.ingsw.GC_18.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import it.polimi.ingsw.GC_18.model.Board;
import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.Player;


/**
 * This class represents the area where is contained the game board excluding towers.
 */
public class SecondBoard extends GameGuiComponent {

    private static final long serialVersionUID = -1923161843160592903L;
    private static final int EXCOM1_WIDTH = 129;
    private static final int EXCOM2_WIDTH = 130;
    private static final int EXCOM1_X = 255;
    private static final int EXCOM1_Y = 208;
    private static final int EXCOM1_HEIGHT = 274;
    private static final int EXCOM2_X = 396;
    private static final int EXCOM2_Y = 244;
    private static final int EXCOM2_HEIGHT = 250;
    private static final int EXCOM3_X = 536;
    private static final int EXCOM3_Y = 208;
    private static final int EXCOM3_WIDTH = 136;
    private static final int EXCOM3_HEIGHT = 282;
    private static final int CUBE0_X = 372;
    private static final int CUBE_Y = 96;
    private static final int CUBE_WIDTH = 24;
    private static final int CUBE_HEIGHT = 24;
    private static final int CUBE1_X = 460;
    private static final int CUBE2_X = 540;
    private static final int COUNCIL_PAWN_X = 894;
    private static final int PAWN_WIDTH = 29;
    private static final int PAWN_HEIGHT = 43;
    private static final int COUNCIL_PAWN_Y = 128;
    private static final int PRODUCTION_PAWN_X = 64;
    private static final int PRODUCTION_PAWN_Y = 810;
    private static final int COVER_BIG_X = 200;
    private static final int COVER_BIG_Y = 756;
    private static final int COVER_BIG_WIDTH = 400;
    private static final int COVER_BIG_HEIGHT = 184;
    private static final int LARGEPRODUCTION_PAWN_X = 310;
    private static final int LARGEPRODUCTION_PAWN_Y = 794;
    private static final int COVER_BIG2_X = 200;
    private static final int COVER_BIG2_Y = 970;
    private static final int HARVEST_PAWN_X = 64;
    private static final int HARVEST_PAWN_Y = 1042;
    private static final int LARGEHARVEST_PAWN_X = 310;
    private static final int LARGEHARVEST_PAWN_Y = 1012;
    private static final int COVER_LITTLE_X = 1220;
    private static final int COVER_LITTLE_Y = 762;
    private static final int COVER_LITTLE_WIDTH = 170;
    private static final int COVER_LITTLE_HEIGHT = 168;
    private static final int COVER_LITTLE2_X = 1340;
    private static final int COVER_LITTLE2_Y = 890;
    private static final int COIN_PAWN_X = 994;
    private static final int COIN_PAWN_Y = 772;
    private static final int SERVANTS_PAWN_X = 1164;
    private static final int SERVANTS_PAWN_Y = 776;
    private static final int MILITARY_PAWN_X = 1250;
    private static final int MILITARY_PAWN_Y = 838;
    private static final int COUNCIL_SPOT_PAWN_X = 1374;
    private static final int COUNCIL_SPOT_PAWN_Y = 966;
    private static final int FAITH_MARKER_X = 12;
    private static final int FAITH_MARKER_Y = 568;
    private static final int FAITH_MARKER_WIDTH = 24;
    private static final int FAITH_MARKET_HEIGHT = 32;
    private static final int MARKER_TURN_X = 1372;
    private static final int MARKER_TURN1_Y = 32;
    private static final int MARKER_TURN2_Y = 138;
    private static final int MARKER_TURN3_Y = 234;
    private static final int MARKER_TURN4_Y = 326;
    private static final int MARKER_TURN5_Y = 420;
    private static final int MARKER_TURN_WIDTH = 50;
    private static final int MARKER_TURN_HEIGHT = 50;
    private static final int PRODUCTION_AREA_X = 14;
    private static final int PRODUCTION_AREA_Y = 766;
    private static final int PRODUCTION_AREA_WIDTH = 142;
    private static final int PRODUCTION_AREA_HEIGHT = 132;
    private static final int HARVEST_AREA_X = 8;
    private static final int HARVEST_AREA_Y = 986;
    private static final int COIN_SPOT_X = 896;
    private static final int COIN_SPOT_Y = 720;
    private static final int SERVANTS_SPOT_X = 1072;
    private static final int SERVANTS_SPOT_Y = 720;
    private static final int MILITARY_SPOT_X = 1228;
    private static final int MILITARY_SPOT_Y = 720;
    private static final int COUNCIL_SPOT_X = 1362;
    private static final int COUNCIL_SPOT_Y = 896;
    private static final int LARGEPRODUCTION_AREA_X = 218;
    private static final int LARGEPRODUCTION_AREA_Y = 766;
    private static final int LARGEPRODUCTION_AREA_WIDTH = 374;
    private static final int LARGEPRODUCTION_AREA_HEIGHT = 136;
    private static final int LARGEHARVEST_AREA_X = 218;
    private static final int LARGEHARVEST_AREA_Y = 986;
    private static final int COUNCIL_PALACE_X = 766;
    private static final int COUNCIL_PALACE_Y = 70;
    private static final int COUNCIL_PALACE_WIDTH = 481;
    private static final int COUNCIL_PALACE_HEIGHT = 204;
    private static final int BLACK_DICE_X = 926;
    private static final int BLACK_DICE_Y = 1090;
    private static final int WHITE_DICE_X = 1104;
    private static final int WHITE_DICE_Y = 1090;
    private static final int ORANGE_DICE_X = 1276;
    private static final int ORANGE_DICE_Y = 1090;
    private static final String BCKG_IMG = "secondBoard.jpeg";
    private static final String EXCOM_1 = "excom1";
    private static final String EXCOM_2 = "excom2";
    private static final String EXCOM_3 = "excom3";
    private static final String CUBE0 = "cube0";
    private static final String CUBE1 = "cube1";
    private static final String CUBE2 = "cube2";
    private static final String COUNCIL_PAWN = "councilPawn";
    private static final String PRODUCTION_PAWN = "productionPawn";
    private static final String LARGE_PRODUCTION_PAWN = "largeProductionPawn";
    private static final String HARVEST_PAWN = "harvestPawn";
    private static final String LARGE_HARVEST_PAWN = "largeHarvestPawn";
    private static final String COIN_PAWN = "coinPawn";
    private static final String SERVANTS_PAWN = "servantsPawn";
    private static final String MILITARY_PAWN = "militaryPawn";
    private static final String COUNCIL_SPOT_PAWN = "councilSpotPawn";
    private static final String TURN = "turn";
    private static final String MARKER = "marker";
    private static final String COVER_BIG = "coverBig";
    private static final String COVER_BIG_2 = "coverBig2";
    private static final String COVER_LITTLE = "coverLittle";
    private static final String COVER_LITTLE_2 = "coverLittle2";
    private static final String TURN_1 = "turn1";
    private static final String TURN_2 = "turn2";
    private static final String TURN_3 = "turn3";
    private static final String TURN_4 = "turn4";
    private static final String PRODUCTION_AREA = "PRODUCTION_AREA";
    private static final String HARVEST_AREA = "HARVEST_AREA";
    private static final String COIN_SPOT = "COIN_SPOT";
    private static final String SERVANTS_SPOT = "SERVANTS_SPOT";
    private static final String MILITARY_SPOT = "MILITARY_SPOT";
    private static final String COUNCIL_SPOT = "COUNCIL_SPOT";
    private static final String LARGEPRODUCTION_AREA = "LARGEPRODUCTION_AREA";
    private static final String LARGEHARVEST_AREA = "LARGEHARVEST_AREA";
    private static final String COUNCIL_PALACE = "COUNCIL_PALACE";
    private static final String COVER_BIG_IMG = "coverBig.png";
    private static final String COVER_BIG2_IMG = "coverBig2.png";
    private static final String COVER_LITTLE_IMG = "coverLittle.png";
    private static final String COVER_LITTLE2_IMG = "coverLittle.png";
    private static final String TURN_5 = "turn5";

    private int blackValue;
    private int orangeValue;
    private int whiteValue;

    private int numPlayers;

    /**
     * Creates a new SecondBoard given the number of players in the game.
     * @param numPlayers the number of players.
     */
    SecondBoard(int numPlayers) {
        super(BCKG_IMG);
        this.numPlayers = numPlayers;
        fill();
        setCoverTiles();
    }


    /**
     * Updates the dices values.
     * @param board the game board.
     */
    void setDicesValues(Board board) {
        blackValue = board.getDicesBox().getDice(DiceColor.BLACK).getValue();
        orangeValue = board.getDicesBox().getDice(DiceColor.ORANGE).getValue();
        whiteValue = board.getDicesBox().getDice(DiceColor.WHITE).getValue();
    }

    /**
     * Sets the excomunication tiles images.
     * @param board the game board.
     */
    void setExcomunicationTiles(Board board) {
        imageHandler.getImagesByName().get(EXCOM_1).setImage(GameInfoGui.getExcomunicationTile(board.getVatican().getFirstVatican()));
        imageHandler.getImagesByName().get(EXCOM_2).setImage(GameInfoGui.getExcomunicationTile(board.getVatican().getSecondVatican()));
        imageHandler.getImagesByName().get(EXCOM_3).setImage(GameInfoGui.getExcomunicationTile(board.getVatican().getThirdVatican()));
    }

    /**
     * Updates the excomunication cubes images.
     * @param game the game board.
     */
    void setupCubes(Game game) {
        for (Player player: game.getPlayers()) {

            if (player.getReport()[0] == 1) {
                imageHandler.getImagesByName().get(CUBE0 + GameInfoGui.getPlayerNumber(player)).setImage(GameInfoGui.getCube(player));
            }

            if (player.getReport()[1] == 1) {
                imageHandler.getImagesByName().get(CUBE1 + GameInfoGui.getPlayerNumber(player)).setImage(GameInfoGui.getCube(player));
            }

            if (player.getReport()[2] == 1) {
                imageHandler.getImagesByName().get(CUBE2 + GameInfoGui.getPlayerNumber(player)).setImage(GameInfoGui.getCube(player));
            }
        }


    }

    /**
     * Updates the pawn images in the council palace.
     * @param board the game board.
     */
    void setCouncilPawns(Board board) {
        for (int i = 0; i < 20; i++) {
            imageHandler.getImagesByName().get(COUNCIL_PAWN + i).setImage(GameInfoGui.getPawnfromList(board.getCouncilPalace().getPawns(), i));
        }
    }


    /**
     * Updates the pawn images in the production space.
     * @param board the game board.
     */
    void setProductionPawns(Board board) {
        imageHandler.getImagesByName().get(PRODUCTION_PAWN).setImage(GameInfoGui.getPawn(board.getProductionSpace().getProductionArea()));

        if (numPlayers > 2 && board.getProductionSpace().getLargeProductionArea() != null) {
            for (int i = 0; i < 20; i++) {
                imageHandler.getImagesByName().get(LARGE_PRODUCTION_PAWN + i).setImage(GameInfoGui.getPawnfromList(board.getProductionSpace().getLargeProductionArea().getPawns(), i));
            }
        }


    }

    /**
     * Updates the pawn images in the harvest space.
     * @param board the game board.
     */
    void setHarvestPawns(Board board) {
        imageHandler.getImagesByName().get(HARVEST_PAWN).setImage(GameInfoGui.getPawn(board.getHarvestSpace().getHarvestArea()));

        if (numPlayers > 2 && board.getHarvestSpace().getLargeHarvestArea() != null) {
            for (int i = 0; i < 20; i++) {
                imageHandler.getImagesByName().get(LARGE_HARVEST_PAWN + i).setImage(GameInfoGui.getPawnfromList(board.getHarvestSpace().getLargeHarvestArea().getPawns(), i));
            }
        }


    }

    /**
     * Updates the pawn images in the market.
     * @param board the game board.
     */
    public void setMarket(Board board) {

        imageHandler.getImagesByName().get(COIN_PAWN).setImage(GameInfoGui.getPawn(board.getMarket().getCoinSpot()));
        imageHandler.getImagesByName().get(SERVANTS_PAWN).setImage(GameInfoGui.getPawn(board.getMarket().getServantSpot()));


        if (numPlayers >= 4 && board.getMarket().getMilitarySpot() != null) {
            imageHandler.getImagesByName().get(MILITARY_PAWN).setImage(GameInfoGui.getPawn(board.getMarket().getMilitarySpot()));
        }
        if (numPlayers >= 4 && board.getMarket().getCouncilSpot() != null) {
            imageHandler.getImagesByName().get(COUNCIL_SPOT_PAWN).setImage(GameInfoGui.getPawn(board.getMarket().getCouncilSpot()));
        }
    }

    /**
     * Updates the marker images in the turn track order.
     * @param game the game.
     */
    void setTurnTrack(Game game) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            imageHandler.getImagesByName().get(TURN + (i+1)).setImage(GameInfoGui.getMarker(game.getPlayers().get(i)));
        }
    }


    /**
     * Updates the marker images in the faith track order.
     * @param game the game.
     */
    void setFaithTrack(Game game) {

        setRectanglesFaithTrack();

        for (Player player: game.getPlayers()) {
            imageHandler.getImagesByName().get(MARKER + GameInfoGui.getFaithTrackPosition(player)).setImage(GameInfoGui.getMarker(player));
        }
    }

    /**
     * Adds the rectangles of the excomunication tiles to the image handler.
     */
    private void setupRectanglesExcomunication() {
        imageHandler.getImagesByName().put(EXCOM_1, new Image(null, null, new Rectangle(EXCOM1_X, EXCOM1_Y, EXCOM1_WIDTH, EXCOM1_HEIGHT)));
        imageHandler.getImagesByName().put(EXCOM_2, new Image(null, null, new Rectangle(EXCOM2_X, EXCOM2_Y, EXCOM2_WIDTH, EXCOM2_HEIGHT)));
        imageHandler.getImagesByName().put(EXCOM_3, new Image(null, null, new Rectangle(EXCOM3_X, EXCOM3_Y, EXCOM3_WIDTH, EXCOM3_HEIGHT)));

        int offset = 0;
        for (int i = 0; i < 5; i++) {
            imageHandler.getImagesByName().put(CUBE0+ i, new Image(null, null, new Rectangle(CUBE0_X, CUBE_Y +offset, CUBE_WIDTH, CUBE_HEIGHT)));
            offset += 28;
        }

        offset = 0;
        for (int i = 0; i < 5; i++) {
            imageHandler.getImagesByName().put(CUBE1+ i, new Image(null, null, new Rectangle(CUBE1_X, CUBE_Y+offset, CUBE_WIDTH, CUBE_HEIGHT)));
            offset += 28;
        }

        offset = 0;
        for (int i = 0; i < 5; i++) {
            imageHandler.getImagesByName().put(CUBE2+ i, new Image(null, null, new Rectangle(CUBE2_X, CUBE_Y+offset, CUBE_WIDTH,  CUBE_HEIGHT)));
            offset += 28;
        }

    }

    /**
     * Adds the rectangles of the council pawns to the image handler.
     */
    private void setupRectanglesCouncil() {
        int offset = 0;
        for (int i = 0; i < 8; i ++) {
            imageHandler.getImagesByName().put(COUNCIL_PAWN + i, new Image(null, null, new Rectangle(COUNCIL_PAWN_X +offset, COUNCIL_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
            offset += 30;
        }

        offset = 0;
        for (int i = 8; i < 16; i ++) {
            imageHandler.getImagesByName().put(COUNCIL_PAWN + i, new Image(null, null, new Rectangle(COUNCIL_PAWN_X +offset, COUNCIL_PAWN_Y + PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
            offset += 30;
        }

        offset = 0;
        for (int i = 16; i < 20; i++) {
            imageHandler.getImagesByName().put(COUNCIL_PAWN + i, new Image(null, null, new Rectangle(COUNCIL_PAWN_X +offset, COUNCIL_PAWN_Y + 2*PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
            offset += 30;
        }
    }

    /**
     * Adds the rectangles of the production pawns to the image handler.
     */
    private void setRectanglesProduction() {
        imageHandler.getImagesByName().put(PRODUCTION_PAWN, new Image(null, null, new Rectangle(PRODUCTION_PAWN_X, PRODUCTION_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));

        if (numPlayers == 2) {
            imageHandler.getImagesByName().put(COVER_BIG, new Image(null, null, new Rectangle(COVER_BIG_X, COVER_BIG_Y, COVER_BIG_WIDTH, COVER_BIG_HEIGHT)));
        }

        if (numPlayers > 2) {
            int offset = 0;
            for (int i = 0; i < 8; i++) {
                imageHandler.getImagesByName().put(LARGE_PRODUCTION_PAWN + i, new Image(null, null, new Rectangle(LARGEPRODUCTION_PAWN_X + offset, LARGEPRODUCTION_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }


            offset = 0;
            for (int i = 16; i < 20; i++) {
                imageHandler.getImagesByName().put(LARGE_PRODUCTION_PAWN + i, new Image(null, null, new Rectangle(LARGEPRODUCTION_PAWN_X + offset, LARGEPRODUCTION_PAWN_Y + 2*PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }

            offset = 0;
            for (int i = 8; i < 16; i++) {
                imageHandler.getImagesByName().put(LARGE_PRODUCTION_PAWN + i, new Image(null, null, new Rectangle(LARGEPRODUCTION_PAWN_X + offset, LARGEPRODUCTION_PAWN_Y + PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }





        }
    }

    /**
     * Adds the rectangles of the harvest pawns to the image handler.
     */
    private void setRectanglesHarvest() {
        if (numPlayers == 2) {
            imageHandler.getImagesByName().put(COVER_BIG_2, new Image(null, null, new Rectangle(COVER_BIG2_X, COVER_BIG2_Y, COVER_BIG_WIDTH, COVER_BIG_HEIGHT)));
        }
        imageHandler.getImagesByName().put(HARVEST_PAWN, new Image(null, null, new Rectangle(HARVEST_PAWN_X, HARVEST_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));


        if (numPlayers > 2) {
            int offset = 0;
            for (int i = 8; i < 16; i++) {
                imageHandler.getImagesByName().put(LARGE_HARVEST_PAWN + i, new Image(null, null, new Rectangle(LARGEHARVEST_PAWN_X + offset, LARGEHARVEST_PAWN_Y + PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }

            offset = 0;
            for (int i = 0; i < 8; i++) {
                imageHandler.getImagesByName().put(LARGE_HARVEST_PAWN + i, new Image(null, null, new Rectangle(LARGEHARVEST_PAWN_X + offset, LARGEHARVEST_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }

            offset = 0;
            for (int i = 16; i < 20; i++) {
                imageHandler.getImagesByName().put(LARGE_HARVEST_PAWN + i, new Image(null, null, new Rectangle(LARGEHARVEST_PAWN_X + offset, LARGEHARVEST_PAWN_Y + 2*PAWN_HEIGHT, PAWN_WIDTH, PAWN_HEIGHT)));
                offset += 30;
            }


        }
    }

    /**
     * Adds the rectangles of the market pawns to the image handler.
     */
    private void setRectanglesMarket() {
        if (numPlayers < 4) {
            imageHandler.getImagesByName().put(COVER_LITTLE, new Image(null, null, new Rectangle(COVER_LITTLE_X, COVER_LITTLE_Y, COVER_LITTLE_WIDTH, COVER_LITTLE_HEIGHT)));
            imageHandler.getImagesByName().put(COVER_LITTLE_2, new Image(null, null, new Rectangle(COVER_LITTLE2_X, COVER_LITTLE2_Y, COVER_LITTLE_WIDTH, COVER_LITTLE_HEIGHT)));
        }

        imageHandler.getImagesByName().put(COIN_PAWN, new Image(null, null, new Rectangle(COIN_PAWN_X, COIN_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        imageHandler.getImagesByName().put(SERVANTS_PAWN, new Image(null, null, new Rectangle(SERVANTS_PAWN_X, SERVANTS_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));

        if (numPlayers >=4 ) {
            imageHandler.getImagesByName().put(MILITARY_PAWN, new Image(null, null, new Rectangle(MILITARY_PAWN_X, MILITARY_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
            imageHandler.getImagesByName().put(COUNCIL_SPOT_PAWN, new Image(null, null, new Rectangle(COUNCIL_SPOT_PAWN_X, COUNCIL_SPOT_PAWN_Y, PAWN_WIDTH, PAWN_HEIGHT)));
        }
    }

    /**
     * Adds the rectangles of the faith track markers to the image handler.
     */
    private void setRectanglesFaithTrack() {
        int offset = 0;
        int offset2 = 0;
        int offset3 = 20;
        for (int k = 0; k < 16; k++) {


            for (int i = 0; i < 5; i++) {
                imageHandler.getImagesByName().put(MARKER +k + i, new Image(null, null, new Rectangle(FAITH_MARKER_X + offset + offset2 +
                        +(k>=3 ? offset3 : 0) - (k>=12 ? 8: 0), FAITH_MARKER_Y, FAITH_MARKER_WIDTH, FAITH_MARKET_HEIGHT)));
                offset += 15;
            }

            if (k >= 3 && k<=5) {
                offset2 += 10;
                offset3 += 25;
            }
            offset2 += 86;
            offset = 0;
        }
    }

    /**
     * Adds the rectangles of the turn track markers to the image handler.
     */
    private void setRectanglesTurnTrack() {
        imageHandler.getImagesByName().put(TURN_1, new Image(null, null, new Rectangle(MARKER_TURN_X, MARKER_TURN1_Y, MARKER_TURN_WIDTH, MARKER_TURN_HEIGHT)));
        imageHandler.getImagesByName().put(TURN_2, new Image(null, null, new Rectangle(MARKER_TURN_X, MARKER_TURN2_Y, MARKER_TURN_WIDTH, MARKER_TURN_HEIGHT)));
        imageHandler.getImagesByName().put(TURN_3, new Image(null, null, new Rectangle(MARKER_TURN_X, MARKER_TURN3_Y, MARKER_TURN_WIDTH, MARKER_TURN_HEIGHT)));
        imageHandler.getImagesByName().put(TURN_4, new Image(null, null, new Rectangle(MARKER_TURN_X, MARKER_TURN4_Y, MARKER_TURN_WIDTH, MARKER_TURN_HEIGHT)));
        imageHandler.getImagesByName().put(TURN_5, new Image(null, null, new Rectangle(MARKER_TURN_X, MARKER_TURN5_Y, MARKER_TURN_WIDTH, MARKER_TURN_HEIGHT)));
    }

    /**
     * Adds the rectangles of the covers to the image handler.
     */
    private void setCoverTiles() {
        if (numPlayers == 2) {
            imageHandler.getImagesByName().get(COVER_BIG).setImage(COVER_BIG_IMG);
            imageHandler.getImagesByName().get(COVER_BIG_2).setImage(COVER_BIG2_IMG);
        }
        if (numPlayers < 4) {
            imageHandler.getImagesByName().get(COVER_LITTLE).setImage(COVER_LITTLE_IMG);
            imageHandler.getImagesByName().get(COVER_LITTLE_2).setImage(COVER_LITTLE2_IMG);
        }

    }

    /**
     * Adds the rectangles of the clicks to the image handler.
     */
    private void setClickRectangles() {
        imageHandler.getImagesByName().put(PRODUCTION_AREA, new Image(null, null, new Rectangle(PRODUCTION_AREA_X, PRODUCTION_AREA_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));
        imageHandler.getImagesByName().put(HARVEST_AREA, new Image(null, null, new Rectangle(HARVEST_AREA_X, HARVEST_AREA_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));

        imageHandler.getImagesByName().put(COIN_SPOT, new Image(null, null, new Rectangle(COIN_SPOT_X, COIN_SPOT_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));
        imageHandler.getImagesByName().put(SERVANTS_SPOT, new Image(null, null, new Rectangle(SERVANTS_SPOT_X, SERVANTS_SPOT_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));

        if (numPlayers >= 4) {
            imageHandler.getImagesByName().put(MILITARY_SPOT, new Image(null, null, new Rectangle(MILITARY_SPOT_X, MILITARY_SPOT_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));
            imageHandler.getImagesByName().put(COUNCIL_SPOT, new Image(null, null, new Rectangle(COUNCIL_SPOT_X, COUNCIL_SPOT_Y, PRODUCTION_AREA_WIDTH, PRODUCTION_AREA_HEIGHT)));
        }

        if (numPlayers > 2) {
            imageHandler.getImagesByName().put(LARGEPRODUCTION_AREA, new Image(null, null, new Rectangle(LARGEPRODUCTION_AREA_X, LARGEPRODUCTION_AREA_Y, LARGEPRODUCTION_AREA_WIDTH, LARGEPRODUCTION_AREA_HEIGHT)));
            imageHandler.getImagesByName().put(LARGEHARVEST_AREA, new Image(null, null, new Rectangle(LARGEHARVEST_AREA_X, LARGEHARVEST_AREA_Y, LARGEPRODUCTION_AREA_WIDTH, LARGEPRODUCTION_AREA_HEIGHT)));
        }

        imageHandler.getImagesByName().put(COUNCIL_PALACE, new Image(null, null, new Rectangle(COUNCIL_PALACE_X, COUNCIL_PALACE_Y, COUNCIL_PALACE_WIDTH, COUNCIL_PALACE_HEIGHT)));
    }


    /**
     * Sets up all rectangles to the image handler.
     */
    private void fill() {
        setupRectanglesExcomunication();
        setupRectanglesCouncil();
        setRectanglesProduction();
        setRectanglesHarvest();
        setRectanglesMarket();
        setRectanglesTurnTrack();
        setRectanglesFaithTrack();
        setClickRectangles();
    }

    @Override
    protected void fillImageHandler() {
        // in this case nothing is filled in the constructor
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(253, 253, 255));
        g.drawString(String.valueOf(blackValue), (int)(BLACK_DICE_X *getScaleX()), (int)(BLACK_DICE_Y *getScaleY()));

        g.setColor(new Color(0,0,0));
        g.drawString(String.valueOf(whiteValue),  (int)(WHITE_DICE_X *getScaleX()), (int)(WHITE_DICE_Y *getScaleY()));

        g.setColor(new Color(0,0,0));
        g.drawString(String.valueOf(orangeValue), (int)(ORANGE_DICE_X *getScaleX()), (int)(ORANGE_DICE_Y *getScaleY()));
    }
    
}
