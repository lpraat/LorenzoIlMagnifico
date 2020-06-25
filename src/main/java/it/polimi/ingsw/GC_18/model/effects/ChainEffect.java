package it.polimi.ingsw.GC_18.model.effects;

/**
 * This interface represents a permanent effect that is a part of a chain of effect activations.
 */
public interface ChainEffect extends PermanentEffect {
    /**
     * @param actionValue the action value that starts the chain.
     * @return true if the effect can be activated.
     */
    boolean isActivable(int actionValue);
}
