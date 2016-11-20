package Classes;

import Interfaces.OperationsI;
import Interfaces.PlayerI;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class Operations implements OperationsI {
    final int blackJackScore = 21;
    final int handSizeForBlackJack = 2;

    public void dealCardToPlayer(PlayerI player, Deck theDeck) throws OutOfCardsError {
        player.getHand().addCard(theDeck.dealCard());
    }

    public boolean isPlayerBusted(PlayerI player) {
        Score theScore = Dependencies.score.make();
        return theScore.getScore(player.getHand()) > blackJackScore;
    }

    public void completeDealToPlayer(PlayerI player, PlayerI otherPlayer, Deck deck) throws OutOfCardsError {
        while (player.nextAction(otherPlayer.getHand()) == Action.Hit) {
            dealCardToPlayer(player, deck);
        }
    }

    public Outcome getWinner(HumanPlayer humanPlayer, BotPlayer botPlayer) {
        Score theScore = Dependencies.score.make();
        int botScore = theScore.getScore(botPlayer.getHand());
        int humanScore = theScore.getScore(humanPlayer.getHand());
        if ((handHasBlackJack(botPlayer.getHand()) && !handHasBlackJack(humanPlayer.getHand())) ||
                (botScore > humanScore)) {
            return Outcome.Bot;
        }
        if ((handHasBlackJack(humanPlayer.getHand()) && !handHasBlackJack(botPlayer.getHand())) ||
                (humanScore > botScore)) {
            return Outcome.Human;
        }

        return Outcome.Push;
    }

    public boolean handHasBlackJack(Hand hand) {
        Score theScore = Dependencies.score.make();
        if (theScore.getScore(hand) != blackJackScore) {
            return false;
        }
        if (hand.size() != handSizeForBlackJack) {
            return false;
        }
        return (true);
    }
}
