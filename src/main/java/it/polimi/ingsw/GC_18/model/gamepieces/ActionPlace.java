package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represent a generic action place.
 */
public abstract class ActionPlace implements Serializable {
    
    private static final long serialVersionUID = -3520986100053599230L;
    
    ArrayList<Pawn> pawns;
    private int placeValue;
    private Resources placeBonus;

    /**
     * Creates a new action place by setting its place bonus and the value needed for placing a pawn in it.
     * @param placeBonus the place bonus.
     * @param placeValue the value needed.
     */
    ActionPlace(Resources placeBonus, int placeValue) {
        this.placeBonus = placeBonus;
        this.placeValue = placeValue;
        this.pawns = new ArrayList<>();
    }

    /**
     * @return the resources bonus given by this place.
     */
    public Resources getBonus() {
        return placeBonus;
    }

    /**
     * @return true if the place is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return !pawns.isEmpty();
    }

    /**
     * @return the value needed for placing a pawn in this action place.
     */
    public int getPlaceValue() {
        return placeValue;
    }

    /**
     * Places a pawn in the action place.
     * @param pawn the pawn placed.
     */
    public void setPawn(Pawn pawn) {
        pawns.add(pawn);
        pawn.setPlaced(true);
    }

    /**
     * @return the pawns in this place.
     */
    public List<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Sets the list of pawns in this place.
     * @param pawns the list to set.
     */
    public void setPawns(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }

    /**
     * @return the name of the place.
     */
    public abstract PlaceName getPlaceName();

    @Override
    public String toString() {
        return getPlaceName().name();
    }
}
