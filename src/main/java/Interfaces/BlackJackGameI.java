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

    int play() throws OutOfCardsError;

    Outcome getOutcome(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction);

    int displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome);

    Action dealToBot(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    Action dealToHuman(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    int initGame(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;
}

