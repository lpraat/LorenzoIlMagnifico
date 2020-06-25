package it.polimi.ingsw.GC_18.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import it.polimi.ingsw.GC_18.model.actions.Action;
import it.polimi.ingsw.GC_18.model.actions.TowerAction;
import it.polimi.ingsw.GC_18.model.cards.Building;
import it.polimi.ingsw.GC_18.model.cards.Character;
import it.polimi.ingsw.GC_18.model.cards.DevelopmentCard;
import it.polimi.ingsw.GC_18.model.cards.ExcomunicationTile;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.model.cards.Territory;
import it.polimi.ingsw.GC_18.model.cards.Venture;
import it.polimi.ingsw.GC_18.model.effects.GameEffect;
import it.polimi.ingsw.GC_18.model.effects.OncePerTurnEffect;
import it.polimi.ingsw.GC_18.model.effects.game.PawnSet;
import it.polimi.ingsw.GC_18.model.gamepieces.ActionPlace;
import it.polimi.ingsw.GC_18.model.gamepieces.BonusTile;
import it.polimi.ingsw.GC_18.model.gamepieces.Floor;
import it.polimi.ingsw.GC_18.model.gamepieces.Pawn;
import it.polimi.ingsw.GC_18.model.gamepieces.Tower;
import it.polimi.ingsw.GC_18.server.Room;
import it.polimi.ingsw.GC_18.server.UserStats;
import it.polimi.ingsw.GC_18.server.controller.GameController;
import it.polimi.ingsw.GC_18.utils.CardLoader;
import it.polimi.ingsw.GC_18.utils.GameConfigLoader;
import it.polimi.ingsw.GC_18.utils.GameUtils;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * Represents a Game. A player can have 2 to 5 number of players.
 */
public final class Game extends Thread implements Serializable {

    private static final long serialVersionUID = 8711001876454032855L;
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
    private static final int INITIAL_MONEY = 5;

    private static Properties gameProperties;

    private boolean setupDone; // true if the leader choose has been done
    private GameMode gameMode;
    private Board board;
    private int numPlayers;
    private PlayerColor[] playerColors = {PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.RED, PlayerColor.YELLOW, PlayerColor.VIOLET};
    private List<Player> players;

    private transient TurnHandler turnHandler;
    private transient Room room;
    private transient GameController gameController;
    private transient LeaderGiver leaderGiver;
    private int[] turnInfo = {1, 1}; // array that keeps the information about current round and period

    /**
     * Creates a new Game instance. Adds the player to the game by looking at the clients
     * present in the room. Creates the board, the turn handler, the game controller and the leader giver.
     * @param room the Room of the game.
     * @param gameMode the game mode.
     */
    public Game(Room room, GameMode gameMode) {
        // loading model properties
        this.gameMode = gameMode;
        this.room = room;
        numPlayers = room.getClients().size();
        players = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(room.getClients().get(i).getUsername(), playerColors[i]));
        }
        for (Player player : players) {
            player.getController().setGame(this);
        }
        board = new Board(players);
        turnHandler = new TurnHandler(this);
        gameController = new GameController(this);
        leaderGiver = new LeaderGiver(players);

    }

    /**
     * @return the game controller.
     */
    public GameController getGameController() {
        return gameController;
    }


    /**
     * Setup of the game. It gives the players the bonus tiles and the initial resources.
     * It does the setup of the board by setting the excommunication tiles and the decks.
     * Starts the leader choosing.
     */
    private void setupGame() {
        giveBonusTiles();
        giveInitialResources();
        giveMoney();
        setupExcomunicationTiles();
        setCurrentDecks(0);
        putAllCards();
        setDicesValue();
        gameController.handleLeaderGive();
        setupDone = true;
    }

    /**
     * @return the leader giver.
     */
    public LeaderGiver getLeaderGiver() {
        return leaderGiver;
    }

    /**
     * It does the setup of the game for the current turn by reactivating all the once per turn effects.
     */
    void setupForTurn() {
        for (Player player: players) {
            for (OncePerTurnEffect oncePerTurnEffect: player.getPersonalBoard().getOncePerTurnEffects()) {
                oncePerTurnEffect.setActivated(false);
            }
        }
    }

    /**
     * It does the setup of the game for the current period by setting the new decks.
     * @param period the period.
     */
    void setupForPeriod(int period) {
        setCurrentDecks(period);
        setupForRound();
    }

    /**
     * It does the setup of the game for the current round, setting the dices values and
     * putting new cards from the current decks in the towers.
     * Also reactivates all game effect.
     */
    void setupForRound() {
        putAllCards();
        setDicesValue();
        for (Player player: players) {
            player.getBlackPawn().setPlaced(false);
            player.getOrangePawn().setPlaced(false);
            player.getWhitePawn().setPlaced(false);
            player.getNeutralPawn().setPlaced(false);
            player.setPlacedPawn(false);
        }
        board.getTerritoryTower().getFirstFloor().setPawns(new ArrayList<>());
        board.getTerritoryTower().getSecondFloor().setPawns(new ArrayList<>());
        board.getTerritoryTower().getThirdFloor().setPawns(new ArrayList<>());
        board.getTerritoryTower().getFourthFloor().setPawns(new ArrayList<>());

        board.getCharacterTower().getFirstFloor().setPawns(new ArrayList<>());
        board.getCharacterTower().getSecondFloor().setPawns(new ArrayList<>());
        board.getCharacterTower().getThirdFloor().setPawns(new ArrayList<>());
        board.getCharacterTower().getFourthFloor().setPawns(new ArrayList<>());

        board.getBuildingTower().getFirstFloor().setPawns(new ArrayList<>());
        board.getBuildingTower().getSecondFloor().setPawns(new ArrayList<>());
        board.getBuildingTower().getThirdFloor().setPawns(new ArrayList<>());
        board.getBuildingTower().getFourthFloor().setPawns(new ArrayList<>());

        board.getVentureTower().getFirstFloor().setPawns(new ArrayList<>());
        board.getVentureTower().getSecondFloor().setPawns(new ArrayList<>());
        board.getVentureTower().getThirdFloor().setPawns(new ArrayList<>());
        board.getVentureTower().getFourthFloor().setPawns(new ArrayList<>());

        board.getMarket().getCoinSpot().setPawns(new ArrayList<>());
        board.getMarket().getServantSpot().setPawns(new ArrayList<>());

        if (board.getMarket().getMilitarySpot() != null) {
            board.getMarket().getMilitarySpot().setPawns(new ArrayList<>());
        }

        if (board.getMarket().getCouncilSpot() != null) {
            board.getMarket().getCouncilSpot().setPawns(new ArrayList<>());
        }

        board.getHarvestSpace().getHarvestArea().setPawns(new ArrayList<>());
        if (board.getHarvestSpace().getLargeHarvestArea() != null) {
            board.getHarvestSpace().getLargeHarvestArea().setPawns(new ArrayList<>());
        }

        board.getProductionSpace().getProductionArea().setPawns(new ArrayList<>());
        if (board.getProductionSpace().getLargeProductionArea() != null) {
            board.getProductionSpace().getLargeProductionArea().setPawns(new ArrayList<>());
        }

        board.getCouncilPalace().setPawns(new ArrayList<>());

        if (board.getFightSpace() != null) {
            board.getFightSpace().reset();
        }

        for (Player player: players) {
            for (GameEffect gameEffect: player.getPersonalBoard().getGameEffects()) {
                if (gameEffect instanceof PawnSet) {
                    PawnSet pawnSet = (PawnSet) gameEffect;
                    pawnSet.apply(player);
                }
            }
        }

    }


    /**
     * Gives the custom bonus tiles to the players
     */
    private void giveBonusTiles() {
        int index = 0;
        for (Player player : players) {
            player.setBonusTile(new BonusTile(gameMode, index));
            index++;
        }
    }

    /**
     * Gives initial resources to the players.
     */
    private void giveInitialResources() {
        for (Player player : players) {
            GameConfigLoader.loadInitialResources().addResources(player, null);
        }
    }

    /**
     * Gives initial money to the players.
     */
    private void giveMoney() {
        int i = 0;
        for (Player player : players) {
            player.addMoney(INITIAL_MONEY + i, null);
            i++;
        }
    }

    /**
     * Set the current decks in the board at the start of a period.
     * @param period the period.
     */
    private void setCurrentDecks(int period) {
        Deck<Character> characterDeck = new Deck<>();
        List<Character> characters = CardLoader.characterLoader(CardLoader.loadCards(period, "characterCards"));
        for (Character character : characters) {
            characterDeck.addCard(character);
        }
        characterDeck.shuffle();
        board.setCurrentCharacterDeck(characterDeck);

        Deck<Building> buildingDeck = new Deck<>();
        List<Building> buildings = CardLoader.buildingLoader(CardLoader.loadCards(period, "buildingCards"));
        for (Building building : buildings) {
            buildingDeck.addCard(building);
        }
        buildingDeck.shuffle();
        board.setCurrentBuildingDeck(buildingDeck);

        Deck<Venture> ventureDeck = new Deck<>();
        List<Venture> ventures = CardLoader.ventureLoader(CardLoader.loadCards(period, "ventureCards"));
        for (Venture venture : ventures) {
            ventureDeck.addCard(venture);
        }
        ventureDeck.shuffle();
        board.setCurrentVentureDeck(ventureDeck);

        Deck<Territory> territoryDeck = new Deck<>();
        List<Territory> territories = CardLoader.territoryLoader(CardLoader.loadCards(period, "territoryCards"));
        for (Territory territory : territories) {
            territoryDeck.addCard(territory);
        }
        territoryDeck.shuffle();
        board.setCurrentTerritoryDeck(territoryDeck);
    }


    /**
     * Put the cards in towers by picking them from the current decks.
     */
    private void putAllCards() {
        putCardsInTower(board.getTerritoryTower(), board.getCurrentTerritoryDeck());
        putCardsInTower(board.getCharacterTower(), board.getCurrentCharacterDeck());
        putCardsInTower(board.getBuildingTower(), board.getCurrentBuildingDeck());
        putCardsInTower(board.getVentureTower(), board.getCurrentVentureDeck());
    }

    /**
     * Picks a card from the deck and sets it in the tower.
     * @param tower the tower.
     * @param deck the deck to pick the card from.
     * @param <T> the type of card must be a development card.
     */
    private <T extends DevelopmentCard> void putCardsInTower(Tower<T> tower, Deck<T> deck) {
        tower.getFirstFloor().setCard(deck.pickCard());
        tower.getSecondFloor().setCard(deck.pickCard());
        tower.getThirdFloor().setCard(deck.pickCard());
        tower.getFourthFloor().setCard(deck.pickCard());
    }

    /**
     * Sets the excomunication tiles for this game in the church spots.
     */
    private void setupExcomunicationTiles() {
        Deck<ExcomunicationTile> excomunicationTileDeck1 = new Deck<>();
        List<ExcomunicationTile> excomunicationTiles1 = CardLoader.excomunicationLoader(CardLoader.loadCards(0, "excomunicationTiles"));
        for (ExcomunicationTile excomunicationTile: excomunicationTiles1) {
            excomunicationTileDeck1.addCard(excomunicationTile);
        }
        excomunicationTileDeck1.shuffle();
        board.getVatican().getFirstVatican().setExcomunicationTile(excomunicationTileDeck1.pickCard());


        Deck<ExcomunicationTile> excomunicationTileDeck2 = new Deck<>();
        List<ExcomunicationTile> excomunicationTiles2 = CardLoader.excomunicationLoader(CardLoader.loadCards(1, "excomunicationTiles"));
        for (ExcomunicationTile excomunicationTile: excomunicationTiles2) {
            excomunicationTileDeck2.addCard(excomunicationTile);
        }
        excomunicationTileDeck2.shuffle();
        board.getVatican().getSecondVatican().setExcomunicationTile(excomunicationTileDeck2.pickCard());

        Deck<ExcomunicationTile> excomunicationTileDeck3 = new Deck<>();
        List<ExcomunicationTile> excomunicationTiles3 = CardLoader.excomunicationLoader(CardLoader.loadCards(2, "excomunicationTiles"));
        for (ExcomunicationTile excomunicationTile: excomunicationTiles3) {
            excomunicationTileDeck3.addCard(excomunicationTile);
        }
        excomunicationTileDeck3.shuffle();
        board.getVatican().getThirdVatican().setExcomunicationTile(excomunicationTileDeck3.pickCard());


    }

    /**
     * Set the dices values by rolling all the dices.
     */
    private void setDicesValue() {
        board.getDicesBox().rollDices();
        for (Player player : players) {
            player.getBlackPawn().setValue(board.getDicesBox().getDice(DiceColor.BLACK).getValue());
            player.getOrangePawn().setValue(board.getDicesBox().getDice(DiceColor.ORANGE).getValue());
            player.getWhitePawn().setValue(board.getDicesBox().getDice(DiceColor.WHITE).getValue());
            player.getNeutralPawn().setValue(0);
        }
    }


    /**
     * Starts an action. It can be a Normal action or a Tower action.
     * Also applies the servant malus if present.
     * @param pawn the pawn to be places.
     * @param actionPlace the place to place the pawn at.
     * @param servantsSpent the servants spent for modifying the value of the action.
     * @return true if the action can be done, false otherwise.
     */
    public boolean place(Pawn pawn, ActionPlace actionPlace, int servantsSpent) {
        int servants = servantsSpent;
        Player player = pawn.getPlayer();

        if (!GameUtils.compareServants(player, servantsSpent)) {
            return false;
        }
        int servantMalus = player.getPersonalBoard().getServantMalus();
        if (servantMalus > 0) {
            servants = player.calculateServantMalus(servantsSpent, servantMalus);
        }
        if (actionPlace instanceof Floor) {
            @SuppressWarnings("unchecked")
            TowerAction newTowerAction = new TowerAction(pawn, (Floor<? extends DevelopmentCard>) actionPlace, servants);
            if (newTowerAction.check()) {
                newTowerAction.run();
                return true;
            }
            return false;
        } else {
            Action newAction = new Action(pawn, actionPlace, servants);
            if (newAction.check()) {
                newAction.run();
                return true;
            }
            return false;
        }
    }


    /**
     * Runs the game.
     */
    @Override
    public void run() {
        if (!setupDone)
            setupGame();
    }

    /**
     * Loads a game.
     * @param room the room of the game.
     * @param gameId the gameId. The gameId is identified by the players' usernames of the game.
     * @return the game loaded.
     */
    public static Game loadGame(Room room,String gameId) {
        Game game = null;
        try (FileInputStream in = new FileInputStream(gameId);
                ObjectInputStream objectInputStream = new ObjectInputStream(in)
                ) {
            game = (Game) objectInputStream.readObject();
            game.room=room;
            game.turnHandler=TurnHandler.loadTurnHandler(game);
            game.gameController=new GameController(game);
            game.turnHandler.start();
            game.gameController.setStarted();

            // add the observers again because they are removed through serialization
            game.getPlayers().forEach(p -> p.getPersonalBoard().getDynamicEffects().forEach(p::addObserver));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "COULDN'T FIND SAVED GAME FILE", e);
            return null;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "PROBLEMS WITH SAVED GAME FILE", e);
            return null;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "COULDN'T FIND CLASS FOR GAME LOADING", e);
            return null;
        } finally {
            if (!new File(gameId).delete()) {
                ModelLogger.getInstance().logInfo("ERROR DELETING FILE OF MODEL"); 
            }
            if (game != null && !new File("resources/gameSaves/turns_" +game.getPlayers().stream().sorted((a,b)->a.getUsername().compareTo(b.getUsername())).map(Player::getUsername).collect(Collectors.joining(" - "))).delete()) {
                ModelLogger.getInstance().logInfo("ERROR DELETING FILE OF SAVED TURNS");
            }
        }
        return game;
    }


    /**
     * Saves the game.
     */
    void saveGame() {
        turnHandler.stop();
        try (   FileOutputStream fout = new FileOutputStream("resources/gameSaves/model_" +players.stream().sorted((a,b)->a.getUsername().compareTo(b.getUsername())).map(Player::getUsername).collect(Collectors.joining(" - ")));
                ObjectOutputStream oos = new ObjectOutputStream(fout) ) {
            oos.writeObject(this);
            Utils.overwriteFile("resources/gameSaves/turns_" +players.stream().sorted((a,b)->a.getUsername().compareTo(b.getUsername())).map(Player::getUsername).collect(Collectors.joining(" - ")), turnHandler.toString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "PROBLEMS SAVING GAME INSTACE", e);
        }
        getRoom().propagateInRoom("NOTIFY", "GAME SAVED!");
        getRoom().deleteRoom();
        interrupt();
    }

    /**
     * This method does all the end game calculations, gets the game winner, updates the statistics and notifies the clients.
     */
    void endGame() {
        // calculating the winner
        for (Player player: players) {
            calculateEndTerritories(player);
            calculateEndCharacters(player);
            calculateEndVentures(player);
            calculateMilitaryStrenght();
            calculateEndResources(player);
        }
        gameController.notifyClients(this);
        // notifying clients
        Player winner = getWinner();
        gameController.notifyClients("CHAT", "Game notify: " + winner.getUsername() + " IS THE WINNER!");
        gameController.notifyClients("CHAT", "Game notify: " + "GAME ENDED");
        // updating statistics
        for (Player player: players) {
            UserStats userStats = UserStats.getUserStats(player.getUsername());
            if (player != winner) {
                userStats.addGameLost();
            } else {
                userStats.addGameWon();
            }
            userStats.addVictoryPoints(player.getVictoryPoints());
            UserStats.updateUserStatsFile();
        }
        // ending the game handling
        room.gameEnded(winner.getUsername());
        interrupt();
    }

    /**
     * Calculates the winner of the game.
     * @return the game winner.
     */
    private Player getWinner() {
        Player winner = players.get(0);
        for (Player player: players.subList(1, players.size())) {
            if (player.getVictoryPoints() > winner.getVictoryPoints()) {
                winner = player;
            } else if (player.getVictoryPoints() == winner.getVictoryPoints()) {
                winner = (players.indexOf(winner) < players.indexOf(player)) ? winner : player;
            }
        }
        return winner;
    }

    /**
     * Adds the player the end bonus for having a certain amount of territory cards.
     * @param player the player to add the resources to.
     */
    private void calculateEndTerritories(Player player) {
        if (player.getPersonalBoard().checkEndGameNegate(TowerColor.GREEN)) {
            return;
        }
        player.getPersonalBoard().calculateConqueredTerritoriesBonus();
    }

    /**
     * Adds the player the end bonus for having a certain amount of character cards.
     * @param player the player to add the resources to.
     */
    private void calculateEndCharacters(Player player) {
        if (player.getPersonalBoard().checkEndGameNegate(TowerColor.BLUE)) {
            return;
        }
        player.getPersonalBoard().calculateInfluencedCharactersBonus();
    }

    /**
     * Adds the player the end bonus for having a certain amount of venture cards.
     * @param player the player to add the resources to.
     */
    private void calculateEndVentures(Player player) {
        if (player.getPersonalBoard().checkEndGameNegate(TowerColor.PURPLE)) {
            return;
        }
        player.getPersonalBoard().calculateEncouragedVentures();
    }

    /**
     * Adds the player the end bonus for having a certain amount of resources.
     * @param player the player to add the resources to.
     */
    private void calculateEndResources(Player player) {
        player.getPersonalBoard().calculateCollectedResources();
    }


    /**
     * Adds the player victory points according to their military points at the end of the game.
     */
    private void calculateMilitaryStrenght() {

        List<Player> ranking = new ArrayList<>();
        ranking.addAll(players);
        ranking.sort(Comparator.comparingInt(Player::getMilitaryPoints));

        List<Player> firstPlace = new ArrayList<>();
        firstPlace.add(ranking.get(ranking.size()-1));
        List<Player> secondPlace = new ArrayList<>();
        secondPlace.add(ranking.get(ranking.size()-2));

        for (Player player: ranking) {
            if (player.getMilitaryPoints() == firstPlace.get(0).getMilitaryPoints() && !(player.equals(firstPlace.get(0)))) {
                firstPlace.add(player);
            } else if (player.getMilitaryPoints() == secondPlace.get(0).getMilitaryPoints() && !(player.equals(secondPlace.get(0)))) {
                secondPlace.add(player);
            }
        }

        // Handles a draw at the first rank.
        if (firstPlace.size() > 1) {
            for (Player player: firstPlace) {
                player.addVictoryPoints(5, null);
            }
            // Handles a draw at the second rank.
        } else if (secondPlace.size() > 1) {
            for (Player player: secondPlace) {
                player.addVictoryPoints(2, null);
            }
        }
    }

    /**
     * Gets all the leader played by all players except p.
     * @param p the player.
     * @return the list of leaders played.
     */
    public List<Leader> getLeadersPlayed(Player p) {
        List<Leader> leadersPlayed = new ArrayList<>();
        for (Player player: players) {
            if (player != p) {
                leadersPlayed.addAll(player.getPersonalBoard().getLeadersPlayed());
            }
        }
        return leadersPlayed;
    }

    /**
     * Sets the turn info.
     */
    void setTurnInfo() {
        turnInfo[0] = turnHandler.getRound()+1;
        turnInfo[1] = turnHandler.getPeriod()+1;
    }

    /**
     * @return the turn info.
     */
    public int[] getTurnInfo() {
        return turnInfo;
    }

    /**
     * @return the game properties.
     */
    static Properties getGameProperties() {
        return gameProperties;
    }

    /**
     * Sets the game properties
     * @param properties the properties to be set.
     */
    public static void setGameProperties(Properties properties) {
        gameProperties = properties;
    }

    /**
     * @return the turn handler.
     */
    public TurnHandler getTurnHandler() {
        return turnHandler;
    }


    /**
     * @return the list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players.
     * @param players the list of players to be set.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @return the game room.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the game room.
     * @param room the room to be set.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the game's board.
     */
    public Board getBoard() {
        return board;
    }

}