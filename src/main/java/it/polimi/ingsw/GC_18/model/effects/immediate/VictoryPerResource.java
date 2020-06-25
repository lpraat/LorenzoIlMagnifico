package it.polimi.ingsw.GC_18.model.effects.immediate;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This class represents an immediate effect that gives the player Victory Points for each
 * type of resource in resources he has.
 */
public class VictoryPerResource implements ImmediateEffect, Serializable {
    
    private static final long serialVersionUID = -5534964080906489916L;
    
    private Resources resources;

    /**
     * Creates a new victory per resource effect.
     * @param resources the resources to check for adding the Victory Points.
     */
    public VictoryPerResource(Resources resources) {
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
                player.addVictoryPoints(GameUtils.calculateWeight(resource.getValue(), resourcePlayer.getValue()), null);
            }
        }
    }

    @Override
    public String toString() {
        return "Gives you victory points for each type of the following resources you have \n" + resources.toString();
    }
    
}
