package game.tests;

import Classes.*;
import enums.Action;
import enums.Outcome;
import enums.Rank;
import enums.Suite;
import navis.injection.Resettable;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class OperationsTest {

    protected Hand mockedHand = null;
    protected BotPlayer mockedBotPlayer = null;
    protected HumanPlayer mockedHumanPlayer = null;
    protected Score mockedScore = null;
    protected Deck mockedDeck = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsole = null;
    protected Hand mockedHumanHand = null;
    protected Hand mockedBotHand = null;

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

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.hand.override(() -> mockedHand);
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

    Card aecOfClubs = new Card(Suite.Clubs, Rank.Ace);
    Card jackOfDiamonds = new Card(Suite.Diamonds, Rank.Jack);
    Card eightOfDiamonds = new Card(Suite.Diamonds, Rank.Eight);
    Card sevenOfHearts = new Card(Suite.Hearts, Rank.Seven);

    int blackJackScore = 21;
    int summOfallCards = 26;

    @Test
    public void blackjack_is_detected_for_ace_and_ten_value_card() {
        Operations blackJackOps = Dependencies.operations.make();
        HashSet<Card> cards = new HashSet<>();
        cards.add(aecOfClubs);
        cards.add(jackOfDiamonds);

        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore()) {
            when(mockedHand.getCards()).thenReturn(cards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore);
            when(mockedHand.size()).thenReturn(cards.size());

            assertTrue(blackJackOps.handHasBlackJack(mockedHand));
        }
    }

    @Test
    public void blackjack_is_not_detected_for_score_of_21_with_more_than_2_cards() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore()) {
            Operations blackJackOps = Dependencies.operations.make();
            HashSet<Card> cards = new HashSet<>();
            cards.add(aecOfClubs);
            cards.add(jackOfDiamonds);
            cards.add(eightOfDiamonds);
            when(mockedHand.getCards()).thenReturn(cards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore);
            when(mockedHand.size()).thenReturn(cards.size());

            assertFalse(blackJackOps.handHasBlackJack(mockedHand));
        }
    }

    @Test
    public void blackjack_is_not_detected_for_score_not_equal_to_21() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore()) {
            Operations blackJackOps = Dependencies.operations.make();
            HashSet<Card> theCards = new HashSet<>();
            theCards.add(aecOfClubs);
            theCards.add(jackOfDiamonds);
            theCards.add(eightOfDiamonds);
            when(mockedHand.getCards()).thenReturn(theCards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore - 1);
            when(mockedHand.size()).thenReturn(theCards.size());

            assertFalse(blackJackOps.handHasBlackJack(mockedHand));
        }
    }

    @Test
    public void player_is_busted_when_score_exceeds_21() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockHumanPlayer()) {
            Operations blackJackOps = Dependencies.operations.make();
            HashSet<Card> cards = new HashSet<>();
            cards.add(aecOfClubs);
            cards.add(jackOfDiamonds);
            cards.add(eightOfDiamonds);
            cards.add(sevenOfHearts);
            when(mockedHand.getCards()).thenReturn(cards);
            when(mockedScore.getScore(any())).thenReturn(summOfallCards);

            assertTrue(blackJackOps.isPlayerBusted(mockedHumanPlayer));
        }
    }

    @Test
    public void player_is_not_busted_when_score_equals_21() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockHumanPlayer()) {
            Operations blackJackOps = Dependencies.operations.make();
            HashSet<Card> cards = new HashSet<>();
            cards.add(aecOfClubs);
            cards.add(jackOfDiamonds);
            when(mockedHand.getCards()).thenReturn(cards);
            when(mockedScore.getScore(any())).thenReturn(blackJackScore);

            assertFalse(blackJackOps.isPlayerBusted(mockedHumanPlayer));
        }
    }

    @Test
    public void player_is_not_busted_when_score_under_21() {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockScore();
             Resettable r3 = withMockHumanPlayer()) {
            Operations blackJackOps = Dependencies.operations.make();
            HashSet<Card> cards = new HashSet<>();
            cards.add(eightOfDiamonds);
            cards.add(sevenOfHearts);
            when(mockedHand.getCards()).thenReturn(cards);
            when(mockedScore.getScore(any())).thenReturn(15);

            assertFalse(blackJackOps.isPlayerBusted(mockedHumanPlayer));
        }
    }

    @Test
    public void player_is_dealt_a_card() throws OutOfCardsError {
        try (Resettable r1 = withMockHand();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK()) {
            Operations blackJackOps = Dependencies.operations.make();
            when(mockedDeck.dealCard()).thenReturn(aecOfClubs).thenReturn(jackOfDiamonds);
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);

            blackJackOps.dealCardToPlayer(mockedHumanPlayer, mockedDeck);
            blackJackOps.dealCardToPlayer(mockedHumanPlayer, mockedDeck);

            verify(mockedHand, times(2)).addCard(any());
        }
    }

    @Test
    public void deals_card_to_player_until_stay() throws OutOfCardsError, IOException {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockConsole();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK();
             Resettable r5 = withMockPrompt();
             Resettable r6 = withMockBotPlayer()) {
            when(mockedDeck.dealCard()).thenReturn(aecOfClubs);
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
            when(mockedHumanPlayer.nextAction(mockedHand)).
                    thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
            Operations blackJackOps = Dependencies.operations.make();

            blackJackOps.completeDealToPlayer(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

            verify(mockedHand, times(3)).addCard(any());
        }
    }

    @Test
    public void deals_card_to_player_until_busted() throws OutOfCardsError, IOException {
        try (Resettable r1 = withMockHand();
             Resettable r2 = withMockConsole();
             Resettable r3 = withMockHumanPlayer();
             Resettable r4 = withMockDecK();
             Resettable r5 = withMockPrompt();
             Resettable r6 = withMockBotPlayer();
             Resettable r7 = withMockScore()) {
            when(mockedDeck.dealCard()).thenReturn(aecOfClubs);
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedHand);
            when(mockedHumanPlayer.nextAction(mockedHand)).
                    thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Busted);
            Operations blackJackOps = Dependencies.operations.make();

            blackJackOps.completeDealToPlayer(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

            verify(mockedHand, times(3)).addCard(any());
        }
    }

    @Test
    public void player_wins_with_blackjack_and_other_player_has_21() {
        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockHumanPlayer();
             Resettable r3 = withMockBotPlayer();
             Resettable r4 = withMockBotHand();
             Resettable r5 = withMockScore()) {
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanHand.size()).thenReturn(2);
            when(mockedBotHand.size()).thenReturn(3);
            when(mockedScore.getScore(any())).thenReturn(21);
            Operations blackJackOps = Dependencies.operations.make();

            Outcome outcome = blackJackOps.getWinner(mockedHumanPlayer, mockedBotPlayer);

            assertTrue(outcome == Outcome.Human);
        }
    }

    @Test
    public void result_is_push_if_both_players_have_blackjack() {
        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockHumanPlayer();
             Resettable r3 = withMockBotPlayer();
             Resettable r4 = withMockBotHand();
             Resettable r5 = withMockScore()) {
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanHand.size()).thenReturn(2);
            when(mockedBotHand.size()).thenReturn(2);
            when(mockedScore.getScore(any())).thenReturn(21);
            Operations blackJackOps = Dependencies.operations.make();

            Outcome outcome = blackJackOps.getWinner(mockedHumanPlayer, mockedBotPlayer);

            assertTrue(outcome == Outcome.Push);
        }
    }

    @Test
    public void result_is_push_if_both_players_have_equal_score() {
        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockHumanPlayer();
             Resettable r3 = withMockBotPlayer();
             Resettable r4 = withMockBotHand();
             Resettable r5 = withMockScore()) {
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanHand.size()).thenReturn(4);
            when(mockedBotHand.size()).thenReturn(4);
            when(mockedScore.getScore(any())).thenReturn(17);
            Operations blackJackOps = Dependencies.operations.make();

            Outcome outcome = blackJackOps.getWinner(mockedHumanPlayer, mockedBotPlayer);

            assertTrue(outcome == Outcome.Push);
        }
    }

    @Test
    public void player_with_highest_score_wins() {
        try (Resettable r1 = withMockHumanHand();
             Resettable r2 = withMockHumanPlayer();
             Resettable r3 = withMockBotPlayer();
             Resettable r4 = withMockBotHand();
             Resettable r5 = withMockScore()) {
            when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
            when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
            when(mockedHumanHand.size()).thenReturn(4);
            when(mockedBotHand.size()).thenReturn(4);
            when(mockedScore.getScore(mockedBotHand)).thenReturn(17);
            when(mockedScore.getScore(mockedHumanHand)).thenReturn(15);
            Operations blackJackOps = Dependencies.operations.make();

            Outcome outcome = blackJackOps.getWinner(mockedHumanPlayer, mockedBotPlayer);

            assertTrue(outcome == Outcome.Bot);
        }
    }
}
