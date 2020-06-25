package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.cards.Pickable;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents an extra tower action made by a player.
 */
public class ExtraTowerAction extends TowerAction {
    
    private static final long serialVersionUID = -3962326793736423554L;
    
    private Resources discount;

    /**
     * @param player the player that starts the extra tower action.
     * @param floor the floor.
     * @param servantsSpent the servants spent by the player for modifying the value.
     * @param resources the resources of the discount.
     * @param initialValue the initial value of the action.
     */
    public ExtraTowerAction(Player player, Floor<? extends DevelopmentCard> floor, int servantsSpent, int initialValue, Resources resources) {
        super(player, floor, servantsSpent, initialValue);
        discount = resources;
    }

    /**
     * @return true if the player can do the extra tower action, false othwerise.
     */
    @Override
    public boolean check() {
        substractServantsSpent();

        if (checkPlaceNegate(actionPlace)) {
            return restorePlayer();
        }

        if (!containsCard()) {
            return restorePlayer();
        }
        if (!checkSpace()) {
            return restorePlayer();
        }
        if (!checkPickableTerritory()) {
            return restorePlayer();
        }


        if (isPlaceOccupied() && !checkPlaceInOccupied()) {
            return restorePlayer();
        }
        if (this.actionValue < this.actionPlace.getPlaceValue()) {
            return restorePlayer();
        }

        notifyValue();

        if (!checkMoney()) {
            return restorePlayer();
        }

        if (!checkNoHighFloorBonus()) {
            getBonus();
        }
        notifyColor();
        if (!checkVentureCost())
            return restorePlayer();

        if (discount != null) {
            increaseResources(discount);
        }
        if (!tryPickCard()) {
            return restorePlayer();
        }
        return true;

    }

    /**
     * Runs the extra tower action.
     */
    @Override
    public void run() {
        player.pickCard((Pickable) card);
        floor.setCard(null);
    }
    
}
