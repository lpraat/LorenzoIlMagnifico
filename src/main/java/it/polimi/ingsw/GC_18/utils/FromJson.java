package it.polimi.ingsw.GC_18.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.DiceColor;
import it.polimi.ingsw.GC_18.model.ModelLogger;
import it.polimi.ingsw.GC_18.model.PlaceName;
import it.polimi.ingsw.GC_18.model.Source;
import it.polimi.ingsw.GC_18.model.TowerColor;
import it.polimi.ingsw.GC_18.model.cards.Requirement;
import it.polimi.ingsw.GC_18.model.resources.CouncilPrivileges;
import it.polimi.ingsw.GC_18.model.resources.FaithPoints;
import it.polimi.ingsw.GC_18.model.resources.MilitaryPoints;
import it.polimi.ingsw.GC_18.model.resources.Money;
import it.polimi.ingsw.GC_18.model.resources.Resources;
import it.polimi.ingsw.GC_18.model.resources.Servants;
import it.polimi.ingsw.GC_18.model.resources.Stones;
import it.polimi.ingsw.GC_18.model.resources.VictoryPoints;
import it.polimi.ingsw.GC_18.model.resources.Woods;

/**
 * This interface provides methods for parsing JSON objects.
 */
public interface FromJson {

    /**
     * Creates a new Resources object from a json object.
     * @param resources the json object.
     * @return the resources represented in the json object.
     */
    static Resources getResources(JSONObject resources) {
        int woods = 0;
        int stones = 0;
        int servants = 0;
        int money = 0;
        int militaryPoints = 0;
        int faithPoints = 0;
        int victoryPoints = 0;
        int councilPrivileges = 0;

        if (resources.has("woods")) {
            woods = resources.getInt("woods");
        }
        if (resources.has("stones")) {
            stones = resources.getInt("stones");
        }
        if (resources.has("servants")) {
            servants = resources.getInt("servants");
        }
        if (resources.has("money")) {
            money = resources.getInt("money");
        }
        if (resources.has("militaryPoints")) {
            militaryPoints = resources.getInt("militaryPoints");
        }
        if (resources.has("faithPoints")) {
            faithPoints = resources.getInt("faithPoints");
        }
        if (resources.has("victoryPoints")) {
            victoryPoints = resources.getInt("victoryPoints");
        }
        if (resources.has("councilPrivileges")) {
            councilPrivileges = resources.getInt("councilPrivileges");
        }
        return new Resources(new Woods(woods), new Stones(stones), new Servants(servants), new Money(money),
                new MilitaryPoints(militaryPoints), new FaithPoints(faithPoints), new CouncilPrivileges(councilPrivileges), new VictoryPoints(victoryPoints));
    }

    /**
     * Creates a new Requirement object from a json object.
     * @param requirement the json object.
     * @return the requirement represented in the json object.
     */
    static Requirement getRequirement(JSONObject requirement) {
        int buildingCards = 0;
        int characterCards = 0;
        int territoryCards = 0;
        int ventureCards = 0;
        boolean any = false;
        int anyValue = 0;

        if (requirement.has("buildingCards")) {
            buildingCards = requirement.getInt("buildingCards");
        }

        if (requirement.has("characterCards")) {
            characterCards = requirement.getInt("characterCards");
        }

        if (requirement.has("territoryCards")) {
            territoryCards = requirement.getInt("territoryCards");
        }

        if (requirement.has("ventureCards")) {
            ventureCards = requirement.getInt("ventureCards");
        }

        if (requirement.has("any")) {
            any = requirement.getBoolean("any");
        }

        if (requirement.has("anyValue")) {
            anyValue = requirement.getInt("anyValue");
        }

        return new Requirement(buildingCards, characterCards, territoryCards, ventureCards,
                any, anyValue, getResources(requirement.getJSONObject("resources")));


    }

    /**
     * Creates a new list of sources from a json array.
     * @param source the json array.
     * @return the list of sources represented in the json object.
     */
    static ArrayList<Source> getSource(JSONArray source) {
        ArrayList<Source> array = new ArrayList<>();
        for (int i = 0; i < source.length(); i++) {
            switch (source.getString(i)) {
            case "immediate":
                array.add(Source.IMMEDIATE_EFFECT);
                break;
            case "permanent":
                array.add(Source.PERMANENT_EFFECT);
                break;
            case "actionPlace":
                array.add(Source.ACTION_PLACE);
                break;
            case "oncePerTurn":
                array.add(Source.ONCEPERTURN);
                break;
            case "permanentLeader":
                array.add(Source.LEADER_PERMANENT);
                break;
            case "councilPrivilege":
                array.add(Source.COUNCIL_PRIVILEGE);
                break;
            default:
                ModelLogger.getInstance().logInfo("Check your json");
                break;
            }
        }
        return array;
    }

    /**
     * Creates a new list of dices' colors from a json array.
     * @param colors the json array.
     * @return the list of colors represented in the json object.
     */
    static ArrayList<DiceColor> getDiceColors(JSONArray colors) {
        ArrayList<DiceColor> array = new ArrayList<>();

        for (int i = 0; i < colors.length(); i++) {
            switch (colors.getString(i)) {
            case "black":
                array.add(DiceColor.BLACK);
                break;
            case "orange":
                array.add(DiceColor.ORANGE);
                break;
            case "white":
                array.add(DiceColor.WHITE);
                break;
            case "neutral":
                array.add(DiceColor.NEUTRAL);
                break;
            default:
                ModelLogger.getInstance().logInfo("Check your json");
                break;
            }
        }
        return array;
    }

    /**
     * Creates a new list of name places from a json array.
     * @param placeNames the json array.
     * @return the list of name places represented in the json object.
     */
    static ArrayList<PlaceName> getPlaceNames(JSONArray placeNames) {
        ArrayList<PlaceName> array = new ArrayList<>();

        for (int i = 0; i < placeNames.length(); i++) {
            switch (placeNames.getString(i)) {
            case "floor":
                array.add(PlaceName.FLOOR);
                break;

            case "coinSpot":
                array.add(PlaceName.COIN_SPOT);
                break;

            case "councilSpot":
                array.add(PlaceName.COUNCIL_SPOT);
                break;

            case "militarySpot":
                array.add(PlaceName.MILITARY_SPOT);
                break;

            case "servantSpot":
                array.add(PlaceName.SERVANT_SPOT);
                break;

            case "councilPalace":
                array.add(PlaceName.COUNCIL_PALACE);
                break;

            case "harvestArea":
                array.add(PlaceName.HARVEST_AREA);
                break;

            case "largeHarvestArea":
                array.add(PlaceName.LARGEHARVEST_AREA);
                break;
            case "productionArea":
                array.add(PlaceName.PRODUCTION_AREA);
                break;
            case "largeProductionArea":
                array.add(PlaceName.LARGEPRODUCTION_AREA);
                break;
            default:
                ModelLogger.getInstance().logInfo("Check your json");
                break;

            }
        }
        return array;

    }

    /**
     * @param color the color.
     * @return to tower color given a color.
     */
    static TowerColor getTowerColor(String color) {
        switch (color) {
        case "green":
            return TowerColor.GREEN;
        case "blue":
            return TowerColor.BLUE;
        case "yellow":
            return TowerColor.YELLOW;
        case "purple":
            return TowerColor.PURPLE;
        case "any":
            return TowerColor.ANY;
        default:
            ModelLogger.getInstance().logInfo("Check your json");
            return null;
        }

    }

}
