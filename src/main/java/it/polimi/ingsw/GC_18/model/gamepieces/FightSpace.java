package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.resources.FaithPoints;
import it.polimi.ingsw.GC_18.model.resources.Money;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Servants;
import it.polimi.ingsw.GC_18.model.resources.Stones;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.model.resources.Woods;

/**
 * This class represents the fight space in the game. Every fight space is made
 * of five spots.
 */
public class FightSpace implements Serializable {

    private static final long serialVersionUID = -1760362122645013255L;

    private List<FightSpot> fightSpots;
    private List<Player> players;

    /**
     * Creates a new FightSpace given the list of players.
     * @param playerList the list of players.
     */
    public FightSpace(List<Player> playerList) {
        fightSpots = new ArrayList<>();
        this.players = playerList;
        for (Player player: playerList) {
            fightSpots.add(new FightSpot(player));
        }
    }

    /**
     * @param player the player.
     * @return the fight spot of the player.
     */
    public FightSpot getPlayerSpot(Player player) {
        return fightSpots.stream()
                .filter(fightSpot -> fightSpot.getPlayer().equals(player))
                .collect(Collectors.toList()).get(0);
    }

    /**
     * @param round the round of the fight.
     * @param militaryPoints the military points.
     * @param totalBattlePoints the total battle points.
     * @param battlePoints the battle points of the fight spot.
     * @return the price.
     */
    private Resource getPrice(int round, int militaryPoints, int totalBattlePoints, int battlePoints) {
        int prizePart = (int)(militaryPoints*(battlePoints * 1d / totalBattlePoints));
        switch (round) {
        case 0:
            return new Money(prizePart);
        case 1:
            return new Servants(prizePart);
        case 2:
            return new Woods(prizePart);
        case 3:
            return new Stones(prizePart);
        case 4:
            return new FaithPoints(prizePart);
        case 5:
            return new VictoryPoints(prizePart);
        default:
            return null;
        }
    }

    /**
     * Reset the pawns and servants in every spot.
     */
    public void reset() {
        fightSpots.forEach(fightSpot -> {
            fightSpot.setPawns(new ArrayList<>());
            fightSpot.setServantsSpent(0);
        });
    }

    /**
     * Adds the fight area bonus to the players given the round.
     * @param round the round.
     */
    public void addBonus(int round) {
        int militaryPoints = players.stream().mapToInt(Player::getMilitaryPoints).sum();
        int totalBattlePoints = fightSpots.stream().mapToInt(fightSpot -> fightSpot.getServantsSpent()
                + fightSpot.getPawns().stream().mapToInt(Pawn::getValue).sum()).sum() + militaryPoints;
        for (FightSpot fightSpot: fightSpots) {
            if (!fightSpot.getPawns().isEmpty()) {
                Resource price = getPrice(round, militaryPoints, totalBattlePoints, fightSpot.getBattlePoints());
                if (price != null) {
                    price.addPlayer(fightSpot.getPlayer(), null);
                }
            }
        }
    }

    /**
     * @param round the round.
     * @return the round type of bonus.
     */
    public String getPriceType(int round) {
        switch (round) {
        case 0:
            return "money";
        case 1:
            return "servants";
        case 2:
            return "woods";
        case 3:
            return "stones";
        case 4:
            return "faith";
        case 5:
            return "victory";
        default:
            return null;
        }
    }

}
