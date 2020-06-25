package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.effects.DynamicEffect;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a dynamic effect that change the resources given to a Player when he receives
 * resources from a game Source.
 */
public class ResourcesChange implements DynamicEffect, Serializable {
    
    private static final long serialVersionUID = 8292177566961887786L;
   
    Resources resources;
    ArrayList<Source> source; // here ArrayList is used instead of List because it must be Serializable

    /**
     * Creates a new ResourcesChange effect.
     * @param resources the resources to add
     * @param source the source from where the external resources come from
     */
    public ResourcesChange(Resources resources, ArrayList<Source> source) {
        this.resources = resources;
        this.source = source;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            NotifyResource notifyResource = (NotifyResource) arg;
            Source notifySource = notifyResource.getSource();
            for (Source s : source) {
                if (s.equals(notifySource)) {
                    Player player = (Player) o;
                    List<Resource> resourcesList = resources.getResourcesList();
                    for (Resource resource: resourcesList) {
                        if (notifyResource.getResourceType() == resource.getType() && resource.getValue() != 0) {
                            resource.addPlayer(player, null);
                        }
                    }
                }
            }
        } catch (ClassCastException e) {
            ModelLogger.getInstance().log(Level.INFO, "Notify thrown", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        source.forEach(source1 -> stringBuilder.append(source1.name()).append(" "));
        return "Adds the following resources after getting resources from " + stringBuilder.toString() + " \n" + resources.toString();
    }
    
}
