package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.gamepieces.BuildingTower;
import it.polimi.ingsw.GC_18.model.gamepieces.CharacterTower;
import it.polimi.ingsw.GC_18.model.gamepieces.CouncilPalace;
import it.polimi.ingsw.GC_18.model.gamepieces.DicesBox;
import it.polimi.ingsw.GC_18.model.gamepieces.FightSpace;
import it.polimi.ingsw.GC_18.model.gamepieces.HarvestSpace;
import it.polimi.ingsw.GC_18.model.gamepieces.Market;
import it.polimi.ingsw.GC_18.model.gamepieces.ProductionSpace;
import it.polimi.ingsw.GC_18.model.gamepieces.TerritoryTower;
import it.polimi.ingsw.GC_18.model.gamepieces.Vatican;
import it.polimi.ingsw.GC_18.model.gamepieces.VentureTower;

/**
 * This class represents the game board. It contains the towers, the market and the harvest
 * and production spaces. Also keeps track of the decks the game will pick card from.
 */
public class Board implements Serializable {

    private static final int MAX_NUM_OF_PLAYERS = 5;
    private static final long serialVersionUID = 6164644757542831021L;
    
    private TerritoryTower territoryTower;
    private BuildingTower buildingTower;
    private CharacterTower characterTower;
    private VentureTower ventureTower;
    private DicesBox dicesBox;
    private Market market;
    private CouncilPalace councilPalace;
    private Vatican vatican;
    private ProductionSpace productionSpace;
    private HarvestSpace harvestSpace;
    private Deck<Character> currentCharacterDeck;
    private Deck<Building> currentBuildingDeck;
    private Deck<Territory> currentTerritoryDeck;
    private Deck<Venture> currentVentureDeck;
    private FightSpace fightSpace;

    /**
     * It creates all the spaces that need to be create for a numPlayers number of players game.
     * @param playerList the list of player.
     */
    public Board(List<Player> playerList) {
        territoryTower = new TerritoryTower();
        buildingTower = new BuildingTower();
        characterTower = new CharacterTower();
        ventureTower = new VentureTower();

        dicesBox = new DicesBox(new Dice(DiceColor.BLACK), new Dice(DiceColor.WHITE), new Dice(DiceColor.ORANGE));
        market = new Market(playerList.size());
        councilPalace = new CouncilPalace();
        vatican=new Vatican();

        productionSpace = new ProductionSpace(playerList.size());
        harvestSpace = new HarvestSpace(playerList.size());



        if (playerList.size() >= MAX_NUM_OF_PLAYERS) {
            fightSpace = new FightSpace(playerList);
        }
    }


    /**
     * Sets the character's deck for the current period.
     * @param currentCharacterDeck the character deck to be set.
     */
    void setCurrentCharacterDeck(Deck<Character> currentCharacterDeck) {
        this.currentCharacterDeck = currentCharacterDeck;
    }

    /**
     * Sets the building's deck for the current period.
     * @param currentBuildingDeck the building deck to be set.
     */
    void setCurrentBuildingDeck(Deck<Building> currentBuildingDeck) {
        this.currentBuildingDeck = currentBuildingDeck;
    }

    /**
     * Sets the territory's deck for the current period.
     * @param currentTerritoryDeck the territory deck to be set.
     */
    void setCurrentTerritoryDeck(Deck<Territory> currentTerritoryDeck) {
        this.currentTerritoryDeck = currentTerritoryDeck;
    }

    /**
     * Sets the venture's deck for the current period.
     * @param currentVentureDeck the venture deck to be set.
     */
    void setCurrentVentureDeck(Deck<Venture> currentVentureDeck) {
        this.currentVentureDeck = currentVentureDeck;
    }

    /**
     * @return the current character deck.
     */
    Deck<Character> getCurrentCharacterDeck() {
        return currentCharacterDeck;
    }

    /**
     * @return the current building deck.
     */
    Deck<Building> getCurrentBuildingDeck() {
        return currentBuildingDeck;
    }

    /**
     * @return the current territory deck.
     */
    Deck<Territory> getCurrentTerritoryDeck() {
        return currentTerritoryDeck;
    }

    /**
     * @return the current venture deck.
     */
    Deck<Venture> getCurrentVentureDeck() {
        return currentVentureDeck;
    }

    /**
     * @return the territory tower.
     */
    public TerritoryTower getTerritoryTower() {
        return territoryTower;
    }

    /**
     * @return the building tower.
     */
    public BuildingTower getBuildingTower() {
        return buildingTower;
    }

    /**
     * @return the character tower.
     */
    public CharacterTower getCharacterTower() {
        return characterTower;
    }

    /**
     * @return the venture tower.
     */
    public VentureTower getVentureTower() {
        return ventureTower;
    }

    /**
     * @return the dices box to be set.
     */
    public DicesBox getDicesBox() {
        return dicesBox;
    }

    /**
     * @return the market.
     */
    public Market getMarket() {
        return market;
    }

    /**
     * @return the council palace.
     */
    public CouncilPalace getCouncilPalace() {
        return councilPalace;
    }

    /**
     * @return the vatican.
     */
    public Vatican getVatican() {
        return vatican;
    }

    /**
     * @return the production space.
     */
    public ProductionSpace getProductionSpace() {
        return productionSpace;
    }

    /**
     * @return the harvest space.
     */
    public HarvestSpace getHarvestSpace() {
        return harvestSpace;
    }

    /**
     * @return the fight space.
     */
    public FightSpace getFightSpace() {
        return fightSpace;
    }
}
