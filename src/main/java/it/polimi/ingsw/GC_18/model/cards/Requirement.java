package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents the requirement a Player
 * needs to have for playing a Leader card.
 */
public class Requirement implements Serializable {
    
    private static final long serialVersionUID = 3560238594806409213L;
    
    private int buildingCards;
    private int characterCards;
    private int territoryCards;
    private int ventureCards;
    private boolean any;
    private int anyValue;
    private Resources resources;

    /**
     * @param buildingCards the player's Building cards
     * @param characterCards the player's Character cards
     * @param territoryCards the player's Territory cards
     * @param ventureCards the player's VentureCards
     * @param any boolean that indicates if requirement is of type "You need x Development cards of the same type"
     * @param anyValue x Development Cards needed
     * @param resources the resources needed
     */
    public Requirement(int buildingCards, int characterCards, int territoryCards, int ventureCards, boolean any,
            int anyValue, Resources resources) {
        this.buildingCards = buildingCards;
        this.characterCards = characterCards;
        this.territoryCards = territoryCards;
        this.ventureCards = ventureCards;
        this.any = any;
        this.anyValue = anyValue;
        this.resources = resources;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requirement: ");

        if (any) {
            stringBuilder.append(anyValue).append(" total development cards");
        } else {
            stringBuilder.append("Resources: ").append(resources.toString()).append("\n");
            stringBuilder.append("Building cards: ").append(buildingCards).append(", ");
            stringBuilder.append("Character cards: ").append(characterCards).append(", ");
            stringBuilder.append("Territory cards: ").append(territoryCards).append(", ");
            stringBuilder.append("Venture cards: ").append(ventureCards).append(", ");
        }
        return stringBuilder.toString();
    }

    /**
     * @param player the player.
     * @return true if the Player meets the requirement
     *         false otherwise
     */
    public boolean checkPlayable(Player player) {
        if (any) {
            return player.getPersonalBoard().getBuildings().size() >= anyValue
                    || player.getPersonalBoard().getCharacters().size() >= anyValue
                    || player.getPersonalBoard().getTerritories().size() >= anyValue
                    || player.getPersonalBoard().getVentures().size() >= anyValue;
        } else {
            return player.getPersonalBoard().getBuildings().size() >= buildingCards &&
                   player.getPersonalBoard().getCharacters().size() >= characterCards &&
                   player.getPersonalBoard().getTerritories().size() >= territoryCards &&
                   player.getPersonalBoard().getVentures().size() >= ventureCards &&
                   Resources.compare(player, resources);
        }
    }
    
}
