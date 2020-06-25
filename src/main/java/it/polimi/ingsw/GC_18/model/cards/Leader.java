package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.effects.decorators.OncePerTurn;
import it.polimi.ingsw.GC_18.model.effects.dynamic.ChurchSupportBonus;
import it.polimi.ingsw.GC_18.model.effects.dynamic.FloorChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.PawnValueChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.ResourcesMultiplier;
import it.polimi.ingsw.GC_18.model.effects.game.CopyLeader;
import it.polimi.ingsw.GC_18.model.effects.game.PawnSet;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraHarvest;
import it.polimi.ingsw.GC_18.model.effects.immediate.ExtraProduction;
import it.polimi.ingsw.GC_18.model.effects.onceperturn.PawnSetChoose;
import it.polimi.ingsw.GC_18.model.effects.statics.NoMilitaryForTerritory;
import it.polimi.ingsw.GC_18.model.effects.statics.NoMoneyCostPlace;
import it.polimi.ingsw.GC_18.model.effects.statics.PlaceInOccupied;
import it.polimi.ingsw.GC_18.utils.FromJson;

/**
 * This class represents a Leader card
 */
public class Leader extends Card implements Pickable, Serializable {

    private static final long serialVersionUID = 2152629155434867157L;
    private static final String REQUIREMENT = "requirement";
    private static final String ONCE_PER_TURN_EFFECT = "oncePerTurnEffect";
    private static final String PERMANENT_EFFECT = "permanentEffect";
    private static final String EXTRA_HARVEST = "extraHarvest";
    private static final String VALUE = "value";
    private static final String CHANGEABLE = "changeable";
    private static final String EXTRA_PRODUCTION = "extraProduction";
    private static final String BONUS = "bonus";
    private static final String RESOURCES = "resources";
    private static final String PAWN_SET_CHOOSE = "pawnSetChoose";
    private static final String PAWN_SET_CHOOSE1 = "pawnSetChoose";
    private static final String NULL = "null";
    private static final String PLACE_IN_OCCUPIED = "placeInOccupied";
    private static final String NO_MONEY_COST_PLACE = "noMoneyCostPlace";
    private static final String PAWN_VALUE_CHANGE = "pawnValueChange";
    private static final String COLORS = "colors";
    private static final String PAWN_SET = "pawnSet";
    private static final String CHURCH_SUPPORT_BONUS = "churchSupportBonus";
    private static final String COPY_LEADER = "copyLeader";
    private static final String NO_MILITARY_FOR_TERRITORY = "noMilitaryForTerritory";
    private static final String FLOOR_CHANGE = "floorChange";
    private static final String COLOR = "color";
    private static final String DISCOUNT = "discount";
    private static final String RESOURCES_MULTIPLIER = "resourcesMultiplier";
    private static final String RESOURCES_MULTIPLIER1 = "resourcesMultiplier";
    private static final String SOURCE = "source";
    private static final String MULTIPLIER = "multiplier";

    private OncePerTurnEffect oncePerTurnEffect;
    private GameEffect gameEffect;
    private Requirement requirement;

    /**
     * Sets the once per turn effect
     * @param oncePerTurnEffect the once per turn effect to set.
     */
    public void setOncePerTurnEffect(OncePerTurnEffect oncePerTurnEffect) {
        this.oncePerTurnEffect = oncePerTurnEffect;
    }

    /**
     * Sets the permanent effect
     * @param gameEffect the effect to set.
     */
    public void setGameEffect(GameEffect gameEffect) {
        this.gameEffect = gameEffect;
    }

    /**
     * @return the once per turn effect.
     */
    public OncePerTurnEffect getOncePerTurnEffect() {
        return oncePerTurnEffect;
    }

    /**
     * @return the permanent effect.
     */
    public GameEffect getGameEffect() {
        return gameEffect;
    }

    /**
     * Create a Leader card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public Leader(JSONObject jsonObject) {
        try {
            name = jsonObject.get(NAME).toString();
            requirement = FromJson.getRequirement((JSONObject) jsonObject.get(REQUIREMENT));
            String oncePerTurnEffectStr = jsonObject.getString(ONCE_PER_TURN_EFFECT);
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (oncePerTurnEffectStr) {
                case EXTRA_HARVEST:
                    JSONObject extraHarvest = jsonObject.getJSONObject(EXTRA_HARVEST);
                    oncePerTurnEffect = new OncePerTurn(new ExtraHarvest(extraHarvest.getInt(VALUE), extraHarvest.getBoolean(CHANGEABLE)));
                    break;

                case EXTRA_PRODUCTION:
                    JSONObject extraProduction = jsonObject.getJSONObject(EXTRA_PRODUCTION);
                    oncePerTurnEffect = new OncePerTurn(new ExtraProduction(extraProduction.getInt(VALUE), extraProduction.getBoolean(CHANGEABLE)));
                    break;

                case BONUS:
                    JSONObject bonus = jsonObject.getJSONObject(BONUS);
                    oncePerTurnEffect = new OncePerTurn(new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES))));
                    break;

                case PAWN_SET_CHOOSE:
                    JSONObject pawnSetChoose = jsonObject.getJSONObject(PAWN_SET_CHOOSE1);
                    oncePerTurnEffect = new PawnSetChoose(pawnSetChoose.getInt(VALUE));
                    break;

                case NULL:
                    break;

                default:
                    ModelLogger.getInstance().logInfo("Check your once per turn effect");
                    break;
            }

            switch (permanentEffectStr) {
                case PLACE_IN_OCCUPIED:
                    gameEffect = new PlaceInOccupied();
                    break;

                case NO_MONEY_COST_PLACE:
                    gameEffect = new NoMoneyCostPlace();
                    break;

                case PAWN_VALUE_CHANGE:
                    JSONObject pawnValueChange = jsonObject.getJSONObject(PAWN_VALUE_CHANGE);
                    gameEffect = new PawnValueChange(pawnValueChange.getInt(VALUE),
                            FromJson.getDiceColors(pawnValueChange.getJSONArray(COLORS)));
                    break;

                case PAWN_SET:
                    JSONObject pawnSet = jsonObject.getJSONObject(PAWN_SET);
                    gameEffect = new PawnSet(pawnSet.getInt(VALUE), FromJson.getDiceColors(pawnSet.getJSONArray(COLORS)));
                    break;

                case CHURCH_SUPPORT_BONUS: {
                    JSONObject churchSupportBonus = jsonObject.getJSONObject(CHURCH_SUPPORT_BONUS);
                    gameEffect = new ChurchSupportBonus(FromJson.getResources(churchSupportBonus.getJSONObject(RESOURCES)));
                    break;
                }

                case COPY_LEADER:
                    gameEffect = new CopyLeader(this);
                    break;

                case NO_MILITARY_FOR_TERRITORY:
                    gameEffect = new NoMilitaryForTerritory();
                    break;

                case FLOOR_CHANGE:
                    JSONObject floorChange = jsonObject.getJSONObject(FLOOR_CHANGE);
                    gameEffect = new FloorChange(FromJson.getTowerColor(floorChange.getString(COLOR)),
                            floorChange.getInt(VALUE), FromJson.getResources(floorChange.getJSONObject(DISCOUNT)));
                    break;

                case RESOURCES_MULTIPLIER:
                    JSONObject resourcesMultiplier = jsonObject.getJSONObject(RESOURCES_MULTIPLIER1);
                    gameEffect = new ResourcesMultiplier(FromJson.getResources(resourcesMultiplier.getJSONObject(RESOURCES)),
                            FromJson.getSource(resourcesMultiplier.getJSONArray(SOURCE)),
                            resourcesMultiplier.getInt(MULTIPLIER));
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
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append(requirement.toString()).append("\n");

        if (oncePerTurnEffect != null) {
            stringBuilder.append("OncePerTurn Effect: ").append(oncePerTurnEffect.toString()).append("\n");
        } else {
            stringBuilder.append("Permanent Effect: ").append(gameEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Adds the Permanent effect or the OncePerTurn effect of the Leader card to the player,
     * if they are present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        if (oncePerTurnEffect != null) {
            oncePerTurnEffect.add(player);
        }
        if (gameEffect != null) {
            gameEffect.add(player);
        }
        player.getPersonalBoard().getLeadersPlayed().add(this);
    }

    /**
     * @return the Requirement a Player needs to satisfy for playing the Leader card
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player can pick the Leader card
     */
    @Override
    public boolean spaceExists(Player player) {
        return true;
    }

}