package Interfaces;

import Classes.BotPlayer;
import Classes.Deck;
import Classes.HumanPlayer;
import Classes.OutOfCardsError;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 12/4/16.
 */
public interface BlackJackHelperI {

    public void displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome);

    public Action dealToBot(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    public Action dealToHuman(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

    public void initGame(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError;

}
