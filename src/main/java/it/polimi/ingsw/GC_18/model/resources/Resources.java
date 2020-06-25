package it.polimi.ingsw.GC_18.model.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;

/**
 * This class represents a group of listOfResources.
 */
public class Resources implements Serializable {
    
    private static final long serialVersionUID = -8705850419449833446L;
    
    private static final int WOODS_INDEX = 0;
    private static final int STONES_INDEX = 1;
    private static final int SERVANTS_INDEX = 2;
    private static final int MONEY_INDEX = 3;
    private static final int MILITARYPOINTS_INDEX = 4;
    private static final int FAITHPOINTS_INDEX = 5;
    private static final int COUNCILPRIVILEGES_INDEX = 6;
    private static final int VICTORYPOINTS_INDEX = 7;
    private ArrayList<Resource> listOfResources;

    /**
     * @param woods the woods resource.
     * @param stones the stones resource.
     * @param servants the servants resource.
     * @param money the money resource.
     * @param militaryPoints the militaryPoints resource.
     * @param faithPoints the faithPoints resource.
     * @param councilPrivileges the councilPrivileges resource.
     * @param victoryPoints the victoryPoints resource.
     */

    public Resources(Woods woods, Stones stones, Servants servants, Money money, MilitaryPoints militaryPoints,
                     FaithPoints faithPoints, CouncilPrivileges councilPrivileges, VictoryPoints victoryPoints) {
        listOfResources = new ArrayList<>();
        listOfResources.add(woods);
        listOfResources.add(stones);
        listOfResources.add(servants);
        listOfResources.add(money);
        listOfResources.add(militaryPoints);
        listOfResources.add(faithPoints);
        listOfResources.add(councilPrivileges);
        listOfResources.add(victoryPoints);
    }



    /**
     * Adds the Resources to the player.
     * @param player the player to add the Resources to.
     * @param source the source from where the Resources come from.
     */
    public void addResources(Player player, Source source) {
        for (Resource resource: listOfResources) {
            if (resource.getValue() > 0)
                resource.addPlayer(player, source);
        }
    }

    /**
     * @return the list of the resource.
     */
    public List<Resource> getResourcesList() {
        return listOfResources;
    }

    /**
     * Subtract the resources to the player.
     * @param player the player.
     */
    public void subtractResources(Player player) {
        for (Resource resource: listOfResources) {
            if (player.isChainActive()) {
                for (Resource bufferedResource : player.getBufferedResources().getResourcesList()) {
                    if (bufferedResource.getType() != null && resource.getType() != null && bufferedResource.getType().equals(resource.getType())) {
                        bufferedResource.subtract(resource.getValue());
                    }
                }
            }
            resource.subtractPlayer(player);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        listOfResources.stream()
                .filter(resource -> resource.getValue() != 0)
                .forEach(resource -> stringBuilder.append(resource.toStringCli()).append(":").append(resource.getValue()).append(" "));
        return stringBuilder.toString();
    }

    /**
     * @param player the player.
     * @param resources the resources.
     * @return true if the player has enough resources.
     */
    public static boolean compare(Player player, Resources resources) {
        boolean check = true;
        List<Resource> playerResourcesList;
        List<Resource> resourcesList = resources.getResourcesList();

        if (player.isChainActive()) {
            playerResourcesList = player.getBufferedResources().getResourcesList();
        } else {
            playerResourcesList = player.getResources().getResourcesList();
        }

        for (int i = 0; i < playerResourcesList.size(); i++) {
            Resource resource = resourcesList.get(i);
            Resource resourcePlayer = playerResourcesList.get(i);

            if (resource.getValue() > 0) {
                check = resourcePlayer.getValue() >= resource.getValue();
                if (!check) {
                    return false;
                }
            }
        }
        return check;
    }
    /**
     * @return the woods.
     */
    public int getWoods() {
        return listOfResources.get(WOODS_INDEX).getValue();
    }

    /**
     * @return the stones.
     */
    public int getStones() {
        return listOfResources.get(STONES_INDEX).getValue();
    }

    /**
     * @return the servants.
     */
    public int getServants() {
        return listOfResources.get(SERVANTS_INDEX).getValue();
    }

    /**
     * @return the money.
     */
    public int getMoney() {
        return listOfResources.get(MONEY_INDEX).getValue();
    }

    /**
     * @return the military points.
     */
    public int getMilitaryPoints() {
        return listOfResources.get(MILITARYPOINTS_INDEX).getValue();
    }

    /**
     * @return the faith points.
     */
    public int getFaithPoints() {
        return listOfResources.get(FAITHPOINTS_INDEX).getValue();
    }

    /**
     * @return the council privileges.
     */
    public int getCouncilPrivileges() {
        return listOfResources.get(COUNCILPRIVILEGES_INDEX).getValue();
    }

    /**
     * @return the victory points.
     */
    public int getVictoryPoints() {
        return listOfResources.get(VICTORYPOINTS_INDEX).getValue();
    }

}