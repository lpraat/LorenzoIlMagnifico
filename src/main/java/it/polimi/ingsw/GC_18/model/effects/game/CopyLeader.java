package it.polimi.ingsw.GC_18.model.effects.game;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This class represents a game effect that copies the effect of a Leader card and adds it
 * to the player.
 */
public class CopyLeader implements GameEffect, Serializable, Blocking {
    
    private static final long serialVersionUID = -7561432478534438929L;
    
    private Leader leader;

    /**
     * Creates a new CopyLeader effect.
     * @param leader the leader of the player that activates this effect. It is NOT the
     *               leader copied.
     */
    public CopyLeader(Leader leader) {
        this.leader = leader;
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public void apply(Player player) {
        List<Leader> leadersToCopy = player.getController().getGame().getLeadersPlayed(player);
        if (leadersToCopy.isEmpty()) {
            player.getController().getGame().getGameController().notifyPlayer(player, "There are no cards to copy!");
            return;
        }
        player.getController().getGame().getGameController().notifyPlayer(player, "You can copy one of these cards: " + GameUtils.stringFromList(leadersToCopy));
        String response = block(player, WaitingCommand.LEADER_COPY);
        Leader leaderToCopy = player.getController().getGame().getLeadersPlayed(player).get(Integer.parseInt(response) - 1);
        if (leaderToCopy.getGameEffect() != null) {
            leader.setGameEffect(leaderToCopy.getGameEffect());
            leaderToCopy.getGameEffect().add(player);
        } else {
            leader.setOncePerTurnEffect(leaderToCopy.getOncePerTurnEffect());
            leaderToCopy.getOncePerTurnEffect().add(player);
        }
        player.getController().getGame().getGameController().notifyPlayer(player,"You copied " + leaderToCopy.getName());
    }

    @Override
    public String toString() {
        return "Gives the possibility of copying another leader card played by another player";
    }
    
}
