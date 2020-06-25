package it.polimi.ingsw.GC_18.model.notifiers;

import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.resources.Resource;
import it.polimi.ingsw.GC_18.model.resources.ResourceType;

/**
 * This class represents a notify resource.
 */
public class NotifyResource {
    private Source source;
    private int value;
    private Resource resource;

    /**
     * Creates a new resource notify.
     * @param resource the resources for which the notify is created.
     * @param source the source from where the notify comes from.
     */
    public NotifyResource(Resource resource, Source source) {
        this.resource = resource;
        this.source = source;
        this.value = resource.getValue();
    }


    /**
     * @return the resource of the notify.
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @return the type of the resource.
     */
    public ResourceType getResourceType() {
            return resource.getType();
    }

    /**
     * @return the source from where the resource comes from.
     */
    public Source getSource() {
        return source;
    }

    /**
     * @return the value of the resource.
     */
    public int getValue() {
        return value;
    }
}