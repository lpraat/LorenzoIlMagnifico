package it.polimi.ingsw.GC_18.server.controller;

import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.LeaderStatus;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This interfaces represents the parser used by the game controller for checking the input from the clients.
 */
public interface Parser {

    /**
     * @param player the player that chooses the effect.
     * @param onceNum the index number of the once per turn effect.
     * @return the once per turn effect choose by the player
     * @throws IllegalArgumentException if the index is not valid.
     */
    static OncePerTurnEffect parseOnceEffect(Player player, String onceNum) {
        try {
            if (Integer.parseInt(onceNum) <= player.getPersonalBoard().getOncePerTurnEffects().size()
                    && Integer.parseInt(onceNum) > 0) {
                return player.getPersonalBoard().getOncePerTurnEffects().get(Integer.parseInt(onceNum) - 1);
            } else {
                throw new IllegalArgumentException("Invalid once effect");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid once effect");
        }
    }

    /**
     * @param player the player that chooses the leader.
     * @param leaderNum the index number of the leader card.
     * @param leaderStatus the status of the leader.
     * @return the leader choose by the player.
     * @throws IllegalArgumentException if the index is not valid.
     */
    static Leader parseLeader(Player player, String leaderNum, LeaderStatus leaderStatus) {
        List<Leader> leaders;
        if (LeaderStatus.HAND.equals(leaderStatus)) {
            leaders = player.getPersonalBoard().getLeadersHand();
        } else {
            leaders = player.getPersonalBoard().getLeadersPlayed();
        }
        try {
            if (Integer.parseInt(leaderNum) <= leaders.size() &&
                    Integer.parseInt(leaderNum) > 0) {
                return leaders.get(Integer.parseInt(leaderNum) - 1);
            } else {
                throw new IllegalArgumentException("Invalid Leader");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Leader");
        }
    }

    /**
     * @param player the player that adds the servants value.
     * @param servantsSpent the servants spent.
     * @return the servants spent.
     * @throws IllegalArgumentException if the servants number is not valid.
     */
    static int parseServants(Player player, String servantsSpent) {
        try {
            if (GameUtils.compareServants(player, Integer.parseInt(servantsSpent))) {
                return Integer.parseInt(servantsSpent);
            } else {
                throw new IllegalArgumentException("Invalid servants");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid servants");
        }
    }

    /**
     * @param player the player that places the pawn.
     * @param pawnColor the pawn color.
     * @return the pawn to place.
     * @throws IllegalArgumentException if the pawn color is not valid.
     */
    static Pawn parsePawn(Player player, String pawnColor) {
        if (player.getPawn(pawnColor) != null) {
            return player.getPawn(pawnColor);
        } else {
            throw new IllegalArgumentException("Invalid pawn");
        }
    }

    /**
     * @param player the player that place the pawn in an action place.
     * @param actionPlaceArg the action place.
     * @return the action place.
     * @throws IllegalArgumentException if the action place is not valid.
     */
    static ActionPlace parseActionPlace(Player player, String actionPlaceArg) {
        String actionPlace = actionPlaceArg.toUpperCase();
        switch (actionPlace) {
            case "CHARACTER1":
                return player.getController().getGame().getBoard().getCharacterTower().getFirstFloor();
            case "CHARACTER2":
                return player.getController().getGame().getBoard().getCharacterTower().getSecondFloor();
            case "CHARACTER3":
                return player.getController().getGame().getBoard().getCharacterTower().getThirdFloor();
            case "CHARACTER4":
                return player.getController().getGame().getBoard().getCharacterTower().getFourthFloor();
            case "BUILDING1":
                return player.getController().getGame().getBoard().getBuildingTower().getFirstFloor();
            case "BUILDING2":
                return player.getController().getGame().getBoard().getBuildingTower().getSecondFloor();
            case "BUILDING3":
                return player.getController().getGame().getBoard().getBuildingTower().getThirdFloor();
            case "BUILDING4":
                return player.getController().getGame().getBoard().getBuildingTower().getFourthFloor();
            case "TERRITORY1":
                return player.getController().getGame().getBoard().getTerritoryTower().getFirstFloor();
            case "TERRITORY2":
                return player.getController().getGame().getBoard().getTerritoryTower().getSecondFloor();
            case "TERRITORY3":
                return player.getController().getGame().getBoard().getTerritoryTower().getThirdFloor();
            case "TERRITORY4":
                return player.getController().getGame().getBoard().getTerritoryTower().getFourthFloor();
            case "VENTURE1":
                return player.getController().getGame().getBoard().getVentureTower().getFirstFloor();
            case "VENTURE2":
                return player.getController().getGame().getBoard().getVentureTower().getSecondFloor();
            case "VENTURE3":
                return player.getController().getGame().getBoard().getVentureTower().getThirdFloor();
            case "VENTURE4":
                return player.getController().getGame().getBoard().getVentureTower().getFourthFloor();
            case "COIN_SPOT":
                return player.getController().getGame().getBoard().getMarket().getCoinSpot();
            case "SERVANTS_SPOT":
                return player.getController().getGame().getBoard().getMarket().getServantSpot();
            case "MILITARY_SPOT":
                return player.getController().getGame().getBoard().getMarket().getMilitarySpot();
            case "COUNCIL_SPOT":
                return player.getController().getGame().getBoard().getMarket().getCouncilSpot();
            case "HARVEST_AREA":
                return player.getController().getGame().getBoard().getHarvestSpace().getHarvestArea();
            case "LARGEHARVEST_AREA":
                return player.getController().getGame().getBoard().getHarvestSpace().getLargeHarvestArea();
            case "PRODUCTION_AREA":
                return player.getController().getGame().getBoard().getProductionSpace().getProductionArea();
            case "LARGEPRODUCTION_AREA":
                return player.getController().getGame().getBoard().getProductionSpace().getLargeProductionArea();
            case "COUNCIL_PALACE":
                return player.getController().getGame().getBoard().getCouncilPalace();
            case "FIGHT_SPOT":
                if (player.getController().getGame().getBoard().getFightSpace() != null) {
                    return player.getController().getGame().getBoard().getFightSpace().getPlayerSpot(player);
                } else {
                    throw new IllegalArgumentException("Expansion pack only available in 5 player mode");
                }
            default:
                throw new IllegalArgumentException("Invalid ActionPlace");
        }
    }

}
