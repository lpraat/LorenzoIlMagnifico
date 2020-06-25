package it.polimi.ingsw.GC_18.model.cards;

import java.io.Serializable;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.effects.ProductionEffect;
import it.polimi.ingsw.GC_18.model.effects.decorators.Production;
import it.polimi.ingsw.GC_18.model.effects.immediate.Bonus;
import it.polimi.ingsw.GC_18.model.effects.immediate.BonusPerCard;
import it.polimi.ingsw.GC_18.model.effects.production.Exchange;
import it.polimi.ingsw.GC_18.model.effects.production.ExchangeOr;
import it.polimi.ingsw.GC_18.utils.FromJson;


/**
 * This class represents a Building card
 */
public class Building extends DevelopmentCard implements Pickable, Serializable {

    private static final long serialVersionUID = -2721692785502452815L;
    private static final String NULL = "null";
    private static final String BONUS = "bonus";
    private static final String EXCHANGE_P = "exchangeP";
    private static final String EXCHANGE_OR_P = "exchangeOrP";
    private static final String IN = "in";
    private static final String OUT = "out";
    private static final String IN_1 = "in1";
    private static final String OUT_1 = "out1";
    private static final String IN_2 = "in2";
    private static final String OUT_2 = "out2";
    private static final String BONUS_P = "bonusP";
    private static final String BONUS_PER_CARD_P = "bonusPerCardP";
    private static final String RESOURCES = "resources";
    private static final String CARD = "card";


    private ProductionEffect productionEffect;

    /**
     * Create a Building card reading from a JSON object
     * @param jsonObject the JSON object
     */
    public Building(JSONObject jsonObject) {
        try {
            int activationValue = jsonObject.getInt("activationValue");
            name = jsonObject.get(NAME).toString();
            cost = FromJson.getResources((JSONObject) jsonObject.get(COST));
            String immediateEffectStr = jsonObject.getString(IMMEDIATE_EFFECT);
            String permanentEffectStr = jsonObject.getString(PERMANENT_EFFECT);

            switch (immediateEffectStr) {
                case NULL:
                    immediateEffect = null;
                    break;

                case BONUS:
                    immediateEffect = new Bonus(FromJson.getResources((JSONObject) jsonObject.getJSONObject(immediateEffectStr).get("resources")));
                    break;
                default:
                    ModelLogger.getInstance().logInfo("Check the immediate effect in json");
                    break;
            }

            switch (permanentEffectStr) {
                case EXCHANGE_P:
                    JSONObject exchange = jsonObject.getJSONObject(permanentEffectStr);
                    productionEffect = new Exchange(FromJson.getResources(exchange.getJSONObject(IN)),
                            FromJson.getResources(exchange.getJSONObject(OUT)), activationValue);
                    break;

                case EXCHANGE_OR_P:
                    JSONObject exchangeOr = jsonObject.getJSONObject(permanentEffectStr);
                    productionEffect = new ExchangeOr(FromJson.getResources(exchangeOr.getJSONObject(IN_1)),
                            FromJson.getResources(exchangeOr.getJSONObject(OUT_1)),
                            FromJson.getResources(exchangeOr.getJSONObject(IN_2)),
                            FromJson.getResources(exchangeOr.getJSONObject(OUT_2)), activationValue);
                    break;

                case BONUS_P:
                    JSONObject bonus = jsonObject.getJSONObject(permanentEffectStr);
                    productionEffect = new Production(activationValue,
                            new Bonus(FromJson.getResources(bonus.getJSONObject(RESOURCES))));
                    break;

                case BONUS_PER_CARD_P:
                    JSONObject bonusPer = jsonObject.getJSONObject(permanentEffectStr);
                    productionEffect = new Production(activationValue,
                            new BonusPerCard(FromJson.getResources(bonusPer.getJSONObject(RESOURCES)),
                                    FromJson.getTowerColor(bonusPer.getString(CARD))));
                    break;

                case NULL:
                    productionEffect = null;
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
        if (productionEffect != null) {
            stringBuilder.append("Permanent Effect: ").append(productionEffect.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Applies the Immediate effect and adds the Permanent effect
     * of the building card to the player, if they are present in the card description.
     * @param player the Player
     */
    @Override
    public void add(Player player) {
        player.getPersonalBoard().getBuildings().add(this);
        if (immediateEffect != null) {
            immediateEffect.apply(player);
        }
        if (productionEffect != null) {
            productionEffect.add(player);
        }
    }

    /**
     * @param player the Player that wants to pick up the card
     * @return true if the player has less than 6 Building cards
     */
    @Override
    public boolean spaceExists(Player player) {
        return player.getPersonalBoard().getBuildings().size() < 6;
    }

}