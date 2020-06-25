package it.polimi.ingsw.GC_18.utils;

import java.util.List;

import it.polimi.ingsw.GC_18.model.Player;

/**
 * This interface provides some utils for game objects.
 */
public interface GameUtils {

    /**
     * @param x the first number.
     * @param y the second number.
     * @return the weight of x given y.
     */
    static int calculateWeight(int x, int y) {
        return x / y;
    }


    /**
     * @param player the player.
     * @param servants the servants.
     * @return true if the player can spend the servants.
     */
    static boolean compareServants(Player player, int servants) {
        return player.getServants() >= servants && servants >= 0;
    }

    /**
     * @param list the list.
     * @return a well formatted string of objects listed in list.
     */
    static String stringFromList(List<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("Number ").append(String.valueOf(i+1)).append("\n");
            stringBuilder.append(list.get(i).toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }


}