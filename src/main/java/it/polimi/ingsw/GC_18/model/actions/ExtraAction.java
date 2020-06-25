package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;

/**
 * This class represents an extra action made by a player.
 * It can be an extra harvest or an extra production.
 */
public class ExtraAction extends Action {
   
    private static final long serialVersionUID = -3207546107152610476L;
    
    private boolean changeable;

    /**
     * @param player the player that starts the extra action.
     * @param actionPlace the action place.
     * @param servantsSpent the servants spent by the player.
     * @param initialValue the initial value of the extra action.
     * @param changeable to indicate if it is changeable by other effects.
     */
    public ExtraAction(Player player, ActionPlace actionPlace, int servantsSpent, int initialValue, boolean changeable) {
        super(player, actionPlace, servantsSpent, initialValue);
        this.changeable = changeable;
    }

    /**
     * Runs the extra action.
     */
    @Override
    public void run() {
        substractServantsSpent();
      

        if (isProductionArea()) {

            if (changeable) {
                notifyProduction();
            }
            player.startProduction(actionValue);
            
        } else if (isHarvestArea()) {
            if (changeable) {
                notifyHarvest();
            }
            player.startHarvest(actionValue);
        }
    }
    
}
