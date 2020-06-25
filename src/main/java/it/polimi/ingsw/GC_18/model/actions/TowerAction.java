package it.polimi.ingsw.GC_18.model.actions;

import java.util.List;

import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.cards.Pickable;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;
import it.polimi.ingsw.GC_18.model.resources.CouncilPrivileges;
import it.polimi.ingsw.GC_18.model.resources.FaithPoints;
import it.polimi.ingsw.GC_18.model.resources.MilitaryPoints;
import it.polimi.ingsw.GC_18.model.resources.Money;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.model.resources.Servants;
import it.polimi.ingsw.GC_18.model.resources.Stones;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.model.resources.Woods;
import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;

/**
 * This class represents a tower action.
 * A tower action is represented by a player placing a pawn in a tower floor.
 */
public class TowerAction extends Action implements Blocking {
    
    private static final long serialVersionUID = 5782577590755480634L;
    
    private Resources tmpResources;
    Floor<? extends DevelopmentCard> floor;
    DevelopmentCard card;
    private Resources cardCost;

    /**
     * This creates a new tower action.
     * @param pawn the pawn to be placed.
     * @param floor the floor the pawn will be placed at.
     * @param servantsSpent the servants spent by the player for modifying the action.
     */
    public TowerAction(Pawn pawn, Floor<? extends DevelopmentCard> floor, int servantsSpent) {
        super(pawn, floor, servantsSpent);
        tmpResources = copyResources(player.getResources());
        this.floor = floor;
        this.actionPlace = floor;
        this.card = floor.getCard();
        if (this.card != null) {
            this.cardCost = card.getCost();
        }
    }

    /**
     * This creates a new tower action that can be used for an extra tower action.
     * @param player the player that starts the action.
     * @param floor the floor the pawn will be placed at.
     * @param servantsSpent the servants spent by the player for modifying the action.
     * @param initialValue the initial value of the extra tower action.
     */
    TowerAction(Player player, Floor<? extends DevelopmentCard> floor, int servantsSpent, int initialValue) {
        super(player, floor, servantsSpent, initialValue);
        tmpResources = copyResources(player.getResources());
        this.actionPlace = floor;
        this.floor = floor;
        this.card = floor.getCard();
        if (this.card != null)
            this.cardCost = card.getCost();
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    /**
     * Copies the current resources of the player.
     * @param playerResources the resources to copy.
     * @return the resources copied.
     */
    private Resources copyResources(Resources playerResources) {
        return new Resources(new Woods(playerResources.getWoods()), new Stones(playerResources.getStones()),
                new Servants(playerResources.getServants()), new Money(playerResources.getMoney()), new MilitaryPoints(playerResources.getMilitaryPoints()),
                new FaithPoints(playerResources.getFaithPoints()), new CouncilPrivileges(playerResources.getCouncilPrivileges()), new VictoryPoints(playerResources.getVictoryPoints()));
    }

    /**
     * This method is used for applying a discount for picking up a card.
     * @param resources the resources of the discount
     */
    public void increaseResources(Resources resources) {
        List<Resource> resourceList = resources.getResourcesList();
        List<Resource> resourceCostList = cardCost.getResourcesList();

        for (int i = 0; i < resources.getResourcesList().size(); i++) {
            Resource resourceCost = resourceCostList.get(i);
            if (resourceCost.getValue() != 0) {
                Resource resource = resourceList.get(i);

                // applies the discount without adding extra resources a player should not have.
                resource.setValue(Math.min(resource.getValue(), resourceCost.getValue()));
                resource.addPlayer(player, null);
            }
        }

    }

    /**
     * @return true if the tower contains a pawn, false otherwise.
     */
    private boolean towerContainsPawn() {
        return floor.getTower().containsPawn();
    }

    /**
     * @return true if player has enough money for picking a card in a tower that contains a pawn, false otherwise.
     */
    private boolean playerHasMoney() {
        return player.getMoney() >= 3;
    }

    /**
     * @return true if the tower contains already the maximum number of pawn per player placed, false otherwise.
     */
    private boolean towerContainsPlayerPawn() {
        return floor.getTower().containsPlayerPawn(this.player);
    }

    /**
     * @return true if the player can spend the cost for picking up the card, false otherwise.
     */
    private boolean canSpendCost() {
        return Resources.compare(player, cardCost);
    }

    /**
     * @return true if the player can pick the card, false otherwise.
     */
    boolean tryPickCard() {
        if (canSpendCost()) {
            cardCost.subtractResources(player);
            return true;
        }
        return false;
    }


    /**
     * This method is used for checking if the card in the floor is of type venture. If so, if the card has both
     * type of costs the player must choose between one of them if he can afford both of them.
     * @return true if the card is of type venture and a cost is set, false otherwise.
     */
    boolean checkVentureCost() {
        if (card instanceof Venture) {
            Venture cardVenture = (Venture) card;
            boolean userHasCost = false;
            boolean userHasAlternative = false;
            boolean userHasAlternativeRequirement = false;

            // checks if the player can spend the normal cost
            if (cardVenture.getCost() != null) {
                userHasCost = Resources.compare(player, cardVenture.getCost());
            }
            // checks if the player can spend the alternative cost
            if (cardVenture.getAlternativeCost() != null) {
                userHasAlternative = Resources.compare(player, cardVenture.getAlternativeCost());
                userHasAlternativeRequirement = Resources.compare(player, cardVenture.getAlternativeCostRequirement());
            }

            // asks the player which type of cost he wants to spend
            if (cardVenture.getCost() != null && cardVenture.getAlternativeCost() != null) {
                if (userHasCost && userHasAlternative && userHasAlternativeRequirement) {
                    String response = block(player, WaitingCommand.VENTURE_COST);
                    if ("NORMAL".equals(response)) {
                        player.getController().getGame().getGameController().notifyPlayer(player, "normal");
                        cardCost = cardVenture.getCost();
                        return true;
                    } else if ("ALTERNATIVE".equals(response)) {
                        player.getController().getGame().getGameController().notifyPlayer(player, "alternative");
                        cardCost = cardVenture.getAlternativeCost();
                        return true;
                    }
                }
                if (userHasCost) {
                    player.getController().getGame().getGameController().notifyPlayer(player, "normal");
                    cardCost = cardVenture.getCost();
                    return true;
                }
                if (userHasAlternative && userHasAlternativeRequirement) {
                    player.getController().getGame().getGameController().notifyPlayer(player, "alternative");
                    cardCost = cardVenture.getAlternativeCost();
                    return true;
                }
            } else {
                if (cardVenture.getAlternativeCost() != null) {
                    player.getController().getGame().getGameController().notifyPlayer(player, "alternative");
                    cardCost = cardVenture.getAlternativeCost();
                    return true;
                }
                if (cardVenture.getCost() != null) {
                    player.getController().getGame().getGameController().notifyPlayer(player, "normal");
                    cardCost = cardVenture.getCost();
                    return true;
                }
            }
            return false;
        }
        return true;
    }


    /**
     * Restore the player to its state before the tower action check started.
     * @return false.
     */
    boolean restorePlayer() {
        for (Resource resource: tmpResources.getResourcesList()) {
            resource.setPlayer(player);
        }
        return false;
    }

    /**
     * @return the action floor.
     */
    public Floor<? extends DevelopmentCard> getFloor() {
        return floor;
    }

    /**
     * Notifies the player observers of a tower action change needed.
     */
    void notifyColor() {
        player.notifyColor(new NotifyAction(this, Notification.COLOR));

    }

    /**
     * Notififes the player observers of a value change needed.
     */
    void notifyValue() {
        player.notifyValue(new NotifyAction(this, Notification.VALUE));
    }

    /**
     * @return true if the player has a NoMoneyCostPlace effect, false otherwise.
     */
    private boolean checkNoMoneyCostPlace() {
        return player.getPersonalBoard().checkNoMoneyCostPlace();
    }


    /**
     * @return true if the player has a NoHighFloorBonus effect, false otherwise.
     */
    boolean checkNoHighFloorBonus() {
        return player.getPersonalBoard().checkNoHighFloorBonus() && (this.floor.getPlaceValue()==5 || this.floor.getPlaceValue()==7);
    }

    /**
     * @return true if the floor contains a card, false otherwise.
     */
    boolean containsCard() {
        return floor.getCard() != null;
    }

    /**
     * @return true if the player can and has to spend money for placing in a tower with already a pawn.
     */
    boolean checkMoney() {
        if (!checkNoMoneyCostPlace()) {
            if (towerContainsPawn()) {
                if (!playerHasMoney()) {
                    return restorePlayer();
                }
                player.subtractMoney(3);
            }
        }
        return true;
    }

    /**
     * @return true if the player has enough space for picking up the card, false otherwise.
     */
    boolean checkSpace() {
        Pickable cardToPick = (Pickable) card;
        return cardToPick.spaceExists(player);
    }

    /**
     * @return true if the player has enough resources for picking up a new territory card, false otherwise.
     */
    boolean checkPickableTerritory() {
        if (player.getPersonalBoard().checkNoMilitaryForTerritory()) {
            return true;
        }
        if (card instanceof Territory) {
            return Resources.compare(player, player.getPersonalBoard().getTerritoryCosts().get(player.getPersonalBoard().getTerritories().size()));
        }
        return true;
    }


    /**
     * Checks if the player can do the tower action. While checking modifies the player following the flow of the tower action,
     * if the player does not meet some criteria the check fails and the state of the player is restored.
     * @return true if the player can do the tower action, false otherwise.
     */
    @Override
    public boolean check() {

        substractServantsSpent();
        if (!containsCard()) {
            return restorePlayer();
        }
        if (!checkSpace()) {
            return restorePlayer();
        }

        if (!checkPickableTerritory()) {
            return restorePlayer();
        }

        notifyValue();
        if (!super.check()) {
            return restorePlayer();
        }

        if (this.towerContainsPlayerPawn() && !this.isPawnNeutral()) {
            return restorePlayer();
        }

        if (!checkMoney()) {
            return restorePlayer();
        }

        if (!checkNoHighFloorBonus()) {
            getBonus();
        }
        notifyColor();
        if (!checkVentureCost())
            return restorePlayer();

        if (!tryPickCard()) {
            return restorePlayer();
        }
        return true;

    }

    /**
     * Runs the tower action. Place the pawn in the floor, the player picks the card
     * and the card is removed from the floor.
     */
    @Override
    public void run() {
        placePawn();
        player.pickCard((Pickable) card);
        floor.setCard(null);
    }
    
}