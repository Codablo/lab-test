package Classes;

import Interfaces.BlackJackGameI;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class BlackJackGame implements BlackJackGameI {
    Deck gameDeck = Dependencies.deck.make();
    HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
    BotPlayer botPlayer = Dependencies.botPlayer.make();
    Operations blackJackOps = Dependencies.operations.make();
    ConsoleIO consoleIO = Dependencies.console.make();

    @Override
    public void play() throws OutOfCardsError {
        //shuffle deck
        gameDeck.shuffle(Dependencies.random.make(""));

        //deal to human
        humanPlayer.getHand().addCard(gameDeck.dealCard());

        //deal to bot
        botPlayer.getHand().addCard(gameDeck.dealCard());

        //deal to human
        humanPlayer.getHand().addCard(gameDeck.dealCard());
        //deal to bot
        botPlayer.getHand().addCard(gameDeck.dealCard());

        //deal to human until stay or bust
        Action humanAction = humanPlayer.nextAction(botPlayer.getHand());
        Action botAction = Action.Hit;
        while (humanAction == Action.Hit) {
            humanPlayer.getHand().addCard(gameDeck.dealCard());
            if (blackJackOps.isPlayerBusted(humanPlayer)) {
                humanAction = Action.Busted;
            } else {
                humanAction = humanPlayer.nextAction(botPlayer.getHand());
            }
        }
        //deal to bot until stay or bust
        if (humanAction != Action.Busted) {
            botAction = botPlayer.nextAction(humanPlayer.getHand());
            while (botAction == Action.Hit) {
                botPlayer.getHand().addCard(gameDeck.dealCard());
                botAction = botPlayer.nextAction(humanPlayer.getHand());
            }
        }

        //calculate winner
        Outcome outcome;
        if (humanAction == Action.Busted) {
            outcome = Outcome.Bot;
        } else if (botAction == Action.Busted) {
            outcome = Outcome.Human;
        } else {
            outcome = blackJackOps.getWinner(humanPlayer, botPlayer);
        }
        Score score = Dependencies.score.make();
        if (outcome == Outcome.Push) {
            consoleIO.writeToConsole(String.format("The game is a Push!"));
        } else {
            consoleIO.writeToConsole(String.format("The winner is " + outcome.toString() + "!"));
        }
        consoleIO.writeToConsole(String.format("\nYour Hand: (" + score.getScore(humanPlayer.getHand()) + ") " +
                humanPlayer.getHand().visibleHand(false)));
        consoleIO.writeToConsole(String.format("\nBOT hand: (" + score.getScore(botPlayer.getHand()) + ") " +
                botPlayer.getHand().visibleHand(false)));
    }
}
