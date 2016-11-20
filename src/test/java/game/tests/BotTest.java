package game.tests;

import Classes.*;
import enums.Action;
import enums.Rank;
import enums.Suite;
import navis.injection.Resettable;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class BotTest {

    protected Hand mockedHand = null;
    protected Score mockedScore = null;
    protected Operations mockedOps = null;

    public Resettable withMockOps() {
        mockedOps = mock(Operations.class);
        return Dependencies.operations.override(() -> mockedOps);
    }

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.bothand.override(() -> mockedHand);
    }

    public Resettable withMockScore() {
        mockedScore = mock(Score.class);
        return Dependencies.score.override(() -> mockedScore);
    }

    @Test
    public void stays_when_score_greater_than_or_equal_17() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockOps()) {
            BotPlayer botPlayer = Dependencies.botPlayer.make();
            when(mockedScore.getScore(any())).thenReturn(17);

            assertEquals(botPlayer.nextAction(mockedHand), Action.Stay);
        }
    }

    @Test
    public void hits_when_score_less_than_17() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockOps()) {
            BotPlayer botPlayer = Dependencies.botPlayer.make();
            when(mockedScore.getScore(any())).thenReturn(16);

            assertEquals(botPlayer.nextAction(mockedHand), Action.Hit);
        }
    }

    @Test
    public void returns_busted_when_busted() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockOps()) {
            BotPlayer botPlayer = Dependencies.botPlayer.make();
            when(mockedOps.isPlayerBusted(botPlayer)).thenReturn(true);

            assertEquals(botPlayer.nextAction(mockedHand), Action.Busted);
        }
    }

    @Test
    public void returns_the_correct_hand() {
        try (Resettable r1 = withMockHand()) {
            BotPlayer botPlayer = Dependencies.botPlayer.make();
            Card card1 = new Card(Suite.Clubs, Rank.Eight);
            Card card2 = new Card(Suite.Diamonds, Rank.Jack);
            HashSet<Card> expectedCards = new HashSet<>();
            expectedCards.add(card1);
            expectedCards.add(card2);
            when(mockedHand.getCards()).thenReturn(expectedCards);

            assertEquals(expectedCards, botPlayer.getHand().getCards());

        }
    }
}
