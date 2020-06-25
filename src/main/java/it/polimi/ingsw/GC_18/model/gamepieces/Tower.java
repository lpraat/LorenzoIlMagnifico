package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;

/**
 * This class represents a generic tower in the board.
 */
public abstract class Tower<T extends DevelopmentCard> implements Serializable {
    
    private static final long serialVersionUID = -8967063113886857579L;
    
    ArrayList<Floor<T>> floors;

    /**
     * Creates a new tower.
     */
    Tower() {
        floors = new ArrayList<>();
    }

    /**
     * @return the first floor.
     */
    public Floor<T> getFirstFloor() {
        return floors.get(0);
    }

    /**
     * @return the second floor.
     */
    public Floor<T> getSecondFloor() {
        return floors.get(1);
    }

    /**
     * @return the third floor.
     */
    public Floor<T> getThirdFloor() {
        return floors.get(2);
    }

    /**
     * @return the fourth floor.
     */
    public Floor<T> getFourthFloor() {
        return floors.get(3);
    }

    /**
     * @return true if the tower contains a pawn, false otherwise.
     */
    public boolean containsPawn() {
        for (Floor<T> floor : floors) {
            if (floor.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param p the player to check.
     * @return true if a player can place another pawn in this tower.
     */
    public boolean containsPlayerPawn(Player p) {
        int count = 0;
        for (Floor<T> floor : floors) {
            List<Pawn> pawns = floor.getPawns();
            for (Pawn pawn : pawns) {
                if (pawn.getPlayer() == p && pawn.getColor() != DiceColor.NEUTRAL) {
                    count++;
                }
            }
        }
        return count >= 1;
    }

    /**
     * @return the tower color.
     */
    public abstract TowerColor getColor();

}
