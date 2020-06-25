package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.Map;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.Notification;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.cards.ExcomunicationTile;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyReport;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a vatican spot in the vatican church.
 * It keeps an excomunication tile and the faith point required for supporting the church.
 */
public class VaticanSpot implements Serializable, Blocking {
    
    private static final long serialVersionUID = -4903950037725552919L;
    
    private ExcomunicationTile excomunicationTile;
    private int faithPointRequired;

    /**
     * Creates a new vatican spot
     * @param faithPointRequired the faith point required for supporting this vatican spot.
     */
    public VaticanSpot(int faithPointRequired) {
        this.faithPointRequired = faithPointRequired;
    }

    /**
     * @param excomunicationTile the tile to be set.
     */
    public void setExcomunicationTile(ExcomunicationTile excomunicationTile) {
        this.excomunicationTile = excomunicationTile;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    /**
     * @return the excomunication tile card.
     */
    public ExcomunicationTile getExcomunicationTile() {
        return excomunicationTile;
    }

    /**
     * Asks the player if he wants to support the church.
     * @param period the period of the report.
     * @param player the player to whom is asking.
     * @param faithTrack the faith track of the board.
     */
    private void ask(int period, Player player, Map<Integer, Resources> faithTrack) {
        player.setYourTurn(true);
        String response = block(player, WaitingCommand.ASK_SUPPORT);
        player.setYourTurn(false);
        if ("YES".equals(response)) {
            int playerFaith = player.getFaithPoints();
            if (playerFaith > 15) 
                playerFaith = 15;
            Resources bonus = faithTrack.get(playerFaith);
            player.setFaithPoints(0);
            bonus.addResources(player, null);
            player.notifyVaticanReport(new NotifyReport(Notification.CHURCH_SUPPORT));
        } else {
            player.pickCard(excomunicationTile);
            player.getReport()[period] = 1;
        }
    }

    /**
     * Does the report for this period spot.
     * @param period the period.
     * @param player the player to ask the support.
     * @param faithTrack the faith track of the board.
     */
    public void report(int period, Player player, Map<Integer, Resources> faithTrack) {
         if (player.getFaithPoints() >= faithPointRequired) {
             ask(period, player, faithTrack);
        } else {
             player.getController().getGame().getGameController().notifyPlayer(player, "You don't have enough faith points! You are Excommunicated!");
             player.pickCard(excomunicationTile);
             player.getReport()[period] = 1;
        }
    }

}
