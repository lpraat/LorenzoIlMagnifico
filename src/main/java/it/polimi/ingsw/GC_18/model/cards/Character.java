package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;
import it.polimi.ingsw.GC_18.model.effects.decorators.DoubleEffectImmediate;
import it.polimi.ingsw.GC_18.model.effects.dynamic.FloorChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.FloorChangeOr;
import it.polimi.ingsw.GC_18.model.effects.dynamic.HarvestChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.ProductionChange;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;
import it.polimi.ingsw.GC_18.model.effects.immediate.BonusPerCard;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraFloorPick;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraHarvest;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraProduction;
import it.polimi.ingsw.GC_18.model.effects.immediate.VictoryPerResource;
import it.polimi.ingsw.GC_18.model.effects.statics.NoHighFloorBonus;
import it.polimi.ingsw.GC_18.utils.FromJson;

/**
 * This class represents a Character card
 */
public class Character extends DevelopmentCard implements Pickable, Serializable {

    private static final long serialVersionUID = -7102158506176063653L;
    private static final String NULL = "null";
    private static final String BONUS = "bonus";
    private static final String RESOURCES = "resources";
    private static final String BONUS_EXTRA_FLOOR_PICK = "bonus-extraFloorPick";
    private static final String COLOR = "color";
    private static final String DISCOUNT = "discount";
    private static final String VALUE = "value";
    private static final String BONUS_EXTRA_HARVEST = "bonus-extraHarvest";
    private static final String CHANGEABLE = "changeable";
    private static final String BONUS_EXTRA_PRODUCTION = "bonus-extraProduction";
    private static final String BONUS_PER_CARD = "bonusPerCard";
    private static final String CARD = "card";
    private static final String EXTRA_FLOOR_PICK = "extraFloorPick";
    private static final String VICTORY_PER_RESOURCE = "victoryPerResource";
    private static final String FLOOR_CHANGE = "floorChange";
    private static final String FLOOR_CHANGE_OR = "floorChangeOr";
    private static final String DISCOUNT_1 = "discount1";
    private static final String DISCOUNT_2 = "discount2";
    private static final String HARVEST_CHANGE = "harvestChange";
    private static final String PRODUCTION_CHANGE = "productionChange";
    private static final String NO_HIGH_FLOOR_BONUS = "noHighFloorBonus";

    private GameEffect gameEffect;

    /**
     * Create a Character card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public Character(JSONObject jsonObject) {
        try {
            name = jsonObject.get(NAME).toString();
            cost = FromJson.getResources((JSONObject) jsonObject.get(COST));
            String immediateEffectStr = jsonObject.getString(IMMEDIATE_EFFECT);
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (immediateEffectStr) {
                case NULL:
                    immediateEffect = null;
                    break;

                case BONUS:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS);
                    immediateEffect = new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES)));
                    break;


                case BONUS_EXTRA_FLOOR_PICK:
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

                case BONUS_EXTRA_HARVEST:
                    immediates = immediateEffectStr.split("-");
                    firstEffectStr = immediates[0];
                    secondEffectStr = immediates[1];
                    firstEffect = (JSONObject) jsonObject.get(firstEffectStr);
                    secondEffect = (JSONObject) jsonObject.get(secondEffectStr);
                    immediateEffect = new DoubleEffectImmediate(
                            new Bonus(FromJson.getResources(firstEffect.getJSONObject(RESOURCES))),
                            new ExtraHarvest(secondEffect.getInt(VALUE), secondEffect.getBoolean(CHANGEABLE)));
                    break;


                case BONUS_EXTRA_PRODUCTION:
                    immediates = immediateEffectStr.split("-");
                    firstEffectStr = immediates[0];
                    secondEffectStr = immediates[1];
                    firstEffect = (JSONObject) jsonObject.get(firstEffectStr);
                    secondEffect = (JSONObject) jsonObject.get(secondEffectStr);
                    immediateEffect = new DoubleEffectImmediate(
                            new Bonus(FromJson.getResources(firstEffect.getJSONObject(RESOURCES))),
                            new ExtraProduction(secondEffect.getInt(VALUE), secondEffect.getBoolean(CHANGEABLE)));
                    break;

                case BONUS_PER_CARD:
                    JSONObject bonusPer = jsonObject.getJSONObject(BONUS_PER_CARD);
                    immediateEffect = new BonusPerCard(FromJson.getResources(bonusPer.getJSONObject(RESOURCES)),
                            FromJson.getTowerColor(bonusPer.getString(CARD)));
                    break;


                case EXTRA_FLOOR_PICK:
                    JSONObject extraFloorPick = jsonObject.getJSONObject(EXTRA_FLOOR_PICK);
                    immediateEffect = new ExtraFloorPick(
                            FromJson.getTowerColor(extraFloorPick.getString(COLOR)), extraFloorPick.getInt(VALUE),
                            FromJson.getResources(extraFloorPick.getJSONObject(DISCOUNT)));
                    break;

                case VICTORY_PER_RESOURCE:
                    JSONObject victoryPerResource = jsonObject.getJSONObject(VICTORY_PER_RESOURCE);
                    immediateEffect = new VictoryPerResource(FromJson.getResources(victoryPerResource.getJSONObject(RESOURCES)));
                    break;



                default:
                    ModelLogger.getInstance().logInfo("Check the immediate effect in json");
                    break;

            }

            switch (permanentEffectStr) {
                case FLOOR_CHANGE:
                    JSONObject floorChange = jsonObject.getJSONObject(FLOOR_CHANGE);
                    gameEffect = new FloorChange(FromJson.getTowerColor(floorChange.getString(COLOR)),
                            floorChange.getInt(VALUE), FromJson.getResources(floorChange.getJSONObject(DISCOUNT)));
                    break;

                case FLOOR_CHANGE_OR:
                    JSONObject floorChangeOr = jsonObject.getJSONObject(FLOOR_CHANGE_OR);
                    gameEffect = new FloorChangeOr(FromJson.getTowerColor(floorChangeOr.getString(COLOR)),
                            floorChangeOr.getInt(VALUE), FromJson.getResources(floorChangeOr.getJSONObject(DISCOUNT_1)),
                            FromJson.getResources(floorChangeOr.getJSONObject(DISCOUNT_2)));
                    break;

                case HARVEST_CHANGE:
                    JSONObject harvestChange = jsonObject.getJSONObject(HARVEST_CHANGE);
                    gameEffect = new HarvestChange(harvestChange.getInt(VALUE));
                    break;

                case PRODUCTION_CHANGE:
                    JSONObject productionChange = jsonObject.getJSONObject(PRODUCTION_CHANGE);
                    gameEffect = new ProductionChange(productionChange.getInt(VALUE));
                    break;

                case NO_HIGH_FLOOR_BONUS:
                    gameEffect = new NoHighFloorBonus();
                    break;

                case NULL:
                    gameEffect = null;
                    break;

                default:
                    ModelLogger.getInstance().logInfo("Check the permanent effect in json");
                    break;
            }
        } catch (JSONException e) {
            ModelLogger.getInstance().log(Level.WARNING, "exception thrown", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (gameEffect != null) {
            stringBuilder.append("Permanent Effect: ").append(gameEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Applies the Immediate effect and adds the Permanent effect
     * of the Character card to the player, if they are present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        player.getPersonalBoard().getCharacters().add(this);
        if (immediateEffect != null) {
            immediateEffect.apply(player);
        }
        if (gameEffect != null) {
            gameEffect.add(player);
        }
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player has less than 6 Character cards
     */
    @Override
    public boolean spaceExists(Player player) {
        return player.getPersonalBoard().getCharacters().size() < 6;
    }

}