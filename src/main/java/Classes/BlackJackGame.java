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
        BlackJackGame game = Dependencies.blackJackGame.make();

        game.initGame(humanPlayer, botPlayer, gameDeck);
        Action humanAction = game.processHumanTurn(humanPlayer, botPlayer, gameDeck);
        Action botAction = Action.Hit;
        if (humanAction != Action.Busted) {
            botAction = game.processDealerTurn(humanPlayer, botPlayer, gameDeck);
        }
        Outcome outcome = game.getOutcome(humanPlayer, botPlayer, humanAction, botAction);
        game.displayGameResults(humanPlayer, botPlayer, outcome);
    }

    @Override
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

    @Override
    public Action processDealerTurn(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError {
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

    @Override
    public Action processHumanTurn(HumanPlayer humanPlayer, BotPlayer botPlayer, Deck gameDeck) throws OutOfCardsError {
        Action humanAction = Action.Hit;
        while (humanAction != Action.Stay && humanAction != Action.Busted) {
            humanAction = humanPlayer.nextAction(botPlayer.getHand());
            if (humanAction == Action.Hit) {
                humanPlayer.addCard(gameDeck);
            }
        }
        return humanAction;
    }

    @Override
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

    @Override
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
}
