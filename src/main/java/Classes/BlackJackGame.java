package Classes;

import Interfaces.BlackJackGameI;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class BlackJackGame implements BlackJackGameI {

    @Override
    public void play() throws OutOfCardsError {
        Deck gameDeck = Dependencies.deck.make();
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        BotPlayer botPlayer = Dependencies.botPlayer.make();
        BlackJackHelper blackJackHelper = Dependencies.blackJackHelper.make();

        blackJackHelper.initGame(humanPlayer, botPlayer, gameDeck);
        Action humanAction = blackJackHelper.dealToHuman(humanPlayer, botPlayer, gameDeck);
        Action botAction = Action.Hit;
        if (humanAction != Action.Busted) {
            botAction = blackJackHelper.dealToBot(humanPlayer, botPlayer, gameDeck);
        }
        Outcome outcome = blackJackHelper.getOutcome(humanPlayer, botPlayer, humanAction, botAction);
        blackJackHelper.displayGameResults(humanPlayer, botPlayer, outcome);
    }
}
