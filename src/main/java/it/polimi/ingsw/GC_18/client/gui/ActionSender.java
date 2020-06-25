package it.polimi.ingsw.GC_18.client.gui;

import it.polimi.ingsw.GC_18.client.connection.ConnectionHandler;

/**
 * This class is used for realizing the clicks in the gui.
 */
class ActionSender {

    private static final String CHARACTER1 = "CHARACTER1";
    private static final String CHARACTER2 = "CHARACTER2";
    private static final String CHARACTER3 = "CHARACTER3";
    private static final String CHARACTER4 = "CHARACTER4";
    private static final String BUILDING1 = "BUILDING1";
    private static final String BUILDING2 = "BUILDING2";
    private static final String BUILDING3 = "BUILDING3";
    private static final String BUILDING4 = "BUILDING4";
    private static final String TERRITORY1 = "TERRITORY1";
    private static final String TERRITORY2 = "TERRITORY2";
    private static final String TERRITORY3 = "TERRITORY3";
    private static final String TERRITORY4 = "TERRITORY4";
    private static final String VENTURE1 = "VENTURE1";
    private static final String VENTURE2 = "VENTURE2";
    private static final String VENTURE3 = "VENTURE3";
    private static final String VENTURE4 = "VENTURE4";
    private static final String HARVEST_AREA = "HARVEST_AREA";
    private static final String PRODUCTION_AREA = "PRODUCTION_AREA";
    private static final String LARGEHARVEST_AREA = "LARGEHARVEST_AREA";
    private static final String LARGEPRODUCTION_AREA = "LARGEPRODUCTION_AREA";
    private static final String COIN_SPOT = "COIN_SPOT";
    private static final String SERVANTS_SPOT = "SERVANTS_SPOT";
    private static final String MILITARY_SPOT = "MILITARY_SPOT";
    private static final String COUNCIL_SPOT = "COUNCIL_SPOT";
    private static final String COUNCIL_PALACE = "COUNCIL_PALACE";
    private static final String FIGHT_SPOT = "FIGHT_SPOT";
    private static final String LARGE_HARVEST_PAWN = "largeHarvestPawn";
    private static final String COUNCIL_PAWN = "councilPawn";
    private static final String LARGE_PRODUCTION_PAWN = "largeProductionPawn";
    private static final String HARVEST_PAWN = "harvestPawn";
    private static final String PRODUCTION_PAWN = "productionPawn";
    private static final String COIN_PAWN = "coinPawn";
    private static final String SERVANTS_PAWN = "servantsPawn";
    private static final String MILITARY_PAWN = "militaryPawn";
    private static final String COUNCIL_SPOT_PAWN = "councilSpotPawn";
    private static final String PAWN1 = "pawn1";
    private static final String PAWN2 = "pawn2";
    private static final String PAWN3 = "pawn3";
    private static final String PAWN4 = "pawn4";
    private static final String PAWN5 = "pawn5";
    private static final String PAWN6 = "pawn6";
    private static final String PAWN7 = "pawn7";
    private static final String PAWN8 = "pawn8";
    private static final String PAWN9 = "pawn9";
    private static final String PAWN10 = "pawn10";
    private static final String PAWN11 = "pawn11";
    private static final String PAWN12 = "pawn12";
    private static final String PAWN13 = "pawn13";
    private static final String PAWN14 = "pawn14";
    private static final String PAWN15 = "pawn15";
    private static final String PAWN16 = "pawn16";


    /**
     * Hidden constructor.
     */
    private ActionSender() {
        // hide the constructor
    }
    /**
     *
     * @param leaderClick the leader clicked.
     * @param leaderAndResources the LeaderAndResources tab of the leader clicked.
     */
    static void sendLeaderCommand(String leaderClick, LeaderAndResources leaderAndResources) {
        int leaderNumber = Character.getNumericValue(leaderClick.charAt(leaderClick.length() - 1));

        if (leaderClick.toUpperCase().startsWith("HAND")) {
            new InteractionFrame("Do you want to activate or discard this leader?",
                    new String[]{"ACTIVATE", "DISCARD"}, false, "LEADER - " + leaderNumber);
        } else if (leaderClick.toUpperCase().startsWith("PLAYED")) {
            ConnectionHandler.outputToServer("LEADER - " + leaderAndResources.getOnce()[leaderNumber-1] + " - ACTIVATE_ONCE");
        }
    }


    /**
     * Creates a new interaction frame for the player that wants to do a place.
     * @param clicked the place clicked.
     */
    static void sendPlaceCommand(String clicked) {
        String adjustClick = parseActionClick(clicked);
        switch (adjustClick) {

            case CHARACTER1:
            case CHARACTER2:
            case CHARACTER3:
            case CHARACTER4:
            case BUILDING1:
            case BUILDING2:
            case BUILDING3:
            case BUILDING4:
            case TERRITORY1:
            case TERRITORY2:
            case TERRITORY3:
            case TERRITORY4:
            case VENTURE1:
            case VENTURE2:
            case VENTURE3:
            case VENTURE4:
            case HARVEST_AREA:
            case PRODUCTION_AREA:
            case LARGEHARVEST_AREA:
            case LARGEPRODUCTION_AREA:
            case COIN_SPOT:
            case SERVANTS_SPOT:
            case MILITARY_SPOT:
            case COUNCIL_SPOT:
            case COUNCIL_PALACE:
            case FIGHT_SPOT:


                new InteractionFrame("Choose a pawn and the servants for this action place",
                                 new String[]{"BLACK", "ORANGE", "WHITE", "NEUTRAL"}, 0, Integer.parseInt(PlayerScore.getUserScore().getServantsPoints()),
                                 false,
                                 "PLACE - " + adjustClick.toUpperCase());
            break;
            default:
                break;


        }


    }

    /**
     * @param clicked the image clicked.
     * @return true if the user clicked in the large harvest area.
     */
    private static boolean isLargeHarvestArea(String clicked) {
        return clicked.contains(LARGE_HARVEST_PAWN);
    }

    /**
     * @param clicked the image clicked.
     * @return true if the user clicked in the council palace.
     */
    private static boolean isCouncil(String clicked) {
        return clicked.contains(COUNCIL_PAWN);
    }

    /**
     * @param clicked the image clicked.
     * @return true if the user clicked in the large production area.
     */
    private static boolean isLargeProductionArea(String clicked) {
        return clicked.contains(LARGE_PRODUCTION_PAWN);
    }


    /**
     * @param clicked the image clicked.
     * @return the action places associated with the image clicked.
     */
    static String parseActionClick(String clicked) {

        if (HARVEST_AREA.equals(clicked) || HARVEST_PAWN.equals(clicked)) {
            return HARVEST_AREA;
        } else if (PRODUCTION_AREA.equals(clicked) || PRODUCTION_PAWN.equals(clicked)) {
            return PRODUCTION_AREA;
        } else if (LARGEHARVEST_AREA.equals(clicked) || isLargeHarvestArea(clicked)) {
            return LARGEHARVEST_AREA;
        } else if (LARGEPRODUCTION_AREA.equals(clicked) || isLargeProductionArea(clicked)) {
            return LARGEPRODUCTION_AREA;
        } else if (COIN_SPOT.equals(clicked) || COIN_PAWN.equals(clicked)) {
            return COIN_SPOT;
        } else if (SERVANTS_SPOT.equals(clicked) || SERVANTS_PAWN.equals(clicked)) {
            return SERVANTS_SPOT;
        } else if (MILITARY_SPOT.equals(clicked) || MILITARY_PAWN.equals(clicked)) {
            return MILITARY_SPOT;
        } else if (COUNCIL_SPOT.equals(clicked)|| COUNCIL_SPOT_PAWN.equals(clicked)) {
            return COUNCIL_SPOT;
        } else if (COUNCIL_PALACE.equals(clicked) || isCouncil(clicked)) {
            return COUNCIL_PALACE;
        } else if (FIGHT_SPOT.equals(clicked)) {
            return FIGHT_SPOT;
        } else {
            return parseTowerFloor(clicked);
        }
        }

    /**
     * @param clicked the image clicked.
     * @return the tower associated with the image clicked.
     */
    private static String parseTowerFloor(String clicked) {
        switch (clicked) {
            case PAWN1:
            case TERRITORY4:
                return TERRITORY4;

            case PAWN2:
            case TERRITORY3:
                return TERRITORY3;

            case PAWN3:
            case TERRITORY2:
                return TERRITORY2;

            case PAWN4:
            case TERRITORY1:
                return TERRITORY1;

            case PAWN5:
            case CHARACTER4:
                return CHARACTER4;

            case PAWN6:
            case CHARACTER3:
                return CHARACTER3;

            case PAWN7:
            case CHARACTER2:
                return CHARACTER2;

            case PAWN8:
            case CHARACTER1:
                return CHARACTER1;

            case PAWN9:
            case BUILDING4:
                return BUILDING4;

            case PAWN10:
            case BUILDING3:
                return BUILDING3;

            case PAWN11:
            case BUILDING2:
                return BUILDING2;

            case PAWN12:
            case BUILDING1:
                return BUILDING1;

            case PAWN13:
            case VENTURE4:
                return VENTURE4;

            case PAWN14:
            case VENTURE3:
                return VENTURE3;

            case PAWN15:
            case VENTURE2:
                return VENTURE2;

            case PAWN16:
            case VENTURE1:
                return VENTURE1;

            default:
                return "";
        }
    }


}
