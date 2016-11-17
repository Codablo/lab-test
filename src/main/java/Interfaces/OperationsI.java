package Interfaces;

import Classes.*;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public interface OperationsI {
    void dealCardToPlayer(PlayerI thePlayer, Deck theDeck)throws OutOfCardsError;

    boolean isPlayerBusted(PlayerI thePlayer);

    void completeDealToPlayer(PlayerI player,PlayerI otherPlayer, Deck theDeck)throws OutOfCardsError ;

    Outcome getWinner(HumanPlayer theHuman, BotPlayer theBot);

    boolean handHasBlackJack(Hand theHand);
}
