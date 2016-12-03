package Interfaces;

import Classes.*;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public interface BlackJackGameI {
    public void play() throws OutOfCardsError;

    public void computeGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction);

    public void displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome);

    public Outcome getOutcome(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction, Operations blackJackOps);

    public Action dealToBot(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    public Action dealToHuman(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    public void initGame(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;
}

