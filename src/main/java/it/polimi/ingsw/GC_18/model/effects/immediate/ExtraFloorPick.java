package it.polimi.ingsw.GC_18.model.effects.immediate;

import java.io.Serializable;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.server.controller.Parser;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.actions.ExtraTowerAction;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents an immediate effect that gives the player a possibility to perform
 * an extra TowerAction without placing the pawn.
 */
public class ExtraFloorPick implements ImmediateEffect, Blocking, Serializable {
    
    private static final long serialVersionUID = 5716242591833422035L;
    
    private TowerColor color;
    private int value;
    private Resources resources;

    /**
     * Creates a new ExtraFloorPick effect.
     * @param color the color of the tower the player can do the extra tower action on.
     * @param value the value of the extra tower action.
     * @param resources the discount given by the extra tower action.
     */
    public ExtraFloorPick(TowerColor color, int value, Resources resources) {
        this.color = color;
        this.value = value;
        this.resources = resources;
    }

    /**
     * @return the tower waiting command.
     */
    private WaitingCommand calculateCommand() {
        return WaitingCommand.getFloorCommand(color);
    }

    @Override
    public void apply(Player player) {
        if (!ask(player, this))
            return;

        while (true) {
            String response = block(player, calculateCommand());
            if ("SKIP".equalsIgnoreCase(response)) {
                player.getController().getGame().getGameController().notifyPlayer(player, "Skipped extra floor");
                break;
            }
            String[] responseSplit = response.split(" - ");
            @SuppressWarnings("unchecked")
            ExtraTowerAction extraTowerAction = new ExtraTowerAction(player, (Floor<? extends DevelopmentCard>) Parser.parseActionPlace(player, responseSplit[0]), Integer.parseInt(responseSplit[1]), value, resources);

            if (extraTowerAction.check()) {
                extraTowerAction.run();
                break;
            }
            player.getController().getGame().getGameController().notifyPlayer(player, "You can't do the extra floor pick here");
        }
    }

    @Override
    public Blocking getLock() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Gives the possibility of an extra floor action in a tower of color ").append(color.name()).append(" with a starting value of ").append(value);
        if (resources != null) {
            if (resources.getResourcesList().stream().filter(resource -> resource.getValue() != 0).collect(Collectors.toList()).size() > 0) {
                stringBuilder.append("with the following discount \n").append(resources.toString());
            }
        }
        return stringBuilder.toString();
    }
    
}