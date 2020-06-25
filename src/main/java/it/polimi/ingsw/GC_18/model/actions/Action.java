package it.polimi.ingsw.GC_18.model.actions;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.SourceNotifier;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.FightSpot;
import it.polimi.ingsw.GC_18.model.gamepieces.HarvestArea;
import it.polimi.ingsw.GC_18.model.gamepieces.LargeHarvestArea;
import it.polimi.ingsw.GC_18.model.gamepieces.LargeProductionArea;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.gamepieces.ProductionArea;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyPlacePawn;

/**
 * This class represents an action made by a player.
 * An action is represented by a player placing a pawn in an action place. Every action
 * has an action value the player can increase by spending servants,
 */
public class Action extends SourceNotifier {

    private static final long serialVersionUID = -7511292296308449000L;
    
    Player player;
    private Pawn pawn;
    int actionValue;
    private int servantsSpent;
    ActionPlace actionPlace;

    /**
     * @param pawn the pawn to place.
     * @param actionPlace the action place to place in.
     * @param servantsSpent the servants spent for adding value to the action.
     */
    public Action(Pawn pawn, ActionPlace actionPlace, int servantsSpent) {
        this.pawn = pawn;
        this.player = pawn.getPlayer();
        this.actionPlace = actionPlace;
        this.servantsSpent = servantsSpent;
        this.source = Source.ACTION_PLACE;
    }

    /**
     * This create a new action that can be used for an extra action.
     * @param player the player that starts the action.
     * @param actionPlace the action place to place in.
     * @param servantsSpent the servants spent for adding value to the action.
     * @param initialValue the initial value of the action.
     */
    Action(Player player, ActionPlace actionPlace, int servantsSpent, int initialValue) {
        this.actionPlace = actionPlace;
        this.player = player;
        this.servantsSpent = servantsSpent;
        this.actionValue = initialValue + servantsSpent;
    }

    /**
     * @return true if the place exists. A place could not exist because of the number of players.
     */
    private boolean placeExist() {
        return actionPlace != null;
    }

    /**
     * @return true if the pawn place is neutral.
     */
    boolean isPawnNeutral() {
        return pawn.getColor() == DiceColor.NEUTRAL;
    }

    /**
     * @return true if the action place is the extra action space of harvest/production area.
     */
    private boolean isLarger() {
        return actionPlace instanceof LargeHarvestArea || actionPlace instanceof LargeProductionArea;
    }

    /**
     * Sets the initial value of the action by adding the pawn value and applying larger malus if present.
     */
    private void initialValue() {
        if (isLarger()) {
            actionValue -= 3;
        }
        this.actionValue += pawn.getValue();
        notifyPlacePawn();
    }

    /**
     * Adds the value of the servants spent.
     */
    private void servantsValue() {
        this.actionValue += this.servantsSpent;
    }

    /**
     * @return true if the player has alread placed this pawn.
     */
    private boolean isPawnPlaced() {
        return pawn.isPlaced();
    }

    /**
     * @return true if the place is occupied.
     */
    boolean isPlaceOccupied() {
        return actionPlace.isOccupied();
    }

    /**
     * Places the pawn in the actionPlace
     */
    void placePawn() {
        this.actionPlace.setPawn(this.pawn);
    }

    /**
     * Gets the bonus from the action place and adds it to the player.
     */
    void getBonus() {
        if (actionPlace.getBonus() != null) {
            this.actionPlace.getBonus().addResources(player, this.source);
        }
    }

    /**
     * @return true if the action place is a production place.
     */
    public boolean isProductionArea() {
        return actionPlace instanceof ProductionArea || actionPlace instanceof LargeProductionArea;

    }

    /**
     * @return true if the action place is a harvest place.
     */
    public boolean isHarvestArea() {
        return actionPlace instanceof HarvestArea || actionPlace instanceof LargeHarvestArea;
    }

    /**
     * @return true if the place is a fight spot.
     */
    private boolean isFightSpot() {
        return actionPlace instanceof FightSpot;
    }

    /**
     * Changes the value of the action.
     * @param value the value.
     */
    public void changeActionValue(int value) {
        actionValue += value;
    }

    /**
     * Notifies player's observers of a harvest action.
     */
    void notifyHarvest() {
        if (isHarvestArea()) {
            player.notifyHarvest(new NotifyAction(this, Notification.NULL));
        }
    }

    /**
     * Subtracts the servants spent.
     */
    void substractServantsSpent() {
        player.subtractServants(servantsSpent);
    }

    /**
     * Notifies player's observers of a production action.
     */
    void notifyProduction() {
        if (isProductionArea()) {
            player.notifyProduction(new NotifyAction(this, Notification.NULL));
        }
    }

    /**
     * Notifies player's observers of a pawn placed.
     */
    private void notifyPlacePawn() {
        player.notifyPlacePawn(new NotifyPlacePawn(pawn, this));
    }

    /**
     * @param actionPlace the action place to check for.
     * @return true if the player has a place negate effect for this action place.
     */
    boolean checkPlaceNegate(ActionPlace actionPlace) {
        return player.getPersonalBoard().checkPlaceNegate(actionPlace);
    }

    /**
     * @return true if player can place in occupied action places.
     */
    boolean checkPlaceInOccupied() {
        return player.getPersonalBoard().checkPlaceInOccupied();
    }

    /**
     * @return true if the player can do the action.
     */
    public boolean check() {
        if (!placeExist())
            return false;
        if (checkPlaceNegate(actionPlace)) {
            return false;
        }

        if (isPawnPlaced()) {
            return false;
        }

        if (isPlaceOccupied() && !checkPlaceInOccupied()) {
            return false;
        }

        this.initialValue();
        this.servantsValue();
        notifyHarvest();
        notifyProduction();
        if (this.actionValue >= this.actionPlace.getPlaceValue()) {
            return true;
        }
        return false;
    }

    /**
     * Runs the action by placing the player pawn in the action place and getting the bonus.
     * Eventually starts the harvest or production action.
     */
    public void run() {
        substractServantsSpent();
        placePawn();
        getBonus();

        if (isHarvestArea()) {
            player.startHarvest(actionValue);
        }
        if (isProductionArea()) {
            player.startProduction(actionValue);
        }

        if (isFightSpot()) {
            FightSpot fightSpot = (FightSpot) actionPlace;
            fightSpot.addServantsSpent(servantsSpent);
        }
    }

}
