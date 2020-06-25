package it.polimi.ingsw.GC_18.model.effects.immediate;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents an immediate effect that gives bonus resources to a player.
 */
public class Bonus extends SourceNotifier implements ImmediateEffect, Serializable {

    private static final long serialVersionUID = -548505504101752185L;
    
    private Resources resources;

    /**
     * Creates a new Bonus effect.
     * @param resources the resources to be given.
     */
    public Bonus(Resources resources) {
        this.resources = resources;
        source = Source.IMMEDIATE_EFFECT;
    }

    @Override
    public void apply(Player player) {
        resources.addResources(player, source);
    }

    @Override
    public String toString() {
        return "Gives the following bonus resources: \n" + resources.toString();
    }
}