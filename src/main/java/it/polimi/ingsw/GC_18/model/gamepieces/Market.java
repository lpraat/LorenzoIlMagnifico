package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the Market in the board.
 * It contains a coin spot, a servant spot, a military spot, and a council spot.
 */
public class Market implements Serializable {

    private static final long serialVersionUID = -5730352038613073356L;
    
    private CoinSpot coinSpot;
    private ServantSpot servantSpot;
    private MilitarySpot militarySpot;
    private CouncilSpot councilSpot;

    /**
     * Creates a new market according to the number of players in the game. There may not be
     * some spots because the number of players is not enough.
     * @param numPlayers the number of players
     */
    public Market(int numPlayers) {
        coinSpot = new CoinSpot(GameConfigLoader.loadPlaceBonus("coinSpot"),
                                GameConfigLoader.loadPlaceValue("coinSpot"));
        servantSpot = new ServantSpot(GameConfigLoader.loadPlaceBonus("servantSpot"),
                                      GameConfigLoader.loadPlaceValue("servantSpot"));
        if (numPlayers >= 4) {
            militarySpot = new MilitarySpot(GameConfigLoader.loadPlaceBonus("militarySpot"),
                                            GameConfigLoader.loadPlaceValue("militarySpot"));
            councilSpot = new CouncilSpot(GameConfigLoader.loadPlaceBonus("councilSpot"),
                                          GameConfigLoader.loadPlaceValue("councilSpot"));
        }
    }

    /**
     * @return the coin spot.
     */
    public CoinSpot getCoinSpot() {
        return coinSpot;
    }

    /**
     * @return the servant spot.
     */
    public ServantSpot getServantSpot() {
        return servantSpot;
    }

    /**
     * @return the military spot.
     */
    public MilitarySpot getMilitarySpot() {
        return militarySpot;
    }

    /**
     * @return the council spot.
     */
    public CouncilSpot getCouncilSpot() {
        return councilSpot;
    }

}
