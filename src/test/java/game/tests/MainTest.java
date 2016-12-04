package game.tests;

import Applications.Server;
import Classes.*;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 12/4/16.
 */
public class MainTest {

    protected Score mockedScore = null;
    protected BotPlayer mockedBotPlayer = null;
    protected HumanPlayer mockedHumanPlayer = null;
    protected Deck mockedDeck = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsole = null;
    protected Hand mockedHumanHand = null;
    protected Hand mockedBotHand = null;
    protected Operations mockedOperations = null;
    protected BlackJackHelper mockedHelper = null;
    protected BlackJackGame mockedGame = null;

    public Resettable withMockScore() {
        mockedScore = mock(Score.class);
        return Dependencies.score.override(() -> mockedScore);
    }

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

    public Resettable withMockHelper() {
        mockedHelper = mock(BlackJackHelper.class);
        return Dependencies.blackJackHelper.override(() -> mockedHelper);
    }

    public Resettable withMockGame() {
        mockedGame = mock(BlackJackGame.class);
        return Dependencies.blackJackGame.override(() -> mockedGame);
    }

    @Before
    public void setup() {
        withMockScore();
        withMockHumanHand();
        withMockConsole();
        withMockHumanPlayer();
        withMockDecK();
        withMockPrompt();
        withMockBotHand();
        withMockBotPlayer();
        withMockOperations();
        withMockHelper();
        withMockGame();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.score.close();
        Dependencies.humanhand.close();
        Dependencies.console.close();
        Dependencies.humanPlayer.close();
        Dependencies.deck.close();
        Dependencies.prompt.close();
        Dependencies.bothand.close();
        Dependencies.botPlayer.close();
        Dependencies.operations.close();
        Dependencies.blackJackHelper.close();
        Dependencies.blackJackGame.close();
    }

    @Test
    public void game_is_played() throws OutOfCardsError, IOException, java.lang.Exception {
        Server server = new Server();
        String[] args = {""};

        server.main(args);

        verify(mockedGame, times((1))).play();

    }
}
