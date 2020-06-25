package it.polimi.ingsw.GC_18.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * This class represents turn handler. It handles the turns, rounds and periods of a game.
 */
public final class TurnHandler implements Serializable {

    // NOTE: turns periods and rounds are indexed from 0
    private static final long serialVersionUID = -5009031257967106413L;

    public static final transient Long TURN_TIMEOUT_IN_SECONDS = Long.parseLong(Game.getGameProperties().getProperty("TURNTIMEOUTINSECONDS"));
    private static final transient int NUMBER_OF_PAWNS = 4;
    private static final transient int PERIODS = Integer.parseInt(Game.getGameProperties().getProperty("PERIODS"));
    private boolean running; // true if the turn handler is running, i.e. the turn timer is active
    private boolean saving; // true if a game save will be done at the next round
    private int turn;
    private int round;
    private int period;
    private int currentTurnIndex;
    private final Game game;
    private transient Timer turnTimer = new Timer(); // timer for automatically switch turn
    private boolean report; // true if a church report  is going on


    /**
     * Creates a new TurnHandler for the game.
     * @param game the game.
     */
    TurnHandler(Game game) {
        turn = -1;
        round = 0;
        period = 0;
        currentTurnIndex = 0;
        this.game = game;
        Collections.shuffle(game.getPlayers());
    }

    /**
     * Changes the turn.
     */
    public synchronized void changeTurn() {
        if (turnTimer!=null)
            turnTimer.cancel();
        // before changing the turn frees the game controller.
        game.getGameController().interruptThread();
        turn++;

        if (turn == game.getPlayers().size() * (NUMBER_OF_PAWNS + 1)) {
            changeRound();
        }

        // handles the skip of the turn because of a card effect
        if (!report) {
            Player curr = game.getPlayers().get(currentTurnIndex);
            if (turn < game.getPlayers().size() && curr.getPersonalBoard().checkTurnNegate()) { // skipping turn for effect
                game.getGameController().notifyClients("CHAT", "Game notify: " + curr.getUsername() + " skipped turn!");
                game.getPlayers().forEach(p -> p.setYourTurn(false));
                currentTurnIndex = (currentTurnIndex + 1) % game.getPlayers().size();
                changeTurn();
            } else if (turn >= game.getPlayers().size()*NUMBER_OF_PAWNS) {

                // if all players have done their turn, do the turn skipped
                if (curr.getPersonalBoard().checkTurnNegate()) {
                    game.getGameController().notifyClients("CHAT", "Game notify: " + curr.getUsername() + " doing extra turn");
                    handleTurn(curr);
                } else {
                    currentTurnIndex = (currentTurnIndex + 1) % game.getPlayers().size();
                    changeTurn();
                }
            } else {
                handleTurn(curr);
            }
        }
    }

    /**
     * Handles the turn change.
     * @param curr the player that will play in this turn change.
     */
    private synchronized void handleTurn(Player curr) {
        game.setTurnInfo();
        game.setupForTurn();
        game.getPlayers().forEach(p -> {p.setYourTurn(false); p.setPlacedPawn(false);});
        curr.setYourTurn(true);
        game.getGameController().notifyClients(game);
        game.getGameController().notifyClients("TURN_OF", curr.getUsername());
        currentTurnIndex = (currentTurnIndex + 1) % game.getPlayers().size();
        if (running) {// starting turnTimer
            turnTimer = new Timer();
            turnTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    changeTurn();
                }
            }, Utils.secondsToMills(TURN_TIMEOUT_IN_SECONDS));
        }
    }

    /**
     * Handles the round change.
     */
    private synchronized void changeRound() {

        if (game.getBoard().getFightSpace() != null) {
            game.getBoard().getFightSpace().addBonus(round);
        }

        turn = 0;
        round++;

        rescheduleTurns();
        game.getBoard().getCouncilPalace().reset();
        if ((round % 2) == 0) {// even round -> VATICAN report
            stop();
            report = true;
            game.getGameController().handleReport(period);
        } else {
            game.setupForRound();
            saveGame();
        }
    }

    /**
     * Handles the period change.
     */
    public void changePeriod() {
        game.setTurnInfo();
        period++;
        if (period >= PERIODS) {
            destroy();
            return;
        }
        game.setupForPeriod(period);
        running = true;
        turn-=1;
        if (!saveGame()) {
            changeTurn();
        }
    }

    /**
     * Sets the report.
     * @param report the boolean indicating if the report is going on.
     */
    public void setReport(boolean report) {
        this.report = report;
    }

    /**
     * Saves the game.
     * @return true if the game can be saved, false otherwise.
     */
    private boolean saveGame() {
        if (saving && !game.getGameController().isOccupied()) {
            game.saveGame();
            return true;
        }
        return false;
    }

    /**
     * According to council palace status, changes the turn order.
     */
    private void rescheduleTurns() {
        List<Player> scheduledPlayers = new ArrayList<>();
        scheduledPlayers.addAll(game.getBoard().getCouncilPalace().getScheduledPlayers());
        List<Player> tmp = new ArrayList<>();
        tmp.addAll(scheduledPlayers);
        for (Player player : game.getPlayers()) {
            if (!tmp.contains(player)) {
                tmp.add(player);
            }
        }
        game.setPlayers(tmp);
    }

    /**
     * Runs the turn handling.
     */
    public void start() {
        running = true;
        changeTurn();
    }

    /**
     * Stops the turn handling.
     */
    void stop() {
        running = false;
        turnTimer.cancel();
    }

    /**
     * Stops the turn handling and finishes the game.
     */
    private void destroy() {
        stop();
        game.endGame();
    }

    /**
     * Sets the saving to true.
     */
    public void tryToSave() {
        saving = true;
    }

    /**
     * @return a string representing the current turn handler status.
     */
    @Override
    public String toString() {
        turn-=1;
        return currentTurnIndex + "\n" + turn + "\n" + round + "\n" + period;
    }

    /**
     * @return the username of the player of this turn.
     */
    public String getTurnOf() {
        return game.getPlayers().stream().filter(Player::isYourTurn).map(Player::getUsername).findFirst().orElse(null);
    }

    /**
     * Creates a new turn handler for the game, loading it from file.
     * @param game the game.
     * @return the turn handler created.
     */
    static TurnHandler loadTurnHandler(Game game) {
        TurnHandler turnHandler = new TurnHandler(game);
        String file = Utils.loadFileAsString("resources/gameSaves/turns_" +game.getPlayers().stream().sorted((a,b)->a.getUsername().compareTo(b.getUsername())).map(Player::getUsername).collect(Collectors.joining(" - ")));
        String[] fileParts = file.split("\n");
        turnHandler.currentTurnIndex = Integer.parseInt(fileParts[0]);
        turnHandler.turn = Integer.parseInt(fileParts[1]);
        turnHandler.round = Integer.parseInt(fileParts[2]);
        turnHandler.period = Integer.parseInt(fileParts[3]);
        return turnHandler;
    }

    /**
     * @return the current round.
     */
    int getRound() {
        return round;
    }

    /**
     * @return the current period.
     */
    int getPeriod() {
        return period;
    }
    
}
