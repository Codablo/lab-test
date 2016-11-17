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

    protected Hand mockedHand = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsoleIO = null;

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.hand.override(() -> mockedHand);
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
        withMockHand();
        withMocKConsoleIO();
        withMockPrompt();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.hand.close();
        Dependencies.console.close();
        Dependencies.prompt.close();
    }

    @Test
    public void returns_hit_when_user_enters_h() throws java.io.IOException {
        HumanPlayer theHuman = Dependencies.humanPlayer.make();

        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("h");

        assertEquals(theHuman.nextAction(mockedHand), Action.Hit);
    }

    @Test
    public void returns_stay_when_user_enters_s() throws java.io.IOException {
        HumanPlayer theHuman = Dependencies.humanPlayer.make();

        when(mockedPrompt.prompt(anyString(), anyString(), anyString())).thenReturn("s");

        assertEquals(theHuman.nextAction(mockedHand), Action.Stay);
    }

    @Test
    public void returns_the_correct_hand() {
        HumanPlayer theHuman = Dependencies.humanPlayer.make();
        Card card1 = new Card(Suite.Clubs, Rank.Eight);
        Card card2 = new Card(Suite.Diamonds, Rank.Jack);
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card1);
        theCards.add(card2);

        when(mockedHand.getCards()).thenReturn(theCards);

        assertEquals(theCards, theHuman.getHand().getCards());
    }

}
