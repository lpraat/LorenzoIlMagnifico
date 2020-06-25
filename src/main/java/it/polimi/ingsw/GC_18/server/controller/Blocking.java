package it.polimi.ingsw.GC_18.server.controller;

import java.util.logging.Level;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.Effect;

/**
 * This interface needs to be implemented by model objects that need a user interaction.
 * The thread running in the model that finds a blocking object will wait till a response is sent from the controller.
 */
public interface Blocking {

    /**
     * Asks a player if it wants to apply an effect.
     * @param player the player.
     * @param effect the effect.
     * @return true if the player wants to apply the effect, false otherwise.
     */
    default boolean ask(Player player, Effect effect) {
        try {
            synchronized (getLock()) {
                player.getController().set(WaitingCommand.ASK_ACTIVATION, this);
                player.getController().getGame().getGameController().notifyPlayer(player, "CHOOSE - STRING - Do you want to activate the effect: " + effect.toString().replaceAll("\n", " ") + " ? - YES - NO");
                while (player.getController().isWaiting()) {
                    wait();
                }
                return !"NO".equals(player.getController().getResponse());
            }
        } catch (InterruptedException e) {
            ModelLogger.getInstance().log(Level.WARNING, "Thread interrupted", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Asks the player for a specific response for a waiting command.
     * @param player the player.
     * @param waitingCommand the waiting command.
     * @return the response.
     */
    default String block(Player player, WaitingCommand waitingCommand) {
        try {
            synchronized (getLock()) {
                player.getController().set(waitingCommand, this);
                player.getController().getGame().getGameController().notifyPlayer(player, "CHOOSE - " + switchDescription(player, waitingCommand));
                while (player.getController().isWaiting()) {
                    wait();
                }
                return player.getController().getResponse();
            }
        } catch (InterruptedException e) {
            ModelLogger.getInstance().log(Level.WARNING, "Thread interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Asks the player for a specific response for a waiting command.
     * @param player the player.
     * @param waitingCommand the waiting command.
     * @param message the message to send to the client.
     * @return the response.
     */
    default String block(Player player, WaitingCommand waitingCommand, String message) {
        try {
            synchronized (getLock()) {
                player.getController().set(waitingCommand, this);
                player.getController().getGame().getGameController().notifyPlayer(player, "CHOOSE - "+message);
                while (player.getController().isWaiting()) {
                    wait();
                }
                return player.getController().getResponse();
            }
        } catch (InterruptedException e) {
            ModelLogger.getInstance().log(Level.WARNING, "Thread interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }
    
    /**
     * Based on the waiting command received as a parameter it handles the string to send to the client in 
     * order to open the right interaction frame for that action
     * @param player - the player to notify
     * @param waitingCommand - the command that is expected
     * @return the description to send to the client
     */
    static String switchDescription(Player player, WaitingCommand waitingCommand) {
        switch (waitingCommand) {
        case ASK_ACTIVATION:
            return "CHOOSE - STRING - ASK_ACTIVATION, Do you want to activate? - YES - NO";
        case ASK_SUPPORT:
            return "STRING - ASK_SUPPORT, Do you want to support the vatican? - YES - NO";
        case COUNCIL_PRIVILEGES:
            return "INT - COUNCIL_PRIVILEGES, Which privilege you'd like?(1 for 1 wood 1 stone, 2 for 2 servants, 3 for 2 money, 4 for 2 military points, 5 for 2 faith points) - 1 - 5";
        case EXCHANGE_OR:
            return "INT - EXCHANGE_OR, Which exchange do you want to make? - 1 - 2";
        case EXTRA_ANY:
            return "EXTRA_PICK - EXTRA_ANY, Select the floor of a tower and the servants to spend: - 0 - "+player.getServants()+" - BUILDING1 - BUILDING2 - BUILDING3 - BUILDING4 - CHARACTER1 - CHARACTER2 - CHARACTER3 - CHARACTER4 - TERRITORY1 - TERRITORY2 - TERRITORY3 - TERRITORY4 - VENTURE1 - VENTURE2 - VENTURE3 - VENTURE4 - SKIP";
        case EXTRA_BUILDING:
            return "EXTRA_PICK - EXTRA_BUILDING, Select the floor of the building tower and the servants to spend: - 0 - "+player.getServants()+" - BUILDING1 - BUILDING2 - BUILDING3 - BUILDING4 - SKIP";
        case EXTRA_CHARACTER:
            return "EXTRA_PICK - EXTRA_CHARACTER, Select the floor of the character tower and the servants to spend: - 0 - "+player.getServants()+" - CHARACTER1 - CHARACTER2 - CHARACTER3 - CHARACTER4 - SKIP";
        case EXTRA_HARVEST:
            return "INT - EXTRA_HARVEST, How many servants you wish to spend for the extra harvest? - 0 - "+player.getServants();
        case EXTRA_PRODUCTION:
            return "INT - EXTRA_PRODUCTION, How many servants you wish to spend for the extra production? - 0 - "+player.getServants();
        case EXTRA_TERRITORY:
            return "EXTRA_PICK - EXTRA_TERRITORY, Select the floor of the territory tower and the servants to spend: - 0 - "+player.getServants()+" - TERRITORY1 - TERRITORY2 - TERRITORY3 - TERRITORY4 - SKIP";
        case EXTRA_VENTURE:
            return "EXTRA_PICK - EXTRA_VENTURE, Select the floor of the venture tower and the servants to spend: - 0 - "+player.getServants()+" - VENTURE1 - VENTURE2 - VENTURE3 - VENTURE4 - SKIP";
        case FLOORCHANGE_OR:
            return "INT - FLOORCHANGE_OR, Which discount you want? - 1 - 2";
        case LEADER_COPY:
            return "INT - LEADER_COPY, Choose a leader to copy between: "+player.getController().getGame().getLeadersPlayed(player).stream()
                    .map(leader -> player.getController().getGame().getLeadersPlayed(player).indexOf(leader)+1 + "-" + leader.getName())
                    .collect(Collectors.joining("\n"))+" - 1 - "+player.getController().getGame().getLeadersPlayed(player).size();
        case PAWN_CHOOSE:
            return "STRING - PAWN_CHOOSE, Which pawn do you choose? - BLACK - WHITE - ORANGE";
        case VENTURE_COST:
            return "STRING - VENTURE_COST, Which kind venture cost you want to spend? - NORMAL - ALTERNATIVE";
        default:
            break;
        }
        return null;
    }

    /**
     * @return the blocking object.
     */
    Blocking getLock();

}
