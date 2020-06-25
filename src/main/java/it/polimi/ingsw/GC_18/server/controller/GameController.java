package it.polimi.ingsw.GC_18.server.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.*;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.LeaderStatus;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.utils.GameUtils;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Represents the controller of the MVC of the server side.
 * This class represents the game controller. It is the interface between the clients and the model.
 * It takes game commands sent from the clients and handles the model updates.
 */
public final class GameController {

    private Game game;

    private boolean occupied; // indicates if the game controller is occupied working on the model

    private Thread t; // the thread running on the model for the updates

    private boolean started; // indicates if the game has started

    private Timer timer; // the timer used in the leader draft and in the church report for setting the limit
                         // of time a player has to do his choice.

    /**
     * Creates a new GameController for the game.
     * @param game the game.
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * Handles the input received from a player.
     * @param username the username of the player.
     * @param command the command type.
     * @param description the description of the command.
     */
    public void handleInput(String username, String command, String description) {
        Player player = game.getPlayers().stream().filter(p -> p.getUsername().equals(username)).findFirst().orElse(null);
        if (player == null)
            return;

        if (!started) {
            if ("CHOOSE".equalsIgnoreCase(command) && player.getController().getWaitingCommand() != null && player.getController().getWaitingCommand().equals(WaitingCommand.CHOOSE_LEADER)) {
                handleInteraction(player, description);
                return;
            } else if (player.getController().getWaitingCommand() != null && player.getController().getWaitingCommand().equals(WaitingCommand.CHOOSE_LEADER)) {
                notifyPlayer(player, "Waiting for: " + player.getController().getWaitingCommand().getName());
                return;
            } else {
                notifyPlayer(player, "Leader choosing going on, other players are choosing their leaders, wait your time");
                return;
            }
        }

        if ("PLAYERS_STATUS".equalsIgnoreCase(command)) {
            notifyPlayer(player, GameInfoCli.playersStatus(game));
            return;
        }

        if ("BOARD_STATUS".equalsIgnoreCase(command)) {
            notifyPlayer(player, GameInfoCli.boardStatus(game.getBoard()));
            return;
        }

        if ("CHOOSE".equalsIgnoreCase(command) && player.getController().getWaitingCommand() != null && player.getController().getWaitingCommand().equals(WaitingCommand.ASK_SUPPORT)) {
            handleInteraction(player, description);
            return;
        }

        if (occupied && !game.getBoard().getVatican().isDoneReporting()) {
            if (player.getController().getWaitingCommand() != null && player.getController().getWaitingCommand().equals(WaitingCommand.ASK_SUPPORT)) {
                notifyPlayer(player, "Waiting for: " + player.getController().getWaitingCommand().getName());
            } else {
                notifyPlayer(player, "Vatican report going on, wait!");
            }
            return;
        }

        if (!player.isYourTurn()) {
            notifyPlayer(player, "It's not your turn!");
            return;
        }

        if (player.getController().isWaiting()) {
            handleInteraction(player, description);
        } else {
            if (!occupied) {
                if ("PLACE".equalsIgnoreCase(command)) {
                    if (player.hasPlacedPawn()) {
                        notifyPlayer(player, "You have already placed a pawn this turn!");
                        return;
                    }
                    handlePlace(player, description);
                } else if ("LEADER".equalsIgnoreCase(command)) {
                    handleLeader(player, description);
                } else {
                    notifyPlayer(player, "Invalid command");
                }
            } else {
                if (player.getController().getWaitingCommand() != null) {
                    notifyPlayer(player, "Waiting for: " + player.getController().getWaitingCommand().getName());
                } else {
                    notifyPlayer(player, "Something went wrong");
                }
            }
        }
    }

    /**
     * Handles an action place in the model after receiving a place command from the player.
     * @param player the player.
     * @param description the place description.
     */
    private void handlePlace(Player player, String description) {
        String[] descriptionParts = description.split(" - ");
        if (descriptionParts.length != 3) {
            notifyPlayer(player, "Invalid place command");
            return;
        }

        try {
            // check if the action place, the pawn and the servants spent are valid
            ActionPlace actionPlace = Parser.parseActionPlace(player, descriptionParts[0]);
            Pawn pawn = Parser.parsePawn(player, descriptionParts[1]);
            int servantsSpent = Parser.parseServants(player, descriptionParts[2]);

            // set the controller to occupied and starts a new thread that does the place
            occupied = true;
            t = new Thread(() ->
            {
                if (checkPlace(pawn, actionPlace, servantsSpent)) {
                    notifyClients("CHAT", "Game notify: " + player.getUsername() + " placed pawn " + pawn.toString() + " in " + actionPlace.toString() +
                            " spending " + String.valueOf(servantsSpent) + " servants");
                    player.setPlacedPawn(true);
                    notifyClients(game);
                } else {
                    notifyPlayer(player, "You can't do this action!");
                }
                occupied = false;
            });

            t.start();
        } catch (IllegalArgumentException e) {
            notifyPlayer(player, e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the church report.
     * @param period the period of the report.
     */
    public void handleReport(int period) {

        // starts a new thread that asks every player for their choice
        occupied = true;
        t = new Thread( ()->
        {
            notifyClients("CHAT", "Game notify: " + "church report time!");
            game.getBoard().getVatican().report(period, game.getPlayers());
            timer.cancel();
            occupied = false;
            game.getTurnHandler().setReport(false);
            game.getTurnHandler().changePeriod();
        });

        setTimer();
        t.start();
    }

    /**
     * Handles a leader action after receiving a leader command from the player.
     * @param player the player.
     * @param description the command description.
     */
    private void handleLeader(Player player, String description) {

        String[] descriptionParts = description.split(" - ");
        if (!(descriptionParts.length == 1 || descriptionParts.length == 2)) {
            notifyPlayer(player, "This is not a valid command");
            return;
        }

        if (descriptionParts.length == 2) {

            // handles the activation of a leader
            if ("ACTIVATE".equalsIgnoreCase(descriptionParts[1])) {

                try {
                    // checks if the leader is a valid one
                    Leader leader = Parser.parseLeader(player, descriptionParts[0], LeaderStatus.HAND);

                    // starts a new thread for doing the activation
                    occupied = true;
                    t = new Thread(() ->
                    {
                        if (checkLeaderActivation(player, leader)) {
                            notifyClients("CHAT", "Game notify: leader card " + leader.getName() + " activated by " + player.getUsername());
                            notifyClients(game);
                        } else {
                            notifyPlayer(player, "You can't activate this leader");
                        }
                        occupied = false;
                    });

                    t.start();
                } catch (IllegalArgumentException e) {
                    notifyPlayer(player, e.getMessage());
                    throw e;
                }

            // handles the activation of a once per turn effect
            } else if ("ACTIVATE_ONCE".equalsIgnoreCase(descriptionParts[1])) {

                try {
                    // checks if the once per turn effect is a valid one
                    OncePerTurnEffect oncePerTurn = Parser.parseOnceEffect(player, descriptionParts[0]);
                    occupied = true;

                    // starts a new thread for doing the activation
                    t = new Thread(() ->
                    {
                        if (player.activateOnce(oncePerTurn)) {
                            notifyClients("CHAT", "Game notify: once per turn effect " + oncePerTurn.toString() + " activated by " + player.getUsername());
                            notifyClients(game);
                        } else {
                            notifyPlayer(player, "You have already activated this one");
                        }
                        occupied = false;
                    });

                } catch (IllegalArgumentException e) {
                    notifyPlayer(player, e.getMessage());
                    throw e;
                }
                t.start();


            // handles the discard of a leader card
            } else if ("DISCARD".equalsIgnoreCase(descriptionParts[1])) {

                try {
                    // checks if the leader is a valid one
                    Leader leader = Parser.parseLeader(player, descriptionParts[0], LeaderStatus.HAND);
                    occupied = true;

                    // starts a new thread that does the discard.
                    t = new Thread(() ->
                    {
                        player.discardLeader(leader);
                        notifyClients("CHAT", "Game notify: leader card " + leader.getName() + " discarded by " + player.getUsername());
                        notifyClients(game);
                        occupied = false;
                    });

                    t.start();
                } catch (IllegalArgumentException e) {
                    notifyPlayer(player, e.getMessage());
                    throw e;
                }

            } else {
                notifyPlayer(player, "Invalid leader command");
            }

        } else {
            switch (descriptionParts[0].toUpperCase()) {
                // shows the player the leader cards he has in hand
                case "SHOW_HAND":
                    notifyPlayer(player, player.getPersonalBoard().showLeaders(LeaderStatus.HAND));
                    break;

                // shows the player the leader cards he has played
                case "SHOW_PLAYED":
                    notifyPlayer(player, player.getPersonalBoard().showLeaders(LeaderStatus.PLAYED));
                    break;

                // shows the player the once per turn effects he has
                case "SHOW_ONCE":
                    notifyPlayer(player, player.getPersonalBoard().showOnces());
                    break;

                default:
                    notifyPlayer(player, "Invalid leader command");
            }
        }
    }

    /**
     * Handles the leader cards draft. Then it starts the turn handler.
     */
    public void handleLeaderGive() {
        occupied = true;
        t = new Thread( () -> {
            notifyClients("CHAT", "Game notify: " + "game started! Wait your time and choose your leader");
            game.getLeaderGiver().setup();
            game.getLeaderGiver().choose();
            timer.cancel();
            occupied = false;
            started = true;
            game.getTurnHandler().start();
        });
        t.start();
        setTimer();
    }

    /**
     * Handles the player interaction. If a player controller is waiting it means an interaction is needed.
     * The player sends the waiting command and this method handles it.
     * @param player the player.
     * @param description the description of the waiting command.
     */
    private void handleInteraction(Player player, String description) {
        String commandName = player.getController().getWaitingCommand().getName();

        // checks if the description command is valid
        if (!checkDescription(player, commandName, description)) {
            notifyPlayer(player, "This is not the expected command");
            return;
        }

        if (player.getController().getWaitingCommand().equals(WaitingCommand.CHOOSE_LEADER)) {
            setTimer();
        }

        if (player.getController().getWaitingCommand().equals(WaitingCommand.ASK_SUPPORT)) {
            setTimer();
        }

        notifyInteraction(player, description);

        if (!player.isChainActive()) {
            notifyClients(game);
        }
        notifyPlayer(player, commandName + " - " + description + " done");

    }

    /**
     * If a timer is active it cancels it, otherwise it starts a new timer.
     */
    private void setTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setDefaultPlayerAction(1);
                setTimer();
            }
        }, Utils.secondsToMills(TurnHandler.TURN_TIMEOUT_IN_SECONDS));
    }

    /**
     * @return true if the game controller is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Sets a default response because of timeout to a waiting command according to the type of waiting command. If the waiting command needs
     * a number response that must be greater than zero it sets it to chooseNumber.
     * @param chooseNumber the number.
     */
    private void setDefaultPlayerAction(int chooseNumber) {
        for (Player player : game.getPlayers()) {
            if (player.getController().getWaitingCommand() != null && player.isYourTurn()) {
                WaitingCommand waitingCommand = player.getController().getWaitingCommand();
                String response;

                if (waitingCommand.equals(WaitingCommand.EXTRA_ANY) || waitingCommand.equals(WaitingCommand.EXTRA_BUILDING) || waitingCommand.equals(WaitingCommand.EXTRA_CHARACTER) ||
                        waitingCommand.equals(WaitingCommand.EXTRA_VENTURE) || waitingCommand.equals(WaitingCommand.EXTRA_TERRITORY)) {
                    response = "SKIP";
                } else if (waitingCommand.equals(WaitingCommand.EXTRA_PRODUCTION) || waitingCommand.equals(WaitingCommand.EXTRA_HARVEST)){
                    response = "0";
                } else if (waitingCommand.equals(WaitingCommand.ASK_ACTIVATION) || (waitingCommand.equals(WaitingCommand.ASK_SUPPORT))) {
                    response = "NO";
                } else if (waitingCommand.equals(WaitingCommand.VENTURE_COST)) {
                    response = "NORMAL";
                } else if (waitingCommand.equals(WaitingCommand.PAWN_CHOOSE)) {
                    response = "BLACK";
                } else {
                    response = String.valueOf(chooseNumber);
                }


                notifyInteraction(player, response);

                notifyPlayer(player, "Action timeout! Default response taken : " + response);
                notifyPlayer(player, "CLOSE_FRAMES");
                notifyClients(game);
                break;
            }
        }
    }

    /**
     * Checks if the player controller is in waiting status, if so sets the response to the controller and notifies
     * the thread running on the model that was waiting for the command.
     * @param player the player.
     * @param response the response description of the waiting command.
     */
    private synchronized void notifyInteraction(Player player, String response) {

        if (player.getController().getBlockingObject() != null) {
            synchronized (player.getController().getBlockingObject()) {
                player.getController().setResponse(response.toUpperCase());
                player.getController().setWaiting(false);
                player.getController().setWaitingCommand(null);
                player.getController().getBlockingObject().notifyAll();
                player.getController().setBlockingObject(null);
            }
        }
    }

    /**
     * Checks if the game controller is occupied. If so it realizes all of the interactions needed in order to free
     * the game controller.
     */
    public void interruptThread() {

        if (t == null) {
            occupied = false;
        } else if (occupied) {
            int defaultChooseNumber = 1;
            while (occupied) {

                // for every player that needs a response, sets a new default response, and increase it in case of a
                // council privileges waiting command that needs more than one interaction.
                if (game.getPlayers().stream().filter(player1 -> player1.getController().isWaiting()).collect(Collectors.toList()).get(0) != null) {
                    setDefaultPlayerAction(defaultChooseNumber);
                    defaultChooseNumber += 1;
                }
            }
        }
    }

    /**
     * Checks if the command and its description are valid.
     * @param player the player that sends the command.
     * @param commandName the command.
     * @param description the description.
     * @return true if the command is valid, false otherwise.
     */
    private boolean checkDescription(Player player, String commandName, String description) {
        switch (commandName.toUpperCase()) {
        case "ASK_SUPPORT":
        case "ASK_ACTIVATION":
            return "YES".equalsIgnoreCase(description) || "NO".equalsIgnoreCase(description);
        case "EXCHANGE_OR":
        case "FLOORCHANGE_OR":
            return "1".equalsIgnoreCase(description) || "2".equalsIgnoreCase(description);

        case "COUNCIL_PRIVILEGES":
            return "1".equalsIgnoreCase(description) || "2".equalsIgnoreCase(description) || "3".equalsIgnoreCase(description) || "4".equalsIgnoreCase(description) || "5".equalsIgnoreCase(description);

        case "VENTURE_COST":
            return "NORMAL".equalsIgnoreCase(description) || "ALTERNATIVE".equalsIgnoreCase(description);

        case "EXTRA_PRODUCTION":
        case "EXTRA_HARVEST":
            try {
                if (GameUtils.compareServants(player, Integer.parseInt(description))) {
                    return true;
                } else {
                    notifyPlayer(player, "You don't have enough servants");
                    return false;                    }
            } catch (NumberFormatException e) {
                return false;
            }
        case "EXTRA_CHARACTER":
            return checkExtraPick(player, description, TowerColor.BLUE);
        case "EXTRA_TERRITORY":
            return checkExtraPick(player, description, TowerColor.GREEN);
        case "EXTRA_BUILDING":
            return checkExtraPick(player, description, TowerColor.YELLOW);
        case "EXTRA_VENTURE":
            return checkExtraPick(player, description, TowerColor.PURPLE);
        case "EXTRA_ANY":
            return checkExtraPick(player, description, TowerColor.ANY);

        case "PAWN_CHOOSE":
            return "BLACK".equalsIgnoreCase(description) || "WHITE".equalsIgnoreCase(description) || "ORANGE".equalsIgnoreCase(description);

        case "CHOOSE_LEADER":
            try {
                int choose = Integer.parseInt(description);
                return choose > 0 && choose <= game.getLeaderGiver().getCardLeft();
            } catch (NumberFormatException e) {
                return false;
            }

        case "LEADER_COPY":
            try {
                return Integer.parseInt(description) <= player.getController().getGame().getLeadersPlayed(player).size()
                        && Integer.parseInt(description) > 0;
            } catch (NumberFormatException e) {
                return false;
            }

        default:
            return false;
        }
    }

    /**
     * Checks if the description for an extra floor pick is valid.
     * @param player the player that does the extra floor pick.
     * @param description the description.
     * @param towerColor the tower color of the extra floor pick.
     * @return true if the description is valid false, otherwise.
     */
    private boolean checkExtraPick(Player player, String description, TowerColor towerColor) {
        if (checkSkip(description)) {
            return true;
        }
        return checkTower(player, description, towerColor);
    }

    /**
     * Checks if the tower of an extra floor pick is valid.
     * @param player the player that does the extra floor pick.
     * @param description the description.
     * @param towerColor the tower color of the extra floor pick.
     * @return true if the tower is valid, false otherwise.
     */
    private boolean checkTower(Player player, String description, TowerColor towerColor) {
        String[] descriptionSplit = description.split(" - ");
        for (ExtraFloor extraFloor: towerColor.getExtraFloors()) {
            if (descriptionSplit[0].equalsIgnoreCase(extraFloor.getName())) {
                try {
                    if (GameUtils.compareServants(player, Integer.parseInt(descriptionSplit[1]))) {
                        return true;
                    } else {
                        notifyPlayer(player, "You don't have enough servants");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * @param description the description.
     * @return true if the description is of type skip, false otherwise.
     */
    private boolean checkSkip(String description) {
        return "SKIP".equalsIgnoreCase(description);
    }


    /**
     * Checks if the player can activate the leader.
     * @param player the player.
     * @param leader the leader.
     * @return true if the player can activate the leader, false otherwise.
     */
    private boolean checkLeaderActivation(Player player, Leader leader) {
        return player.activateLeader(leader);
    }

    /**
     * Checks if the player can do the place.
     * @param pawn the pawn of the place.
     * @param actionPlace the action place.
     * @param servants the servants spent.
     * @return true if the player can do the place, false otherwise.
     */
    private boolean checkPlace(Pawn pawn, ActionPlace actionPlace, int servants) {
        return game.place(pawn, actionPlace, servants);
    }

    /**
     * Sends the client's player a message.
     * @param player the player.
     * @param message the message.
     */
    public void notifyPlayer(Player player, String message) {
        game.getRoom().getClients().stream().filter(cli -> cli.getUsername().equals(player.getUsername()))
        .forEach(cli -> cli.outputToClient("NOTIFY - "+message));
    }

    /**
     * Sends all clients a command.
     * @param command the command.
     * @param description the command description.
     */
    public void notifyClients(String command, String description) {
        game.getRoom().propagateInRoom(command, description);
    }

    /**
     * Sends all clients the game object.
     * @param game the game object.
     */
    public void notifyClients(Game game) {
        game.getRoom().sendGame(game);
    }

    /**
     * Sets started to true.
     */
    public void setStarted() {
        started = true;
    }

}
