package game.tests;

import Classes.*;
import enums.Action;
import enums.Rank;
import enums.Suite;
import navis.injection.Resettable;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class HumanTest {

    protected Hand mockedHumanHand = null;
    protected Hand mockedBotHand = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsoleIO = null;
    protected Operations mockedOps = null;

    public Resettable withMockOps() {
        mockedOps = mock(Operations.class);
        return Dependencies.operations.override(() -> mockedOps);
    }

    public Resettable withMockHumanHand() {
        mockedHumanHand = mock(Hand.class);
        return Dependencies.humanhand.override(() -> mockedHumanHand);
    }

    public Resettable withMockBotHand() {
        mockedBotHand = mock(Hand.class);
        return Dependencies.bothand.override(() -> mockedBotHand);
    }

    public Resettable withMockPrompt() {
        mockedPrompt = mock(Prompt.class);
        return Dependencies.prompt.override(() -> mockedPrompt);
    }

    public Resettable withMocKConsoleIO() {
        mockedConsoleIO = mock(ConsoleIO.class);
        return Dependencies.console.override(() -> mockedConsoleIO);
    }

    @Before
    public void setup() {
        withMockHumanHand();
        withMockBotHand();
        withMocKConsoleIO();
        withMockPrompt();
        withMockOps();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.humanhand.close();
        Dependencies.bothand.close();
        Dependencies.operations.close();
        Dependencies.console.close();
        Dependencies.prompt.close();
    }

    @Test
    public void returns_hit_when_user_enters_h() throws java.io.IOException {
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("h");

        Action action = humanPlayer.nextAction(mockedBotHand);

        assertEquals(action, Action.Hit);
    }

    @Test
    public void returns_stay_when_user_enters_s() throws java.io.IOException {
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("s");

        Action action = humanPlayer.nextAction(mockedBotHand);

        assertEquals(action, Action.Stay);
    }

    @Test
    public void returns_the_correct_hand() {
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        Card card1 = new Card(Suite.Clubs, Rank.Eight);
        Card card2 = new Card(Suite.Diamonds, Rank.Jack);
        HashSet<Card> expectedCards = new HashSet<>();
        expectedCards.add(card1);
        expectedCards.add(card2);
        when(mockedHumanHand.getCards()).thenReturn(expectedCards);

        HashSet<Card> humanCards = new HashSet<>(humanPlayer.getHand().getCards());

        assertEquals(expectedCards, humanCards);
    }

    @Test
    public void returns_busted_when_busted() {
        HumanPlayer humanPlayer = Dependencies.humanPlayer.make();
        when(mockedOps.isPlayerBusted(humanPlayer)).thenReturn(true);

        Action action = humanPlayer.nextAction(mockedHumanHand);

        assertEquals(action, Action.Busted);
    }

}
