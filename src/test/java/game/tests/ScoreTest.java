package game.tests;

import Classes.Card;
import Classes.Dependencies;
import Classes.Hand;
import Classes.Score;
import enums.Rank;
import enums.Suite;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class ScoreTest {


    protected Hand mockedHand = null;

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.hand.override(() -> mockedHand);
    }

    @Before
    public void setup() {
        withMockHand();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.hand.close();
    }

    Card card1 = new Card(Suite.Clubs, Rank.Eight);
    Card card2 = new Card(Suite.Diamonds, Rank.Jack);
    Card card3 = new Card(Suite.Diamonds, Rank.Ace);

    @Test
    public void hand_with_ace_over_21_subtracts_10() {
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card1);
        theCards.add(card2);
        theCards.add(card3);
        Score theScore = Dependencies.score.make();

        when(mockedHand.getCards()).thenReturn(theCards);

        assertEquals(theScore.getScore(mockedHand), 19);
    }

    @Test
    public void hand_with_ace_adds_11() {
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card2);
        theCards.add(card3);
        Score theScore = Dependencies.score.make();

        when(mockedHand.getCards()).thenReturn(theCards);

        assertEquals(theScore.getScore(mockedHand), 21);
    }
}
