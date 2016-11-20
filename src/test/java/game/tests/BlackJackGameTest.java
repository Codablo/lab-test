package game.tests;

import Classes.*;
import enums.Action;
import enums.Outcome;
import navis.injection.Resettable;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 11/19/16.
 */
public class BlackJackGameTest {


    protected Hand mockedHand = null;
    protected BotPlayer mockedBotPlayer = null;
    protected HumanPlayer mockedHumanPlayer = null;
    protected Score mockedScore = null;
    protected Deck mockedDeck = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsole = null;
    protected Hand mockedHumanHand = null;
    protected Hand mockedBotHand = null;
    protected Operations mockedOperations = null;

    public Resettable withMockPrompt() {
        mockedPrompt = mock(Prompt.class);
        return Dependencies.prompt.override(() -> mockedPrompt);
    }

    public Resettable withMockConsole() {
        mockedConsole = mock(ConsoleIO.class);
        return Dependencies.console.override(() -> mockedConsole);
    }

    public Resettable withMockDecK() {
        mockedDeck = mock(Deck.class);
        return Dependencies.deck.override(() -> mockedDeck);
    }

    public Resettable withMockHumanHand() {
        mockedHumanHand = mock(Hand.class);
        return Dependencies.humanhand.override(() -> mockedHumanHand);
    }

    public Resettable withMockBotHand() {
        mockedBotHand = mock(Hand.class);
        return Dependencies.bothand.override(() -> mockedBotHand);
    }

    public Resettable withMockScore() {
        mockedScore = mock(Score.class);
        return Dependencies.score.override(() -> mockedScore);
    }

    public Resettable withMockBotPlayer() {
        mockedBotPlayer = mock(BotPlayer.class);
        return Dependencies.botPlayer.override(() -> mockedBotPlayer);
    }

    public Resettable withMockHumanPlayer() {
        mockedHumanPlayer = mock(HumanPlayer.class);
        return Dependencies.humanPlayer.override(() -> mockedHumanPlayer);
    }

    public Resettable withMockOperations() {
        mockedOperations = mock(Operations.class);
        return Dependencies.operations.override(() -> mockedOperations);
    }

    @Test
    public void human_wins_game() throws OutOfCardsError, IOException {

        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockConsole();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK();
             Resettable r5 = withMockPrompt();
             Resettable r6 = withMockBotHand();
             Resettable r7 = withMockBotPlayer();
             Resettable r8 = withMockOperations()) {
            BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanPlayer.nextAction(mockedBotHand)).thenReturn(Action.Hit).thenReturn(Action.Stay);
            when(mockedBotPlayer.nextAction(mockedHumanHand)).thenReturn(Action.Stay);
            when(mockedConsole.readFromConsole()).thenReturn("s");
            when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("h").thenReturn("s");
            when(mockedOperations.getWinner(any(), any())).thenReturn(Outcome.Human);

            blackJackGame.play();

            verify(mockedConsole).writeToConsole("The winner is " + Outcome.Human.toString() + "!");
        }
    }

    @Test
    public void bot_wins_game() throws OutOfCardsError, IOException {

        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockConsole();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK();
             Resettable r5 = withMockPrompt();
             Resettable r6 = withMockBotHand();
             Resettable r7 = withMockBotPlayer();
             Resettable r8 = withMockOperations()) {
            BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanPlayer.nextAction(mockedBotHand)).thenReturn(Action.Hit).thenReturn(Action.Stay);
            when(mockedBotPlayer.nextAction(mockedHumanHand)).thenReturn(Action.Stay);
            when(mockedConsole.readFromConsole()).thenReturn("s");
            when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("h").thenReturn("s");
            when(mockedOperations.getWinner(any(), any())).thenReturn(Outcome.Bot);

            blackJackGame.play();

            verify(mockedConsole).writeToConsole("The winner is " + Outcome.Bot.toString() + "!");
        }
    }

    @Test
    public void game_is_a_push() throws OutOfCardsError, IOException {

        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockConsole();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK();
             Resettable r5 = withMockPrompt();
             Resettable r6 = withMockBotHand();
             Resettable r7 = withMockBotPlayer();
             Resettable r8 = withMockOperations()) {
            BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanPlayer.nextAction(mockedBotHand)).thenReturn(Action.Hit).thenReturn(Action.Stay);
            when(mockedBotPlayer.nextAction(mockedHumanHand)).thenReturn(Action.Stay);
            when(mockedConsole.readFromConsole()).thenReturn("s");
            when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("h").thenReturn("s");
            when(mockedOperations.getWinner(any(), any())).thenReturn(Outcome.Push);

            blackJackGame.play();

            verify(mockedConsole).writeToConsole("The game is a Push!");
        }
    }
}
