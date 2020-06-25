package it.polimi.ingsw.GC_18.model.effects.immediate;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.SourceNotifier;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents an immediate effect that gives bonus resources to a player for
 * each card of color TowerColor he has.
 */
public class BonusPerCard extends SourceNotifier implements ImmediateEffect, Serializable {
    
    private static final long serialVersionUID = -1004942750749123816L;
    
    private Resources resources;
    private TowerColor towerColor;

    /**
     * Creates a new BonusPerCard effect.
     * @param resources the resources bonus to the player.
     * @param towerColor the color of the card.
     */
    public BonusPerCard(Resources resources, TowerColor towerColor) {
        this.resources = resources;
        this.towerColor = towerColor;
        source = Source.IMMEDIATE_EFFECT;
    }

    @Override
    public void apply(Player player) {
        switch (towerColor) {
        case GREEN:
            for (int i = 0; i < player.getPersonalBoard().getTerritories().size(); i++) {
                resources.addResources(player, source);
            }
            break;
        case BLUE:
            for (int i = 0; i < player.getPersonalBoard().getCharacters().size(); i++) {
                resources.addResources(player, source);
            }
            break;
        case YELLOW:
            for (int i = 0; i < player.getPersonalBoard().getBuildings().size(); i++) {
                resources.addResources(player, source);
            }
            break;
        case PURPLE:
            for (int i = 0; i < player.getPersonalBoard().getVentures().size(); i++) {
                resources.addResources(player, source);
            }
            break;
        default:
            break;
        }
    }

    @Override
    public String toString() {
        return "Gives you the following bonus for each card of " + towerColor.name() + "color" + "\n" + resources.toString();
    }
}