package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;
import java.util.Observable;

import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.Pickable;
import it.polimi.ingsw.GC_18.model.effects.HarvestEffect;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.effects.ProductionEffect;
import it.polimi.ingsw.GC_18.model.gamepieces.BonusTile;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.gamepieces.PersonalBoard;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyAction;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyPlacePawn;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyReport;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;
import it.polimi.ingsw.GC_18.model.resources.CouncilPrivileges;
import it.polimi.ingsw.GC_18.model.resources.FaithPoints;
import it.polimi.ingsw.GC_18.model.resources.MilitaryPoints;
import it.polimi.ingsw.GC_18.model.resources.Money;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.model.resources.Servants;
import it.polimi.ingsw.GC_18.model.resources.Stones;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.model.resources.Woods;
import it.polimi.ingsw.GC_18.server.controller.PlayerController;

/**
 * This class represents a game player. Every player is observed by its effects
 * that change the player properties during the player actions.
 */
public class Player extends Observable implements Serializable {

    private static final long serialVersionUID = 978217546587167372L;

    private String username;
    private PlayerColor color;
    private boolean yourTurn;
    private VictoryPoints victoryPoints;
    private MilitaryPoints militaryPoints;
    private FaithPoints faithPoints;
    private Woods woods;
    private Stones stones;
    private Servants servants;
    private Money money;
    private Pawn blackPawn;
    private Pawn orangePawn;
    private Pawn whitePawn;
    private Pawn neutralPawn;
    private BonusTile bonusTile;
    private PersonalBoard personalBoard;
    private CouncilPrivileges councilPrivileges;
    private PlayerController controller;
    private boolean placedPawn;
    private int[] report = {0,0,0};
    private boolean chainActive;
    private Resources bufferedResources;

    /**
     * Creates a new Player by initializing its resources pool and its dices.
     * Also creates the player controller.
     * @param username the username of the player.
     * @param playerColor the player's color.
     */
    public Player(String username, PlayerColor playerColor) {
        woods = new Woods(0);
        stones = new Stones(0);
        servants = new Servants(0);
        money = new Money(0);
        militaryPoints = new MilitaryPoints(0);
        victoryPoints = new VictoryPoints(0);
        faithPoints = new FaithPoints(0);
        councilPrivileges = new CouncilPrivileges(0);

        this.username = username;
        this.color = playerColor;
        blackPawn = new Pawn(DiceColor.BLACK, this);
        orangePawn = new Pawn(DiceColor.ORANGE, this);
        whitePawn = new Pawn(DiceColor.WHITE, this);
        neutralPawn = new Pawn(DiceColor.NEUTRAL, this);
        personalBoard = new PersonalBoard(this);
        controller = new PlayerController();
    }


    /**
     * @return the array representing the report.
     */
    public int[] getReport() {
        return report;
    }

    /**
     * @return true if the player has placed a pawn on his turn.
     */
    public boolean hasPlacedPawn() {
        return placedPawn;
    }

    /**
     * @param placedPawn the boolean indicating if the pawn is placed.
     */
    public void setPlacedPawn(boolean placedPawn) {
        this.placedPawn = placedPawn;
    }

    /**
     * Initializes a resources buffer for production and harvest action.
     */
    private void initializeResourcesBuffer() {
        bufferedResources = new Resources(new Woods(woods.getValue()), new Stones(stones.getValue()), new Servants(servants.getValue()),
                new Money(money.getValue()), new MilitaryPoints(militaryPoints.getValue()), new FaithPoints(faithPoints.getValue()),
                new CouncilPrivileges(0), new VictoryPoints(victoryPoints.getValue()));
    }

    /**
     * Starts a production action. Initializes the resources buffer.
     * @param actionValue the value of the action.
     */
    public void startProduction(int actionValue) {
        chainActive = true;
        initializeResourcesBuffer();

        if (actionValue > 1) {
            bonusTile.getProductionBonus().addResources(this, null);
        }

        for (ProductionEffect p : getPersonalBoard().getProductionEffects()) {
            if (p.isActivable(actionValue)) {
                p.apply(this);
            }
        }
        chainActive = false;
        bufferedResources = null;
    }

    /**
     * Starts an harvest action. Initializes the resources buffer.
     * @param actionValue the value of the action.
     */
    public void startHarvest(int actionValue) {
        chainActive = true;
        initializeResourcesBuffer();

        if (actionValue > 1) {
            bonusTile.getHarvestBonus().addResources(this, null);
        }

        for (HarvestEffect h : getPersonalBoard().getHarvestEffects()) {
            if (h.isActivable(actionValue)) {
                h.apply(this);
            }
        }
        chainActive = false;
        bufferedResources = null;
    }

    /**
     * @return the buffered resources.
     */
    public Resources getBufferedResources() {
        return bufferedResources;
    }

    /**
     * @return true if a production or harvest action is going on for the player.
     */
    public boolean isChainActive() {
        return chainActive;
    }

    /**
     * @param servantsSpent the servants spent.
     * @param servantsMalus the servants malus.
     * @return the servants with the malus applied.
     */
    int calculateServantMalus(int servantsSpent, int servantsMalus) {
        return servantsSpent / servantsMalus;
    }

    /**
     * Discard a player leader and adds council privileges to the player.
     * @param leader the leader to be discarded.
     */
    public void discardLeader(Leader leader) {
        personalBoard.getLeadersHand().remove(leader);
        new CouncilPrivileges(1).addPlayer(this, null);
    }

    /**
     * Tries to activate a player leader.
     * @param leader the leader to be played.
     * @return true if the player has been activated, false otherwise.
     */
    public boolean activateLeader(Leader leader) {
        if (leader.getRequirement().checkPlayable(this)) {
            pickCard(leader);
            personalBoard.getLeadersHand().remove(leader);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Picks a card for the player.
     * @param card the card to be picked.
     */
    public void pickCard(Pickable card) {
        card.add(this);
    }

    /**
     * Adds victory points to the player and if a notify resource is present notifies the observers.
     * @param victoryPoints the victory points to add.
     * @param notifyResource the notify.
     */
    public void addVictoryPoints(int victoryPoints, NotifyResource notifyResource) {
        this.victoryPoints.add(victoryPoints);
        this.setChanged();
        notifyAddResource(notifyResource);

    }

    /**
     * Adds military points to the player and if a notify resource is present notifies the observers.
     * @param militaryPoints the military points to add.
     * @param notifyResource the notify.
     */
    public void addMilitaryPoints(int militaryPoints, NotifyResource notifyResource) {
        this.militaryPoints.add(militaryPoints);
        this.setChanged();
        notifyAddResource(notifyResource);

    }

    /**
     * Adds faith points to the player and if a notify resource is present notifies the observers.
     * @param faithPoints the faith points to add.
     * @param notifyResource the notify.
     */
    public void addFaithPoints(int faithPoints, NotifyResource notifyResource) {
        this.faithPoints.add(faithPoints);
        this.setChanged();
        notifyAddResource(notifyResource);

    }

    /**
     * Adds woods to the player and if a notify resource is present notifies the observers.
     * @param woods the woods to add.
     * @param notifyResource the notify.
     */
    public void addWoods(int woods, NotifyResource notifyResource) {
        this.woods.add(woods);
        this.setChanged();
        notifyAddResource(notifyResource);
    }

    /**
     * Adds stones to the player and if a notify resource is present notifies the observers.
     * @param stones the stones to add.
     * @param notifyResource the notify.
     */
    public void addStones(int stones, NotifyResource notifyResource) {
        this.stones.add(stones);
        this.setChanged();
        notifyAddResource(notifyResource);
    }

    /**
     * Adds servants to the player and if a notify resource is present notifies the observers.
     * @param servants the servants to add.
     * @param notifyResource the notify.
     */
    public void addServants(int servants, NotifyResource notifyResource) {
        this.servants.add(servants);
        this.setChanged();
        notifyAddResource(notifyResource);
    }

    /**
     * Adds money to the player and if a notify resource is present notifies the observers.
     * @param money the money to add.
     * @param notifyResource the notify.
     */
    public void addMoney(int money, NotifyResource notifyResource) {
        this.money.add(money);
        this.setChanged();
        notifyAddResource(notifyResource);
    }

    /**
     * This method is used for notifying the observers of a place in a tower.
     * @param notifyAction the notify.
     */
    public void notifyColor(NotifyAction notifyAction) {
        this.setChanged();
        notifyObservers(notifyAction);
    }

    /**
     * This method is used for notifying the observers of a place.
     * @param notifyAction the notify.
     */
    public void notifyValue(NotifyAction notifyAction) {
        this.setChanged();
        notifyObservers(notifyAction);
    }

    /**
     * This method is used for notifying the observers of a harvest action.
     * @param notifyAction the notify.
     */
    public void notifyHarvest(NotifyAction notifyAction) {
        this.setChanged();
        notifyObservers(notifyAction);
    }

    /**
     * This method is used for notifying the observers of a production action.
     * @param notifyAction the notify.
     */
    public void notifyProduction(NotifyAction notifyAction) {
        this.setChanged();
        notifyObservers(notifyAction);
    }

    /**
     * This method is used for notifying the observers of a a resources add.
     * @param notifyResource the notify.
     */
    private void notifyAddResource(NotifyResource notifyResource) {
        if (notifyResource != null) {
            this.setChanged();
            notifyObservers(notifyResource);
        }
    }

    /**
     * This method is used for notifying the observers of a pawn placement.
     * @param notifyPlacePawn the notify.
     */
    public void notifyPlacePawn(NotifyPlacePawn notifyPlacePawn) {
        this.setChanged();
        notifyObservers(notifyPlacePawn);
    }

    /**
     * This method is used for notifying the observers of a vatican report.
     * @param notifyReport the notify.
     */
    public void notifyVaticanReport(NotifyReport notifyReport) {
        this.setChanged();
        notifyObservers(notifyReport);
    }

    /**
     * Tries to activate an once per turn effect.
     * @param oncePerTurn the effect activated
     * @return true if the effect is activable, false otherwise.
     */
    public boolean activateOnce(OncePerTurnEffect oncePerTurn) {
        if (oncePerTurn.isActivable()) {
            oncePerTurn.apply(this);
            oncePerTurn.setActivated(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Subtract victory points from the player.
     * @param victoryPoints the victory points to be subtracted.
     */
    public void subtractVictoryPoints(int victoryPoints) {
        this.victoryPoints.subtract(victoryPoints);
    }

    /**
     * Subtract military points from the player.
     * @param militaryPoints the military points to be subtracted.
     */
    public void subtractMilitaryPoints(int militaryPoints) {
        this.militaryPoints.subtract(militaryPoints);
    }

    /**
     * Subtract faith points from the player.
     * @param faithPoints the faith points to be subtracted.
     */
    public void subtractFaithPoints(int faithPoints) {
        this.faithPoints.subtract(faithPoints);
    }

    /**
     * Subtract councilPrivileges from the player.
     * @param councilPrivileges the council privileges to be subtracted.
     */
    public void subtractCouncilPrivileges(int councilPrivileges) {
        this.councilPrivileges.subtract(councilPrivileges);
    }

    /**
     * Subtract woods from the player.
     * @param woods the woods to be subtracted.
     */
    public void subtractWoods(int woods) {
        this.woods.subtract(woods);
    }

    /**
     * Subtract stones from the player.
     * @param stones the stones to be subtracted.
     */
    public void subtractStones(int stones) {
        this.stones.subtract(stones);
    }

    /**
     * Subtract servants from the player.
     * @param servants the servants to be subtracted.
     */
    public void subtractServants(int servants) {
        this.servants.subtract(servants);
    }

    /**
     * Subtract money from the player.
     * @param money the money to be subtracted
     */
    public void subtractMoney(int money) {
        this.money.subtract(money);
    }

    /**
     * @return the player's resources.
     */
    public Resources getResources() {
        return new Resources(woods, stones, servants, money, militaryPoints, faithPoints, new CouncilPrivileges(0),
                victoryPoints);
    }

    /**
     * @return the player's woods.
     */
    public int getWoods() {
        return woods.getValue();
    }

    /**
     * @return the player's stones.
     */
    public int getStones() {
        return stones.getValue();
    }

    /**
     * @return the player's servants.
     */
    public int getServants() {
        return servants.getValue();
    }

    /**
     * @return the player's money.
     */
    public int getMoney() {
        return money.getValue();
    }

    /**
     * @return the player's council privileges.
     */
    public int getCouncilPrivileges() {
        return councilPrivileges.getValue();
    }

    /**
     * @return the player's victory points.
     */
    public int getVictoryPoints() {
        return victoryPoints.getValue();
    }

    /**
     * @return the player's military points.
     */
    public int getMilitaryPoints() {
        return militaryPoints.getValue();
    }

    /**
     * @return the player's faith points.
     */
    public int getFaithPoints() {
        return faithPoints.getValue();
    }

    /**
     * @return the player's personal board.
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Sets the player's woods.
     * @param woods the woods to be set.
     */
    public void setWoods(int woods) {
        this.woods.setValue(woods);
    }

    /**
     * Sets the player's stones.
     * @param stones the stones to be set.
     */
    public void setStones(int stones) {
        this.stones.setValue(stones);
    }

    /**
     * Sets the player's servants.
     * @param servants the servants to be set.
     */
    public void setServants(int servants) {
        this.servants.setValue(servants);
    }

    /**
     * Sets the player's money.
     * @param money the money to be set.
     */
    public void setMoney(int money) {
        this.money.setValue(money);
    }

    /**
     * Sets the player's victory points.
     * @param victoryPoints the victory points to be set.
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints.setValue(victoryPoints);
    }

    /**
     * Sets the player's military points.
     * @param militaryPoints the military points to be set.
     */
    public void setMilitaryPoints(int militaryPoints) {
        this.militaryPoints.setValue(militaryPoints);
    }

    /**
     * Sets the player's faith points.
     * @param faithPoints the faith points to be set.
     */
    public void setFaithPoints(int faithPoints) {
        this.faithPoints.setValue(faithPoints);
    }

    /**
     * @param pawnColor the pawn color.
     * @return the dice of the color pawn color.
     */
    public Pawn getPawn(String pawnColor) {
        if ("BLACK".equalsIgnoreCase(pawnColor)) {
            return blackPawn;
        } else if ("ORANGE".equalsIgnoreCase(pawnColor)) {
            return orangePawn;
        } else if ("WHITE".equalsIgnoreCase(pawnColor)) {
            return whitePawn;
        } else if ("NEUTRAL".equalsIgnoreCase(pawnColor)){
            return neutralPawn;
        } else {
            return null;
        }
    }

    /**
     * @return the black pawn.
     */
    public Pawn getBlackPawn() {
        return blackPawn;
    }

    /**
     * @return the orange pawn.
     */
    public Pawn getOrangePawn() {
        return orangePawn;
    }

    /**
     * @return the white pawn.
     */
    public Pawn getWhitePawn() {
        return whitePawn;
    }

    /**
     * @return the neutral pawn.
     */
    public Pawn getNeutralPawn() {
        return neutralPawn;
    }

    /**
     * @return true if it is the turn player, false otherwise.
     */
    public boolean isYourTurn() {
        return yourTurn;
    }

    /**
     * Sets the turn of the player to be yourTurn.
     * @param yourTurn boolean indicating if it is the turn player.
     */
    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    /**
     * @return the player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the bonus tile to the player.
     * @param bonusTile the bonus tile to be set.
     */
    public void setBonusTile(BonusTile bonusTile) {
        this.bonusTile = bonusTile;
    }

    /**
     * @return the player's color.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Sets the player's color.
     * @param color the color to be set.
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * @return the player's controller.
     */
    public PlayerController getController() {
        return controller;
    }

    /**
     * Sets the player's controller.
     * @param controller the controller to be set.
     */
    public void setController(PlayerController controller) {
        this.controller = controller;
    }


}
