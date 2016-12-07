package Interfaces;

import Classes.BotPlayer;
import Classes.Deck;
import Classes.HumanPlayer;
import Classes.OutOfCardsError;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public interface BlackJackGameI {

    void play() throws OutOfCardsError;

    Outcome getOutcome(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction);

    void displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome);

    Action processDealerTurn(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    Action processHumanTurn(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    void initGame(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;
}

