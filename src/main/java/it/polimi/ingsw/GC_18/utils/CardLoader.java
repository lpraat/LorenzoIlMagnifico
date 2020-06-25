package it.polimi.ingsw.GC_18.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.ExcomunicationTile;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;

/**
 * This interface provide methods for loading json files of cards.
 */
public interface CardLoader {

    /**
     * Loads cards from a json file.
     * @param period the period.
     * @param type the type of cards.
     * @return the object of the cards.
     */
    static JSONObject loadCards(int period, String type) {
        String periodStr = null;
        if (period == 0) {
            periodStr = "firstPeriod";
        } else if (period == 1) {
            periodStr = "secondPeriod";
        } else if (period == 2){
            periodStr = "thirdPeriod";
        } else if (period == -1){
            periodStr = null;
        }
        JSONObject elements = new JSONObject(Utils.loadFileAsString("json/" + type + ".json"));
        if (periodStr == null) {
            return elements;
        }
        return elements.getJSONObject(periodStr);

    }

    /**
     * @param jObject the json object to generate the list from.
     * @return a new list containing the leaders.
     */
    static ArrayList<Leader> leaderLoader(JSONObject jObject) {
        ArrayList<Leader> cards = new ArrayList<>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            cards.add(new Leader((JSONObject) jObject.get(s)));
        }
        return cards;

    }

    /**
     * @param jObject the json object to generate the list from.
     * @return a new list containing the buildings.
     */
    static ArrayList<Building> buildingLoader(JSONObject jObject) {
        ArrayList<Building> cards = new ArrayList<>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            cards.add(new Building((JSONObject) jObject.get(s)));
        }
        return cards;

    }

    /**
     * @param jObject the json object to generate the list from.
     * @return a new list containing the ventures.
     */
    static ArrayList<Venture> ventureLoader(JSONObject jObject) {
        ArrayList<Venture> cards = new ArrayList<>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            cards.add(new Venture((JSONObject) jObject.get(s)));
        }
        return cards;

    }

    /**
     * @param jObject the json object to generate the list from.
     * @return a new list containing the territory.
     */
    static ArrayList<Territory> territoryLoader(JSONObject jObject) {
        ArrayList<Territory> cards = new ArrayList<>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            cards.add(new Territory((JSONObject) jObject.get(s)));
        }
        return cards;

    }

    /**
     * @param jObject the json object to generate the list from.
     * @return a new list containing the characters.
     */
    static ArrayList<Character> characterLoader(JSONObject jObject) {
        ArrayList<Character> cards = new ArrayList<>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            cards.add(new Character((JSONObject) jObject.get(s)));
        }
        return cards;

    }

    /**
     * @param jsonObject the json object to generate the list from.
     * @return a new list containing the leaders.
     */
    static ArrayList<ExcomunicationTile> excomunicationLoader(JSONObject jsonObject) {
        ArrayList<ExcomunicationTile> excomunicationTiles = new ArrayList<>();
        Iterator<?> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String s = (String) keys.next();
            excomunicationTiles.add(new ExcomunicationTile((JSONObject) jsonObject.get(s)));
        }
        return excomunicationTiles;
    }

}
