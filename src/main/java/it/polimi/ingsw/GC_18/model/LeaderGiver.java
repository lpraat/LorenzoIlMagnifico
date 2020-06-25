package it.polimi.ingsw.GC_18.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.GC_18.server.controller.Blocking;
import it.polimi.ingsw.GC_18.server.controller.WaitingCommand;
import it.polimi.ingsw.GC_18.model.cards.Leader;
import it.polimi.ingsw.GC_18.utils.CardLoader;

/**
 * This class represent the process of leader choosing at the start of the game.
 */
public class LeaderGiver implements Blocking {
    private List<Player> players;
    private List<ArrayList<Leader>> allLeaders;
    private int cardLeft;

    /**
     * Creates a new LeaderGiver given the list of players.
     * @param players the list of players.
     */
    LeaderGiver(List<Player> players) {
        allLeaders = new ArrayList<>();
        this.players = players;
        cardLeft = 4;
    }

    /**
     * @return the number of card left.
     */
    public int getCardLeft() {
        return cardLeft;
    }

    /**
     * This method setup the leader giver for starting the choosing leader process.
     */
    public void setup() {
        ArrayList<Leader> leaderCards = CardLoader.leaderLoader(CardLoader.loadCards(-1, "leaderCards"));
        Collections.shuffle(leaderCards);



        for (int j = 0; j < players.size(); j++) {
            ArrayList<Leader> fourLeader = new ArrayList<>();
            for (int i = 3; i >= 0; i--) {
                fourLeader.add(leaderCards.get(i));
                leaderCards.remove(i);
            }
            allLeaders.add(fourLeader);
        }
    }

    /**
     * Let every player choose one card between four cards and pass the others to the next player.
     */
    public void choose() {
        int i = 0;
        while (i < players.size()*4) {
            for (Player player : players) {
                int index = players.indexOf(player);
                StringBuilder b = new StringBuilder("LEADER - CHOOSE_LEADER, Pick the leader card you prefer by selecting its index - ");
                for (Leader leader: allLeaders.get(index)) {
                    b.append(String.valueOf(allLeaders.get(index).indexOf(leader) + 1));
                    b.append("->");
                    b.append(leader.getName());
                    b.append(" - ");
                }
                player.setYourTurn(true);
                String response = block(player, WaitingCommand.CHOOSE_LEADER, b.toString());
                player.setYourTurn(false);
                int cardIndex = Integer.parseInt(response) - 1;

                player.getPersonalBoard().getLeadersHand().add(allLeaders.get(index).get(cardIndex));
                allLeaders.get(index).remove(cardIndex);
                i++;
            }
            cardLeft -= 1;

            // passes the leaders of each player to the next player.
            Collections.rotate(allLeaders, 1);
        }
    }

    @Override
    public Blocking getLock() {
        return this;
    }

}
