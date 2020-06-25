package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.HarvestEffect;
import it.polimi.ingsw.GC_18.model.effects.decorators.Harvest;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;
import it.polimi.ingsw.GC_18.utils.FromJson;

/**
 * This class represents a Territory card
 */
public class Territory extends DevelopmentCard implements Pickable, Serializable{

    private static final long serialVersionUID = -3910683612956014125L;
    private static final String ACTIVATION_VALUE = "activationValue";
    private static final String BONUS = "bonus";
    private static final String RESOURCES = "resources";
    private static final String NULL = "null";
    private static final String BONUS_H = "bonusH";

    private HarvestEffect harvestEffect;

    /**
     * Create a Territory card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public Territory(JSONObject jsonObject) {
        try {
            int activationValue = jsonObject.getInt(ACTIVATION_VALUE);
            name = jsonObject.get(NAME).toString();
            cost = FromJson.getResources((JSONObject) jsonObject.get(COST));
            String immediateEffectStr = jsonObject.getString(IMMEDIATE_EFFECT);
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (immediateEffectStr) {

                case BONUS:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS);
                    immediateEffect = new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES)));
                    break;

                case NULL:
                    break;

                default:
                    ModelLogger.getInstance().logInfo("Check your immediate effect");
                    break;

            }

            switch (permanentEffectStr) {

                case BONUS_H:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS_H);
                    harvestEffect = new Harvest(activationValue, new Bonus(FromJson.getResources(bonus.getJSONObject("resources"))));
                    break;


                case NULL:
                    break;
                default:
                    ModelLogger.getInstance().logInfo("Check your permanent effect");
            }
        } catch (JSONException e) {
            ModelLogger.getInstance().log(Level.WARNING, "exception thrown", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (harvestEffect != null) {
            stringBuilder.append("Permanent Effect: ").append(harvestEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Applies the Immediate effect and adds the Permanent effect
     * of the Territory card to the player, if they are present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        player.getPersonalBoard().getTerritories().add(this);
        if (immediateEffect != null) {
            immediateEffect.apply(player);
        }
        if (harvestEffect != null) {
            harvestEffect.add(player);
        }
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player has less than 6 Territory cards
     */
    @Override
    public boolean spaceExists(Player player) {
        return player.getPersonalBoard().getTerritories().size() < 6;
    }

}