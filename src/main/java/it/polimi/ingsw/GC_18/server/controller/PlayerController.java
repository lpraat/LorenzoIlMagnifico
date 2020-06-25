package it.polimi.ingsw.GC_18.server.controller;


import it.polimi.ingsw.GC_18.model.Game;

import java.io.Serializable;

/**
 * This class represents the controller of the player.
 * It is used by the game controller to set the status of a player to waiting.
 * Every time a player is waiting it means the game controller is waiting from the player
 * a Waiting Command.
 */
public final class PlayerController implements Serializable {

    private static final long serialVersionUID = -6647484846749481097L;
    
    private boolean waiting;
    private WaitingCommand waitingCommand;
    private transient Object blockingObject;
    private String response;
    private Game game;

    /**
     * Sets the game.
     * @param game the game to be set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return true if the model is waiting for the player, false otherwise.
     */
    boolean isWaiting() {
        return waiting;
    }

    /**
     * @return the waiting command the game controller is waiting for.
     */
    public WaitingCommand getWaitingCommand() {
        return waitingCommand;
    }

    /**
     * Sets the player controller status in waiting.
     * @param waiting the boolean indicating the status to be set.
     */
    void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * Sets the player controller waiting command.
     * @param waitingCommand the waiting command to be set.
     */
    void setWaitingCommand(WaitingCommand waitingCommand) {
        this.waitingCommand = waitingCommand;
    }

    /**
     * @return the blocking object where the thread running on the model is waiting.
     */
    Object getBlockingObject() {
        return blockingObject;
    }

    /**
     * @return the player's response.
     */
    public String getResponse() {
        return response;
    }

    /**
     * Sets the player response.
     * @param response the response to be set.
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Sets the blocking object.
     * @param blockingObject the blocking object to be set.
     */
    void setBlockingObject(Object blockingObject) {
        this.blockingObject = blockingObject;
    }

    /**
     * Sets the player waiting by setting the waiting command and the blocking object.
     * @param waitingCommand the waiting command.
     * @param blockingObject the blocking object.
     */
    public void set(WaitingCommand waitingCommand, Object blockingObject) {
        this.waiting = true;
        this.waitingCommand = waitingCommand;
        this.blockingObject = blockingObject;
    }

}
