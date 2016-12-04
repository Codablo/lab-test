package Classes;

import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 12/4/16.
 */
public class BlackJackHelper {

    public void displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome) {
        Score score = Dependencies.score.make();
        ConsoleIO consoleIO = Dependencies.console.make();
        if (outcome == null) {
            outcome = Outcome.None;
        }

        if (outcome == Outcome.Push) {
            consoleIO.writeToConsole(String.format("The game is a Push!"));
        } else {
            consoleIO.writeToConsole(String.format("The winner is " + outcome.text + "!"));
        }
        consoleIO.writeToConsole(String.format("\nYour Hand: (" + score.getScore(humanPlayer.getHand()) + ") " +
                humanPlayer.getVisibleHand(false)));
        consoleIO.writeToConsole(String.format("\nDealer Hand: (" + score.getScore(botPlayer.getHand()) + ") " +
                botPlayer.getVisibleHand(false)));
    }

    public Outcome getOutcome(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction) {
        Outcome outcome;
        Operations blackJackOps = Dependencies.operations.make();
        if (humanAction == Action.Busted) {
            outcome = Outcome.Bot;
        } else if (botAction == Action.Busted) {
            outcome = Outcome.Human;
        } else {
            outcome = blackJackOps.getWinner(humanPlayer, botPlayer);
        }
        return outcome;
    }

    public Action dealToBot(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError {
        //deal to bot until stay or bust
        Action botAction = Action.Hit;
        while (botAction != Action.Busted && botAction != Action.Stay) {
            botAction = botPlayer.nextAction(humanPlayer.getHand());
            if (botAction == Action.Hit) {
                botPlayer.addCard(gameDeck);
            }
        }
        return botAction;
    }

    public Action dealToHuman(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError {
        Action humanAction = Action.Hit;
        while (humanAction != Action.Stay && humanAction != Action.Busted) {
            humanAction = humanPlayer.nextAction(botPlayer.getHand());
            if (humanAction == Action.Hit) {
                humanPlayer.addCard(gameDeck);
            }
        }
        return humanAction;
    }

    public void initGame(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError {
        gameDeck.shuffle(Dependencies.random.make(""));

        //deal to human
        humanPlayer.addCard(gameDeck);

        //deal to bot
        botPlayer.addCard(gameDeck);

        //deal to human
        humanPlayer.addCard(gameDeck);

        //deal to bot
        botPlayer.addCard(gameDeck);
    }

}
