package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.ExcomunicationTile;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.LeaderStatus;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.effects.EndgameEffect;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;
import it.polimi.ingsw.GC_18.model.effects.HarvestEffect;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.effects.ProductionEffect;
import it.polimi.ingsw.GC_18.model.effects.StaticEffect;
import it.polimi.ingsw.GC_18.model.effects.statics.EndGameNegate;
import it.polimi.ingsw.GC_18.model.effects.statics.PlaceNegate;
import it.polimi.ingsw.GC_18.model.effects.statics.ServantMalus;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;
import it.polimi.ingsw.GC_18.utils.GameUtils;

/**
 * This class represents the personal board of a player. It keeps all the development cards of the player,
 * all the excomunication tiles the player got because of a report and all of the player's leader in hand or played.
 * It keeps all the player permanent effects.
 */
public class PersonalBoard implements Serializable {
    
    private static final long serialVersionUID = -6304531485973790869L;
    
    private List<Character> characters;
    private List<Venture> ventures;
    private List<Building> buildings;
    private List<Territory> territories;
    private List<Leader> leadersHand;
    private List<Leader> leadersPlayed;
    private List<ExcomunicationTile> excomunicationTiles;
    private List<HarvestEffect> harvestEffects;
    private List<ProductionEffect> productionEffects;
    private List<EndgameEffect> endgameEffects;
    private List<GameEffect> gameEffects;
    private List<DynamicEffect> dynamicEffects;
    private Map<String, StaticEffect> staticEffects;
    private List<OncePerTurnEffect> oncePerTurnEffects;
    private Map<Integer, Resources> territoryCosts;
    private Map<Integer, Resources> conqueredTerritoriesBonus;
    private Map<Integer, Resources> influencedCharactersBonus;
    private Player player;

    /**
     * Creates a new PersonalBoard for the player.
     * @param player the player.
     */
    public PersonalBoard(Player player) {
        this.player = player;
        characters = new ArrayList<>();
        ventures = new ArrayList<>();
        buildings = new ArrayList<>();
        territories = new ArrayList<>();
        leadersHand = new ArrayList<>();
        leadersPlayed = new ArrayList<>();
        productionEffects = new ArrayList<>();
        harvestEffects = new ArrayList<>();
        dynamicEffects = new ArrayList<>();
        staticEffects = new HashMap<>();
        endgameEffects = new ArrayList<>();
        oncePerTurnEffects = new ArrayList<>();
        gameEffects = new ArrayList<>();
        excomunicationTiles = new ArrayList<>();
        territoryCosts = GameConfigLoader.loadTerritoryCosts();
        conqueredTerritoriesBonus = GameConfigLoader.loadConqueredTerritoriesBonus();
        influencedCharactersBonus = GameConfigLoader.loadInfluencedCharactersBonus();
    }

    /**
     * This method is used for adding the end game bonus for the territories the player has.
     */
    public void calculateConqueredTerritoriesBonus() {
        conqueredTerritoriesBonus.get(territories.size()).addResources(player, null);
    }

    /**
     * This method is used for adding the end game bonus for the territories the player has.
     */
    public void calculateInfluencedCharactersBonus() {
        influencedCharactersBonus.get(characters.size()).addResources(player, null);
    }

    /**
     * This method is used for adding the end game bonus for the venture the player has.
     */
    public void calculateEncouragedVentures() {
        for (EndgameEffect endgameEffect: endgameEffects) {
            endgameEffect.apply(player);
        }
    }

    /**
     * This method is used for adding the end game bonus for the resources the player has.
     */
    public void calculateCollectedResources() {
        int totalCollected = 0;
        totalCollected += player.getResources().getMoney();
        totalCollected += player.getResources().getStones();
        totalCollected += player.getResources().getMoney();
        totalCollected += player.getResources().getServants();
        new VictoryPoints(totalCollected / 5).addPlayer(player, null);
    }

    /**
     * @return tha map representing the territory pick costs.
     */
    public Map<Integer, Resources> getTerritoryCosts() {
        return territoryCosts;
    }

    /**
     * @return true if the player has a TurnNegate effect.
     */
    public boolean checkTurnNegate() {
        return staticEffects.containsKey("turnNegate");
    }

    /**
     * @return true if the player has a NoHighFloorBonus effect.
     */
    public boolean checkNoHighFloorBonus() {
        return staticEffects.containsKey("noHighFloorBonus");
    }

    /**
     * @return true if the player has a NoMilitaryForTerritory.
     */
    public boolean checkNoMilitaryForTerritory() {
        return staticEffects.containsKey("noMilitaryForTerritory");
    }

    /**
     * @param actionPlace the place to check for the negate.
     * @return true if the player has a PlaceNegate effect.
     */
    public boolean checkPlaceNegate(ActionPlace actionPlace) {
        if (staticEffects.containsKey("placeNegate")) {
            PlaceNegate placeNegate = (PlaceNegate) staticEffects.get("placeNegate");
            return placeNegate.isPresent(actionPlace.getPlaceName());
        }
        return false;
    }

    /**
     * @return the servant malus if a player has one.
     */
    public int getServantMalus() {
        if (staticEffects.containsKey("servantMalus")) {
            ServantMalus servantMalus = (ServantMalus) staticEffects.get("servantMalus");
            return servantMalus.getValue();
        }
        return 0;
    }

    /**
     * @param towerColor the tower to check.
     * @return true if the player has a EndGamNegate effect.
     */
    public boolean checkEndGameNegate(TowerColor towerColor) {
        if (staticEffects.containsKey("endGameNegate")) {
            EndGameNegate endGameNegate = (EndGameNegate) staticEffects.get("endGameNegate");
            return endGameNegate.getTowerColor() == towerColor;
        }
        return false;
    }

    /**
     * Return a string containing leader according to the status given.
     * @param leaderStatus the leader status.
     * @return a numerated string of the leaders.
     */
    public String showLeaders(LeaderStatus leaderStatus) {
        List<Leader> leaders;
        if (LeaderStatus.HAND.equals(leaderStatus)) {
            leaders = leadersHand;
        } else {
            leaders = leadersPlayed;
        }
        return GameUtils.stringFromList(leaders);
    }

    /**
     * @return a numerated string of the once per turn effects.
     */
    public String showOnces() {
        return GameUtils.stringFromList(oncePerTurnEffects);
    }

    /**
     * @return true if the player has a PlaceInOccupied effect.
     */
    public boolean checkPlaceInOccupied() {
        return staticEffects.containsKey("placeInOccupied");
    }

    /**
     * @return true if the player has a NoMoneyCostPlace effect.
     */
    public boolean checkNoMoneyCostPlace() {
        return staticEffects.containsKey("noMoneyCostPlace");
    }

    /**
     * @return the character cards.
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * @return the venture cards.
     */
    public List<Venture> getVentures() {
        return ventures;
    }

    /**
     * @return the building cards.
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * @return the territories cards.
     */
    public List<Territory> getTerritories() {
        return territories;
    }

    /**
     * @return the leaders in hand.
     */
    public List<Leader> getLeadersHand() {
        return leadersHand;
    }

    /**
     * @return the leaders played.
     */
    public List<Leader> getLeadersPlayed() {
        return leadersPlayed;
    }

    /**
     * @return the excomunication tiles the player has.
     */
    public List<ExcomunicationTile> getExcomunicationTiles() {
        return excomunicationTiles;
    }

    /**
     * @return the harvest effects of the player.
     */
    public List<HarvestEffect> getHarvestEffects() {
        return harvestEffects;
    }

    /**
     * @return the production effects of the player.
     */
    public List<ProductionEffect> getProductionEffects() {
        return productionEffects;
    }

    /**
     * @return the end game effects of the player.
     */
    public List<EndgameEffect> getEndgameEffects() {
        return endgameEffects;
    }

    /**
     * @return the dynamic effects of the player.
     */
    public List<DynamicEffect> getDynamicEffects() {
        return dynamicEffects;
    }

    /**
     * @return the static effects of the player.
     */
    public Map<String, StaticEffect> getStaticEffects() {
        return staticEffects;
    }

    /**
     * @return the once per turn effects of the player.
     */
    public List<OncePerTurnEffect> getOncePerTurnEffects() {
        return oncePerTurnEffects;
    }

    /**
     * @return the game effects of the player.
     */
    public List<GameEffect> getGameEffects() {
        return gameEffects;
    }

}
