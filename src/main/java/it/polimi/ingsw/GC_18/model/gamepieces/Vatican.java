package it.polimi.ingsw.GC_18.model.gamepieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;

/**
 * This class represents the Vatican church in the board. The church has the faith track
 * of the board.
 */

public class Vatican implements Serializable {

    private static final long serialVersionUID = 1707298466329581812L;
    private static final int FAITH_POINT_REQUIRED_1 = 3;
    private static final int FAITH_POINT_REQUIRED_2 = 4;
    private static final int FAITH_POINT_REQUIRED_3 = 5;

    private List<VaticanSpot> vaticanSpots;
    private HashMap<Integer, Resources> faithTrack;
    private boolean doneReporting;

    /**
     * Creates a new vatican church.
     */
    public Vatican() {
        vaticanSpots = new ArrayList<>();
        vaticanSpots.add(new VaticanSpot(FAITH_POINT_REQUIRED_1));
        vaticanSpots.add(new VaticanSpot(FAITH_POINT_REQUIRED_2));
        vaticanSpots.add(new VaticanSpot(FAITH_POINT_REQUIRED_3));
        faithTrack = GameConfigLoader.loadFaithTrack();
        doneReporting = true;
    }

    /**
     * Does the vatican report.
     * @param period the period of the report.
     * @param players the players of the report.
     */
    public void report(int period, List<Player> players) {
        doneReporting = false;
        for (Player player: players) {
            getVaticanSpot(period).report(period, player, faithTrack);
        }
        doneReporting = true;
    }

    /**
     * @param period the period.
     * @return the vatican spot according to the period.
     */
    private VaticanSpot getVaticanSpot(int period) {
        return vaticanSpots.get(period);
    }


    /**
     * @return true if the church report is running, false otherwise.
     */
    public boolean isDoneReporting() {
        return doneReporting;
    }

    /**
     * @return the first vatican spot.
     */
    public VaticanSpot getFirstVatican() {
        return vaticanSpots.get(0);
    }

    /**
     * @return the second vatican spot.
     */
    public VaticanSpot getSecondVatican() {
        return vaticanSpots.get(1);

    }

    /**
     * @return the third vatican spot.
     */
    public VaticanSpot getThirdVatican() {
        return vaticanSpots.get(2);
    }
    
}
