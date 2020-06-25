package it.polimi.ingsw.GC_18.model.effects.dynamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.notifiers.NotifyResource;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This class represents a dynamic effect that multiplies the resources given to a Player when he receives
 * resources from a game Source.
 */
public class ResourcesMultiplier extends ResourcesChange implements Serializable {
    
    private static final long serialVersionUID = 3743147224784678774L;
    
    private int multiplier;

    /**
     * @param resources the resources to add
     * @param source the source from where the external resources come from
     * @param multiplier the multiply factor
     */
    public ResourcesMultiplier(Resources resources, ArrayList<Source> source, int multiplier) {
        super(resources, source);
        this.multiplier = multiplier;
    }

    /**
     * Using a NotifyResource applies the effect.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            NotifyResource notifyResource = (NotifyResource) arg;
            Source notifySource = notifyResource.getSource();
            for (Source s : source) {
                if (s.equals(notifySource)) {
                    List<Resource> resourcesList = resources.getResourcesList();
                    Player player = (Player) o;
                    for (Resource resource: resourcesList) {
                        if (notifyResource.getResourceType() == resource.getType() && resource.getValue() != 0) {
                            for (int i = 1; i < multiplier; i++) {
                                notifyResource.getResource().addPlayer(player, null);
                            }
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
        return "Multiplies resources of " + multiplier+ " after getting resources from " + stringBuilder.toString() + " \n" + resources.toString();
    }
    
}
