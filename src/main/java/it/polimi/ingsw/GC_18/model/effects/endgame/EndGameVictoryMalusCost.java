package it.polimi.ingsw.GC_18.model.effects.endgame;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.effects.EndgameEffect;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This class represents an end game effect that adds a malus of Victory Points endgame
 * for every cost in player of towercolor type of cards.
 */
public class EndGameVictoryMalusCost implements EndgameEffect, Serializable {
    
    private static final long serialVersionUID = 4465472231070771088L;
    
    private TowerColor towerColor;

    /**
     * This creates a new EndGameVictoryMalusCost effect.
     * @param towerColor the tower color.
     */
    public EndGameVictoryMalusCost(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    @Override
    public void apply(Player player) {
        switch (towerColor) {
        case YELLOW:
            for (Building building : player.getPersonalBoard().getBuildings()) {
                player.subtractVictoryPoints(GameUtils.calculateWeight(building.getCost().getWoods(), 1));
                player.subtractVictoryPoints(GameUtils.calculateWeight(building.getCost().getStones(), 1));
            }
            break;

        case BLUE:
            for (Character character : player.getPersonalBoard().getCharacters()) {
                player.subtractVictoryPoints(GameUtils.calculateWeight(character.getCost().getWoods(), 1));
                player.subtractVictoryPoints(GameUtils.calculateWeight(character.getCost().getStones(), 1));
            }
            break;

        case GREEN:
            for (Territory territory : player.getPersonalBoard().getTerritories()) {
                player.subtractVictoryPoints(GameUtils.calculateWeight(territory.getCost().getWoods(), 1));
                player.subtractVictoryPoints(GameUtils.calculateWeight(territory.getCost().getStones(), 1));
            }
            break;

        case PURPLE:
            for (Venture venture : player.getPersonalBoard().getVentures()) {
                player.subtractVictoryPoints(GameUtils.calculateWeight(venture.getCost().getWoods(), 1));
                player.subtractVictoryPoints(GameUtils.calculateWeight(venture.getCost().getStones(), 1));
            }
            break;

        default:
            break;
        }

    }

    @Override
    public String toString() {
        return "At the end of the game, you lose 1 Victory Point for every wood and stone costs on your " + towerColor.name() +" card you have";
    }
    
}
