package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.PermanentEffect;
import it.polimi.ingsw.GC_18.model.effects.dynamic.FloorChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.HarvestChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.PawnValueChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.ProductionChange;
import it.polimi.ingsw.GC_18.model.effects.dynamic.ResourcesChange;
import it.polimi.ingsw.GC_18.model.effects.endgame.EndGameVictoryMalus;
import it.polimi.ingsw.GC_18.model.effects.endgame.EndGameVictoryMalusCost;
import it.polimi.ingsw.GC_18.model.effects.statics.EndGameNegate;
import it.polimi.ingsw.GC_18.model.effects.statics.PlaceNegate;
import it.polimi.ingsw.GC_18.model.effects.statics.ServantMalus;
import it.polimi.ingsw.GC_18.model.effects.statics.TurnNegate;
import it.polimi.ingsw.GC_18.utils.FromJson;

/**
 * This class represents a ExcomunicationTile card
 */
public class ExcomunicationTile extends Card implements Pickable, Serializable {

    private static final long serialVersionUID = -482133943636363502L;
    private static final String PERMANENT_EFFECT = "permanentEffect";
    private static final String RESOURCES_CHANGE = "resourcesChange";
    private static final String RESOURCES = "resources";
    private static final String SOURCE = "source";
    private static final String HARVEST_CHANGE = "harvestChange";
    private static final String VALUE = "value";
    private static final String PRODUCTION_CHANGE = "productionChange";
    private static final String PAWN_VALUE_CHANGE = "pawnValueChange";
    private static final String COLORS = "colors";
    private static final String FLOOR_CHANGE = "floorChange";
    private static final String DISCOUNT = "discount";
    private static final String PLACE_NEGATE = "placeNegate";
    private static final String PLACES = "places";
    private static final String SERVANT_MALUS = "servantMalus";
    private static final String END_GAME_NEGATE = "endGameNegate";
    private static final String COLOR = "color";
    private static final String END_GAME_VICTORY_MALUS = "endGameVictoryMalus";
    private static final String END_GAME_VICTORY_MALUS_COST = "endGameVictoryMalusCost";
    private static final String TURN_NEGATE = "turnNegate";
    private static final String NULL = "null";

    private PermanentEffect permanentEffect;

    /**
     * Create an ExcomunicationTile card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public ExcomunicationTile(JSONObject jsonObject) {
        try {
            name = jsonObject.get(NAME).toString();
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (permanentEffectStr) {
                case RESOURCES_CHANGE:
                    JSONObject resourcesChange = jsonObject.getJSONObject(RESOURCES_CHANGE);
                    permanentEffect = new ResourcesChange(FromJson.getResources(resourcesChange.getJSONObject(RESOURCES)),
                            FromJson.getSource(resourcesChange.getJSONArray(SOURCE)));
                    break;

                case HARVEST_CHANGE:
                    JSONObject harvestChange = jsonObject.getJSONObject(HARVEST_CHANGE);
                    permanentEffect = new HarvestChange(harvestChange.getInt(VALUE));
                    break;

                case PRODUCTION_CHANGE:
                    JSONObject productionChange = jsonObject.getJSONObject(PRODUCTION_CHANGE);
                    permanentEffect = new ProductionChange(productionChange.getInt(VALUE));
                    break;

                case PAWN_VALUE_CHANGE:
                    JSONObject pawnValueChange = jsonObject.getJSONObject(PAWN_VALUE_CHANGE);
                    permanentEffect = new PawnValueChange(pawnValueChange.getInt(VALUE),
                            FromJson.getDiceColors(pawnValueChange.getJSONArray(COLORS)));
                    break;

                // floorChange in excomunication tiles cannot have resources discount
                case FLOOR_CHANGE:
                    JSONObject floorChange = jsonObject.getJSONObject(FLOOR_CHANGE);
                    permanentEffect = new FloorChange(FromJson.getTowerColor(floorChange.getString(COLOR)),
                            floorChange.getInt(VALUE), FromJson.getResources(floorChange.getJSONObject(DISCOUNT)));
                    break;

                case PLACE_NEGATE:
                    JSONObject stateModifier = jsonObject.getJSONObject(PLACE_NEGATE);
                    permanentEffect = new PlaceNegate(FromJson.getPlaceNames(stateModifier.getJSONArray(PLACES)));
                    break;

                case SERVANT_MALUS:
                    JSONObject servantMalus = jsonObject.getJSONObject(SERVANT_MALUS);
                    permanentEffect = new ServantMalus(servantMalus.getInt(VALUE));
                    break;

                case END_GAME_NEGATE:
                    JSONObject endGameNegate = jsonObject.getJSONObject(END_GAME_NEGATE);
                    permanentEffect = new EndGameNegate(FromJson.getTowerColor(endGameNegate.getString(COLOR)));
                    break;

                case END_GAME_VICTORY_MALUS:
                    JSONObject endGameVictoryMalus = jsonObject.getJSONObject(END_GAME_VICTORY_MALUS);
                    permanentEffect = new EndGameVictoryMalus(
                            FromJson.getResources((JSONObject) endGameVictoryMalus.get(RESOURCES)));
                    break;

                case END_GAME_VICTORY_MALUS_COST:
                    JSONObject endGameVictoryMalusCost = jsonObject.getJSONObject(END_GAME_VICTORY_MALUS_COST);
                    permanentEffect = new EndGameVictoryMalusCost(FromJson.getTowerColor(endGameVictoryMalusCost.getString("color")));
                    break;

                case TURN_NEGATE:
                    permanentEffect = new TurnNegate();
                    break;

                case NULL:
                    break;

                default:
                    ModelLogger.getInstance().logInfo("Check the permanent effect");
                    break;
            }
        } catch (JSONException e) {
            ModelLogger.getInstance().log(Level.WARNING, "exception thrown", e);
        }

    }

    @Override
    public String toString() {
        if (permanentEffect != null) {
            return "Malus: " + permanentEffect.toString() + "\n";
        }
        return " ";
    }

    /**
     * Adds the permanent effects of the ExcomunicationTile card to the player,
     * if it is present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        player.getPersonalBoard().getExcomunicationTiles().add(this);
        if (permanentEffect != null) {
            permanentEffect.add(player);
        }
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player can pick the ExcomunicationTile card
     */
    @Override
    public boolean spaceExists(Player player) {
        return true;
    }

}