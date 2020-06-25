package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.Source;

/**
 * This class represents a game object that wants to notify its source
 * after changing resources in the game model.
 */
public class SourceNotifier implements Serializable {
    
    private static final long serialVersionUID = 9104069241739355081L;
    
    protected Source source;

    /**
     * @return the source of the change.
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the source of the change.
     * @param source the source to be set.
     */
    public void setSource(Source source) {
        this.source = source;
    }
    
}
