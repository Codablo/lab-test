package Interfaces;

import Classes.*;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public interface OperationsI {
    void dealCardToPlayer(PlayerI player, Deck deck) throws OutOfCardsError;

    boolean isPlayerBusted(PlayerI player);

    void completeDealToPlayer(PlayerI player, PlayerI otherPlayer, Deck theDeck) throws OutOfCardsError;

    Outcome getWinner(HumanPlayer human, BotPlayer bot);

    boolean handHasBlackJack(Hand hand);
}
