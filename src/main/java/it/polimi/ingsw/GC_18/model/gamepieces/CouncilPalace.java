package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the council palace. It's used by the turn handler for checking
 * if a turn reschedule is needed.
 */
public class CouncilPalace extends ActionPlace implements Serializable {
    
    private static final long serialVersionUID = -4569211216104976373L;
    
    private List<Player> scheduledPlayers;

    /**
     * Creates a new council palace.
     */
    public CouncilPalace() {
        super(GameConfigLoader.loadPlaceBonus("councilPalace"), GameConfigLoader.loadPlaceValue("councilPalace"));
        pawns = new ArrayList<>();
        scheduledPlayers = new ArrayList<>();
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    /**
     * Sets the pawn and change the turn schedule.
     * @param pawn the pawn placed.
     */
    @Override
    public void setPawn(Pawn pawn) {
        pawn.setPlaced(true);
        pawns.add(pawn);
        addSchedule(pawn.getPlayer());
    }

    /**
     * Resets the turn schedule.
     */
    public void reset() {
        pawns = new ArrayList<>();
        scheduledPlayers = new ArrayList<>();
    }

    /**
     * Adds the player to the turn reschedule.
     * @param player the player.
     */
    private void addSchedule(Player player) {
        if (!scheduledPlayers.contains(player)) {
            scheduledPlayers.add(player);
        }
    }

    /**
     * @return the turn schedule.
     */
    public List<Player> getScheduledPlayers() {
        return scheduledPlayers;
    }

    @Override
    public PlaceName getPlaceName() {
        return PlaceName.COUNCIL_PALACE;
    }
    
}
