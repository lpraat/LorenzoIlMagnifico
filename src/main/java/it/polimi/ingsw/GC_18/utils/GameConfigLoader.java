package it.polimi.ingsw.GC_18.utils;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import it.polimi.ingsw.GC_18.model.resources.Resources;

/**
 * This interface provides methods for reading json files related to the game pieces.
 */
public interface GameConfigLoader {


    /**
     * @return the map between integer and resources bonus for conquering territories.
     */
    static HashMap<Integer, Resources> loadConqueredTerritoriesBonus() {
        HashMap<Integer, Resources> hashMap = new HashMap<>();
        JSONObject faithTrack = new JSONObject(Utils.loadFileAsString("json/conqueredTerritoriesBonus.json"));
        Iterator<?> keys = faithTrack.keys();
        while (keys.hasNext()) {
            String number = (String) keys.next();
            Resources resources = FromJson.getResources(faithTrack.getJSONObject(number));
            hashMap.put(Integer.parseInt(number), resources);
        }
        return hashMap;

    }

    /**
     * @return the map between integer and resources bonus for influencing characters.
     */
    static HashMap<Integer, Resources> loadInfluencedCharactersBonus() {
        HashMap<Integer, Resources> hashMap = new HashMap<>();
        JSONObject faithTrack = new JSONObject(Utils.loadFileAsString("json/influencedCharactersBonus.json"));
        Iterator<?> keys = faithTrack.keys();
        while (keys.hasNext()) {
            String number = (String) keys.next();
            Resources resources = FromJson.getResources(faithTrack.getJSONObject(number));
            hashMap.put(Integer.parseInt(number), resources);
        }
        return hashMap;

    }

    /**
     * @return the map between integer and resources bonus in the faith track.
     */
    static HashMap<Integer, Resources> loadFaithTrack() {
        HashMap<Integer, Resources> hashMap = new HashMap<>();
        JSONObject faithTrack = new JSONObject(Utils.loadFileAsString("json/faithTrack.json"));
        Iterator<?> keys = faithTrack.keys();
        while (keys.hasNext()) {
            String number = (String) keys.next();
            Resources resources = FromJson.getResources(faithTrack.getJSONObject(number));
            hashMap.put(Integer.parseInt(number), resources);
        }
        return hashMap;

    }

    /**
     * @return the map between integer and resources in the territory picking costs.
     */
    static HashMap<Integer, Resources> loadTerritoryCosts() {
        HashMap<Integer, Resources> hashMap = new HashMap<>();
        JSONObject territoryCosts = new JSONObject(Utils.loadFileAsString("json/territoryCosts.json"));
        Iterator<?> keys = territoryCosts.keys();
        while (keys.hasNext()) {
            String number = (String) keys.next();
            Resources resources = FromJson.getResources(territoryCosts.getJSONObject(number));
            hashMap.put(Integer.parseInt(number), resources);
        }
        return hashMap;

    }

    /**
     * @param place the string representing the action place.
     * @return the resources bonus of the place.
     */
    static Resources loadPlaceBonus(String place) {
        JSONObject bonuses = new JSONObject(Utils.loadFileAsString("json/bonuses.json"));
        JSONObject resources = bonuses.getJSONObject(place).getJSONObject("resources");
        return FromJson.getResources(resources);
    }


    /**
     * @param type the type of bonus tile.
     * @param index the bonus tile number.
     * @return the resources bonus of the bonus tile.
     */
    static Resources loadAdvancedBonusTile(String type, int index) {
        JSONObject bonusTile = new JSONObject(Utils.loadFileAsString("json/bonusAdvancedTile.json"));
        JSONObject resources = (JSONObject) bonusTile.getJSONObject(Integer.toString(index)).get(type);
        return FromJson.getResources(resources);
    }

    /**
     * @param place the action place.
     * @return the value needed for a place in the action place.
     */
    static int loadPlaceValue(String place) {
        JSONObject bonuses = new JSONObject(Utils.loadFileAsString("json/values.json"));
        return bonuses.getJSONObject(place).getInt("value");
    }

    /**
     * @return the resources to give the players at the start.
     */
    static Resources loadInitialResources() {
        JSONObject initialResources = new JSONObject(Utils.loadFileAsString("json/initialResources.json"));
        return FromJson.getResources(initialResources.getJSONObject("resources"));
    }
}
