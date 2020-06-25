package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.Player;

/**
 * This class represents a fight spot in the fight space.
 */
public class FightSpot extends ActionPlace implements Serializable {

    private static final long serialVersionUID = 1714081448507270596L;

    private Player player;
    private int servantsSpent;

    /**
     * Creates a new FightSpot for the player.
     * @param player the player.
     */
    FightSpot(Player player) {
        super(null, 0);
        this.player = player;
    }

    /**
     * @return the battle points for this fight spot.
     */
    public int getBattlePoints() {
        return getServantsSpent() + getPawns().stream().mapToInt(Pawn::getValue).sum() + player.getMilitaryPoints();
    }

    /**
     * @return the player of the fight spot.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the servants spent by the player.
     * @param servantsSpent the number of servants to set.
     */
    public void setServantsSpent(int servantsSpent) {
        this.servantsSpent = servantsSpent;
    }

    /**
     * Adds the servants spent by the player.
     * @param servantsSpent the number of servants to add.
     */
    public void addServantsSpent(int servantsSpent) {
        this.servantsSpent += servantsSpent;
    }

    /**
     * @return the servants spent.
     */
    public int getServantsSpent() {
        return servantsSpent;
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.FIGHT_SPOT;
    }

}
