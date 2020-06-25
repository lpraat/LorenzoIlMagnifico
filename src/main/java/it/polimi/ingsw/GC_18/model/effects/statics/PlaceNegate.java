package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that negates the Player placing
 * in a given ActionPlace.
 */

public class PlaceNegate implements StaticEffect, Serializable {
   
    private static final long serialVersionUID = 1899617956359122475L;
    
    private List<PlaceName> placeNames;

    /**
     * @param placeNames the list of the names of the action places the player can't place in.
     */
    public PlaceNegate(List<PlaceName> placeNames) {
        this.placeNames = placeNames;
    }

    /**
     * @param name the place.
     * @return true if the place for place name is negated.
     */
    public boolean isPresent(PlaceName name) {
        for (PlaceName placeName : placeNames) {
            if (placeName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("placeNegate", this);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        placeNames.forEach(placeName -> stringBuilder.append(placeName.name()).append(" "));
        return "You can't place your pawn in the following places " + stringBuilder.toString();
    }
    
}
