package game.tests;

import Classes.*;
import enums.Action;
import enums.Outcome;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 11/19/16.
 */
public class BlackJackGameTest {


    protected Score mockedScore = null;
    protected BotPlayer mockedBotPlayer = null;
    protected HumanPlayer mockedHumanPlayer = null;
    protected Deck mockedDeck = null;
    protected Prompt mockedPrompt = null;
    protected ConsoleIO mockedConsole = null;
    protected Hand mockedHumanHand = null;
    protected Hand mockedBotHand = null;
    protected Operations mockedOperations = null;

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
    }

    @Test
    public void deck_is_shuffled_and_each_player_is_dealt_to_in_order() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        blackJackGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        InOrder inOrder = inOrder(mockedDeck, mockedHumanPlayer, mockedBotPlayer, mockedHumanPlayer, mockedBotPlayer);
        inOrder.verify(mockedDeck).shuffle(any());
        inOrder.verify(mockedHumanPlayer).addCard(any());
        inOrder.verify(mockedBotPlayer).addCard(any());
        inOrder.verify(mockedHumanPlayer).addCard(any());
        inOrder.verify(mockedBotPlayer).addCard(any());
    }

    @Test
    public void each_player_starts_with_two_cards() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        blackJackGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        verify(mockedBotPlayer, times(2)).addCard(any());
        verify(mockedHumanPlayer, times((2))).addCard(any());
    }

    @Test
    public void human_is_dealt_to_until_stay() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);

        for (int i = 0; i < 3; i++) {
            blackJackGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedHumanPlayer, times((2))).addCard(any());
    }

    @Test
    public void human_is_dealt_to_until_bust() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Busted);

        for (int i = 0; i < 4; i++) {
            blackJackGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedHumanPlayer, times((3))).addCard(any());
    }

    @Test
    public void bot_is_dealt_to_until_stay() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);

        for (int i = 0; i < 3; i++) {
            blackJackGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedBotPlayer, times((2))).addCard(any());
    }

    @Test
    public void bot_is_dealt_to_until_bust() throws OutOfCardsError {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Busted);

        for (int i = 0; i < 4; i++) {
            blackJackGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedBotPlayer, times((3))).addCard(any());
    }

    @Test
    public void outcome_is_bot_when_human_is_busted() {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        Outcome outcome;
        outcome = blackJackGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Busted, Action.Stay);

        assertEquals(outcome, Outcome.Bot);
    }

    @Test
    public void outcome_is_human_when_bot_is_busted_and_human_is_not() {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        Outcome outcome;
        outcome = blackJackGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Stay, Action.Busted);

        assertEquals(outcome, Outcome.Human);
    }

    @Ignore
    public void is_played_in_the_correct_order() throws OutOfCardsError {
        BlackJackGame mockedBlackJackGame = mock(BlackJackGame.class);
        Dependencies.blackJackGame.override(() -> mockedBlackJackGame);
        //BlackJackGame mockedBlackJackGame = Dependencies.blackJackGame.make();
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Stay);
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
        when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
        //when(mockedHumanHand.visibleHand(any())).thenReturn("");
        //when(mockedBotHand.visibleHand(any())).thenReturn("");

        mockedBlackJackGame.play();

        InOrder inOrder = inOrder(mockedBlackJackGame);
        inOrder.verify(mockedBlackJackGame).initGame(any(), any(), any());
        inOrder.verify(mockedBlackJackGame).dealToHuman(any(), any(), any());
        inOrder.verify(mockedBlackJackGame).dealToBot(any(), any(), any());
        inOrder.verify(mockedBlackJackGame).getOutcome(any(), any(), any(), any());
        inOrder.verify(mockedBlackJackGame).displayGameResults(any(), any(), any());

        Dependencies.blackJackGame.close();
    }
}
