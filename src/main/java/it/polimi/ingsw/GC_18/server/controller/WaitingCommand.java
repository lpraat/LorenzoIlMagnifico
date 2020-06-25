package it.polimi.ingsw.GC_18.server.controller;

import it.polimi.ingsw.GC_18.model.TowerColor;

/**
 * This enum represents all the possible waiting commands.
 * Waiting command is the command the client has to send in order to complete an interaction. It is set inside
 * the player controller.
 */
public enum WaitingCommand {
    ASK_ACTIVATION("ASK_ACTIVATION"), EXCHANGE_OR("EXCHANGE_OR"), COUNCIL_PRIVILEGES("COUNCIL_PRIVILEGES"), FLOORCHANGE_OR("FLOORCHANGE_OR"),
    EXTRA_PRODUCTION("EXTRA_PRODUCTION"), EXTRA_HARVEST("EXTRA_HARVEST"), EXTRA_BUILDING("EXTRA_BUILDING"),
    EXTRA_CHARACTER("EXTRA_CHARACTER"), EXTRA_TERRITORY("EXTRA_TERRITORY"), EXTRA_VENTURE("EXTRA_VENTURE"),
    EXTRA_ANY("EXTRA_ANY"), VENTURE_COST("VENTURE_COST"), PAWN_CHOOSE("PAWN_CHOOSE"), LEADER_COPY("LEADER_COPY"),
    ASK_SUPPORT("ASK_SUPPORT"), CHOOSE_LEADER("CHOOSE_LEADER");

    private String name;

    /**
     * Creates a new WaitingCommand.
     * @param name the name of the waiting command.
     */
    WaitingCommand(String name) {
        this.name = name;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param towerColor the tower color.
     * @return the extra floor pick waiting command for the tower color.
     */
    public static WaitingCommand getFloorCommand(TowerColor towerColor) {
        switch (towerColor) {
            case GREEN:
                return EXTRA_TERRITORY;
            case BLUE:
                return EXTRA_CHARACTER;
            case PURPLE:
                return EXTRA_VENTURE;
            case YELLOW:
                return EXTRA_BUILDING;
            case ANY:
                return EXTRA_ANY;
            default:
                return null;
        }

    }
}
