package it.polimi.ingsw.GC_18.model.resources;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;

/**
 * This class represents the council privileges resource.
 */
public class CouncilPrivileges extends Resource implements Blocking {

    private static final long serialVersionUID = 1798731397089471764L;

    private static final int COUNCIL_PRIVILEGES_OPTIONS = 5;

    /**
     * Creates a new council privilege resource type.
     * @param value the council privileges number.
     */
    public CouncilPrivileges(int value) {
        super(value);
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    /**
     * Asks the player which kind of resources wants in exchange of a council privilege.
     * @param player the player to add the resource to.
     * @param source the source from where the resource come from.
     */
    @Override
    public void addPlayer(Player player, Source source) {
        int[] possibleChoices = new int[COUNCIL_PRIVILEGES_OPTIONS];

        for (int j = 0; j < COUNCIL_PRIVILEGES_OPTIONS; j++) {
            possibleChoices[j] = 0;
        }

        for (int i = 0; i < value;) {

            String response = block(player, WaitingCommand.COUNCIL_PRIVILEGES);
            // the -1 is due to the fact that the index starts from 0
            if (possibleChoices[Integer.parseInt(response)- 1] != 1) {
                possibleChoices[Integer.parseInt(response)- 1] = 1;
                giveOptionChosen(response, player);
                i++;
            } else {
                player.getController().getGame().getGameController().notifyPlayer(player, "You have already chose this council privileges reward option");
            }
        }
    }

    /**
     * @param player the player to subtract the resource to.
     */
    @Override
    public void subtractPlayer(Player player) {
        player.subtractCouncilPrivileges(value);
    }

    /**
     * @param player the player to set the resource.
     */
    @Override
    public void setPlayer(Player player) {
        // council privileges are never set to a player
    }

    @Override
    public ResourceType getType() {
        return null;
    }

    @Override
    public String toString() {
        return "councilPrivileges";
    }

    /**
     * Checks which option the player choose and adds the resources accordingly
     * @param option the option choose by the player.
     * @param player the player.
     */
    private void giveOptionChosen(String option, Player player) {
        switch (option) {
        case "1":
            player.addWoods(1, null);
            player.addStones(1, null);
            break;

        case "2":
            player.addServants(2, null);
            break;

        case "3":
            player.addMoney(2, null);
            break;

        case "4":
            player.addMilitaryPoints(2, null);
            break;

        case "5":
            player.addFaithPoints(1, null);
            break;
        default:
            break;
        }

    }

    @Override
    public String toStringCli() {
        return "Council Privileges";
    }
}
