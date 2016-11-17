package game.tests;

import Classes.*;
import enums.Rank;
import enums.Suite;
import navis.injection.Resettable;
import org.junit.Test;

import java.util.HashSet;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class OperationsTest {

    protected Hand mockedHand = null;
    protected HumanPlayer mockedHumanPlayer = null;
    protected Score mockedScore = null;

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.hand.override(() -> mockedHand);
    }

    public Resettable withMockScore() {
        mockedScore = mock(Score.class);
        return Dependencies.score.override(() -> mockedScore);
    }

    public Resettable withMockHumanPlayer() {
        mockedHumanPlayer = mock(HumanPlayer.class);
        return Dependencies.humanPlayer.override(() -> mockedHumanPlayer);
    }

    Card card1 = new Card(Suite.Clubs, Rank.Ace);
    Card card2 = new Card(Suite.Diamonds, Rank.Jack);
    Card card3 = new Card(Suite.Diamonds, Rank.Eight);
    int blackJackScore = 21;

    @Test
    public void blackjack_is_detected_for_ace_and_ten_value_card(){
        Operations theOps = Dependencies.operations.make();
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card1);
        theCards.add(card2);

        try(Resettable r1 = withMockHand(); Resettable r2 = withMockScore()){
            when(mockedHand.getCards()).thenReturn(theCards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore);
            when(mockedHand.size()).thenReturn(theCards.size());

            assertTrue(theOps.handHasBlackJack(mockedHand));
        }
    }

    @Test
    public void blackjack_is_not_detected_for_score_of_21_greater_than_2_cards(){
        Operations theOps = Dependencies.operations.make();
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card1);
        theCards.add(card2);
        theCards.add(card3);

        try(Resettable r1 = withMockHand(); Resettable r2 = withMockScore()){
            when(mockedHand.getCards()).thenReturn(theCards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore);
            when(mockedHand.size()).thenReturn(theCards.size());

            assertFalse(theOps.handHasBlackJack(mockedHand));
        }
    }

    @Test
    public void blackjack_is_not_detected_for_score_not_equal_to_21(){
        Operations theOps = Dependencies.operations.make();
        HashSet<Card> theCards = new HashSet<>();
        theCards.add(card1);
        theCards.add(card2);
        theCards.add(card3);

        try(Resettable r1 = withMockHand(); Resettable r2 = withMockScore()){
            when(mockedHand.getCards()).thenReturn(theCards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore-1);
            when(mockedHand.size()).thenReturn(theCards.size());

            assertFalse(theOps.handHasBlackJack(mockedHand));
        }
    }

}
