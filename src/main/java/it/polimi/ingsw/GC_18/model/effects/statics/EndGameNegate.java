package it.polimi.ingsw.GC_18.model.effects.statics;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;

/**
 * This class represents a static effect that negates bonus received at the end of the game
 * for cards in the Personal Board.
 */
public class EndGameNegate implements StaticEffect, Serializable {
   
    private static final long serialVersionUID = 4764691547387594732L;
    
    private TowerColor towerColor;

    /**
     * @param towerColor the color of the cards for which the bonus is negated.
     */
    public EndGameNegate(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    @Override
    public void apply(Player player) {
        player.getPersonalBoard().getStaticEffects().put("endGameNegate", this);
    }

    /**
     * @return the tower color.
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    @Override
    public String toString() {
        return "Negates bonus received at the end of the game for " + towerColor.name() + " cards";
    }
    
}
