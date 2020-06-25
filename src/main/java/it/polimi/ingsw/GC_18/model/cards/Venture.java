package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.EndgameEffect;
import it.polimi.ingsw.GC_18.model.effects.decorators.DoubleEffectImmediate;
import it.polimi.ingsw.GC_18.model.effects.decorators.Endgame;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraFloorPick;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraHarvest;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraProduction;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.utils.FromJson;

/**
 * This class represents a Venture card. Venture can have a cost, an alternative cost, or both.
 * Cost is a normal cost like the one of all other Development cards. Alternative cost is a normal cost that
 * can be spent by the Player only if he has the alternative requirement resources.
 */
public class Venture extends DevelopmentCard implements Pickable, Serializable {

    private static final long serialVersionUID = 4307577131441954021L;
    private static final String NORMAL = "normal";
    private static final String COST_TYPE = "costType";
    private static final String ALTERNATIVE = "alternative";
    private static final String ALTERNATIVE_REQUIRES = "alternativeRequires";
    private static final String BONUS = "bonus";
    private static final String RESOURCES = "resources";
    private static final String EXTRA_HARVEST = "extraHarvest";
    private static final String VALUE = "value";
    private static final String CHANGEABLE = "changeable";
    private static final String EXTRA_PRODUCTION = "extraProduction";
    private static final String BONUS_EXTRA_FLOOR_PICK = "bonus-extraFloorPick";
    private static final String COLOR = "color";
    private static final String DISCOUNT = "discount";
    private static final String NULL = "null";
    private static final String BONUS_E = "bonusE";

    private EndgameEffect endgameEffect;
    private Resources alternativeCost;
    private Resources alternativeCostRequirement;

    /**
     * Create a Venture card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public Venture(JSONObject jsonObject) {
        try {
            name = jsonObject.get(NAME).toString();
            if (NORMAL.equals(jsonObject.getString(COST_TYPE))) {
                cost = FromJson.getResources((JSONObject) jsonObject.get(COST));
            } else if (ALTERNATIVE.equals(jsonObject.getString(COST_TYPE))) {
                alternativeCost = FromJson.getResources((JSONObject) jsonObject.get(ALTERNATIVE));
                alternativeCostRequirement = FromJson.getResources((JSONObject) jsonObject.get(ALTERNATIVE_REQUIRES));
            } else {
                cost = FromJson.getResources((JSONObject) jsonObject.get(COST));
                alternativeCost = FromJson.getResources((JSONObject) jsonObject.get(ALTERNATIVE));
                alternativeCostRequirement = FromJson.getResources((JSONObject) jsonObject.get(ALTERNATIVE_REQUIRES));
            }

            String immediateEffectStr = jsonObject.getString(IMMEDIATE_EFFECT);
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (immediateEffectStr) {
                case BONUS:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS);
                    immediateEffect = new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES)));
                    break;

                case EXTRA_HARVEST:
                    JSONObject extraHarvest = jsonObject.getJSONObject(EXTRA_HARVEST);
                    immediateEffect = new ExtraHarvest(extraHarvest.getInt(VALUE), extraHarvest.getBoolean(CHANGEABLE));
                    break;

                case EXTRA_PRODUCTION:
                    JSONObject extraProduction = jsonObject.getJSONObject(EXTRA_PRODUCTION);
                    immediateEffect = new ExtraProduction(extraProduction.getInt(VALUE), extraProduction.getBoolean(CHANGEABLE));
                    break;

                case BONUS_EXTRA_FLOOR_PICK:// this could be extended with regex
                    String[] immediates = immediateEffectStr.split("-");
                    String firstEffectStr = immediates[0];
                    String secondEffectStr = immediates[1];
                    JSONObject firstEffect = (JSONObject) jsonObject.get(firstEffectStr);
                    JSONObject secondEffect = (JSONObject) jsonObject.get(secondEffectStr);
                    immediateEffect = new DoubleEffectImmediate(
                            new Bonus(FromJson.getResources(firstEffect.getJSONObject(RESOURCES))), new ExtraFloorPick(
                            FromJson.getTowerColor(secondEffect.getString(COLOR)), secondEffect.getInt(VALUE),
                            FromJson.getResources(secondEffect.getJSONObject(DISCOUNT))));
                    break;


                case NULL:
                    immediateEffect = null;
                    break;

                default:
                    ModelLogger.getInstance().logInfo("Check your immediate effect");
            }


            switch (permanentEffectStr) {
                case BONUS_E:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS_E);
                    endgameEffect = new Endgame(new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES))));
                    break;

                case NULL:
                    endgameEffect = null;
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
        if (alternativeCost != null && alternativeCostRequirement != null) {
            stringBuilder.append("Alternative cost: ").append(alternativeCost.toString()).append("\n");
            stringBuilder.append("Alternative cost requirement: ").append(alternativeCostRequirement.toString()).append("\n");
        }
        if (endgameEffect != null) {
            stringBuilder.append("Permanent Effect: ").append(endgameEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Applies the Immediate effect and adds the Permanent effect
     * of the Venture card to the player, if they are present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        player.getPersonalBoard().getVentures().add(this);
        if (immediateEffect != null) {
            immediateEffect.apply(player);
        }
        if (endgameEffect != null) {
            endgameEffect.add(player);
        }
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player has less than 6 Venture cards
     */
    @Override
    public boolean spaceExists(Player player) {
        return player.getPersonalBoard().getVentures().size() < 6;
    }

    /**
     * @return the alternative cost of the Venture
     */
    public Resources getAlternativeCost() {
        return alternativeCost;
    }

    /**
     * @return the requirement resources to spend the alternative cost
     */
    public Resources getAlternativeCostRequirement() {
        return alternativeCostRequirement;
    }

}