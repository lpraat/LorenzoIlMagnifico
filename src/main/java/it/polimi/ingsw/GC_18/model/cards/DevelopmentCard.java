package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;

import it.polimi.ingsw.GC_18.model.effects.ImmediateEffect;
import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This abstract class represents a Development card
 */
public abstract class DevelopmentCard extends Card implements Serializable {

    private static final long serialVersionUID = -8288860534420329703L;
    static final String IMMEDIATE_EFFECT = "immediateEffect";
    static final String PERMANENT_EFFECT = "permanentEffect";
    static final String COST = "cost";

    Resources cost;
    ImmediateEffect immediateEffect;

    /**
     * @return the cost of the Development card
     */
    public Resources getCost() {
        return cost;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(name).append("\n");

        if (cost != null) {
            stringBuilder.append("Cost: ").append(cost.toString()).append("\n");
        }

        if (immediateEffect != null) {
            stringBuilder.append("Immediate Effect: ").append(immediateEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}