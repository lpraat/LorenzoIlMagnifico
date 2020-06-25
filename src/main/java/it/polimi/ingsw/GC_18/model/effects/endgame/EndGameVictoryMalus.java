package it.polimi.ingsw.GC_18.model.effects.endgame;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.EndgameEffect;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This class represents an end game effect that adds a malus of Victory Points endgame
 * for every resource in resources.
 */
public class EndGameVictoryMalus implements EndgameEffect, Serializable {
    
    private static final long serialVersionUID = -8353451297090380216L;
    
    private Resources resources;

    /**
     * This creates a new EndGameVictoryMalusEffect.
     * @param resources the resources of the malus.
     */
    public EndGameVictoryMalus(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void apply(Player player) {
        List<Resource> playerResourcesList = player.getResources().getResourcesList();
        List<Resource> resourcesList = resources.getResourcesList();

        for (int i = 0; i < playerResourcesList.size(); i++) {
            Resource resourcePlayer = playerResourcesList.get(i);
            Resource resource = resourcesList.get(i);

            if (resource.getValue() != 0) {
                player.subtractVictoryPoints(GameUtils.calculateWeight(resourcePlayer.getValue(), resource.getValue()));
            }
        }
    }

    @Override
    public String toString() {
        return "At the end of the game for every resource in the following resources a victory point will be lost: \n"+resources.toString();
    }
    
}
