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
        Deck gameDeck = Dependencies.deck.make(); // better as an argument, because you become the "owner" of it
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        BotPlayer botPlayer = Dependencies.botPlayer.make(); // same as deck...Game owns these

        initGame(humanPlayer, botPlayer, gameDeck);
        Action humanAction = dealToHuman(humanPlayer, botPlayer, gameDeck);
        Action botAction = Action.Hit;
        if (humanAction != Action.Busted) {
            botAction = dealToBot(humanPlayer, botPlayer, gameDeck);
        }
        computeGameResults(humanPlayer, botPlayer, humanAction, botAction);
    }

    public void computeGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction) {
        Operations blackJackOps = Dependencies.operations.make();
        Outcome outcome = getOutcome(humanPlayer, botPlayer, humanAction, botAction, blackJackOps);
        displayGameResults(humanPlayer, botPlayer, outcome);
    }

    public void displayGameResults(HumanPlayer humanPlayer, BotPlayer botPlayer, Outcome outcome) {
        Score score = Dependencies.score.make();
        ConsoleIO consoleIO = Dependencies.console.make();

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

    public Outcome getOutcome(HumanPlayer humanPlayer, BotPlayer botPlayer, Action humanAction, Action botAction, Operations blackJackOps) {
        Outcome outcome;
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

    // TODO: behavior "asks player for hit/stay until they stay or bust"
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
