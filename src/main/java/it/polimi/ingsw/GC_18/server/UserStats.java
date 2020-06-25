package it.polimi.ingsw.GC_18.server;

import java.util.Arrays;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Holds the statistics of a client (user name, games played, games won, games lost, total victory points accumulated)
 */
public class UserStats {

    static final String STATS_PATH = "resources/stats/stats.txt";
    private static final int STATS_FIELDS = 5;
    private static final int USERNAME_COL = 0;
    private static final int GAMES_PLAYED_COL = 1;
    private static final int GAMES_WON_COL = 2;
    private static final int GAMES_LOST_COL = 3;
    private static final int TOTAL_VICTORY_POINTS_COL = 4;

    private static String[][] globalStats;

    private String username;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int totalVictoryPoints;
    private int indexInGlobalStats;

    /**
     * Creates the statistics of a client (user name, games played, games won, games lost, total victory points accumulated)
     * @param stats -  the statistic as a string (separated by " - "
     * @param indexInGlobalStats - the index in the global statistics
     * @throws IllegalArgumentException for file errors
     */
    private UserStats(String[] stats, int indexInGlobalStats) throws IllegalArgumentException {
        if (stats.length != STATS_FIELDS) {
            throw new IllegalArgumentException();
        }
        username = stats[USERNAME_COL];
        gamesPlayed = Integer.parseInt(stats[GAMES_PLAYED_COL]);
        gamesWon = Integer.parseInt(stats[GAMES_WON_COL]);
        gamesLost = Integer.parseInt(stats[GAMES_LOST_COL]);
        totalVictoryPoints = Integer.parseInt(stats[TOTAL_VICTORY_POINTS_COL]);
        this.indexInGlobalStats = indexInGlobalStats;
    }

    /**
     * Adds a game won to the game won counter
     */
    public void addGameWon() {
        gamesWon++;
        gamesPlayed++;
        globalStats[indexInGlobalStats][GAMES_WON_COL] = String.valueOf(gamesWon);
        globalStats[indexInGlobalStats][GAMES_PLAYED_COL] = String.valueOf(gamesPlayed);
    }

    /**
     * Adds a game lost to the game lost counter
     */
    public void addGameLost() {
        gamesLost++;
        gamesPlayed++;
        globalStats[indexInGlobalStats][GAMES_LOST_COL] = String.valueOf(gamesLost);
        globalStats[indexInGlobalStats][GAMES_PLAYED_COL] = String.valueOf(gamesPlayed);
    }

    /**
     * Adds victory points to the total victory points
     * @param victoryPoints - the victory points to add
     */
    public void addVictoryPoints(int victoryPoints) {
        totalVictoryPoints += victoryPoints;
        globalStats[indexInGlobalStats][TOTAL_VICTORY_POINTS_COL] = String.valueOf(totalVictoryPoints);
    }

    /**
     * Retrieves the statistics of a player
     * @param username - the user name of the player for whom the statistics are requested
     * @return the statistics of the player
     * @throws IllegalArgumentException for file errors
     */
    public static UserStats getUserStats(String username) throws IllegalArgumentException {
        for (int i=0;i<globalStats.length;i++) {
            if (globalStats[i][0].equals(username))
                return new UserStats(globalStats[i], i);
        }
        // user doesn't have any statistics already
        String[] stats = new String[]{username,"0","0","0","0"};
        Utils.appendToFile(STATS_PATH, Arrays.asList(stats).stream().collect(Collectors.joining(" - ")));
        loadStats();
        return new UserStats(globalStats[globalStats.length-1],globalStats.length-1);
    }

    /**
     * Updates the statistics log
     */
    public static void updateUserStatsFile() {
        StringBuilder statsString = new StringBuilder();
        for (String[] globalStat : globalStats) {
            for (int j = 0; j < globalStat.length - 1; j++) {
                statsString.append(globalStat[j]).append(" - ");
            }
            statsString.append(globalStat[globalStat.length - 1]).append("\n");
        }
        Utils.overwriteFile(STATS_PATH, statsString.toString());
    }

    /**
     * Loads the statistics from the log
     */
    static void loadStats() {
        String[] rows = Utils.loadFileAsString(STATS_PATH).split("\n");
        String[][] stats = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            stats[i] = rows[i].split(" - ");
        }
        UserStats.globalStats = stats;
    }

    /**
     * @return the user name associated to this statistics
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username - the user name to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
