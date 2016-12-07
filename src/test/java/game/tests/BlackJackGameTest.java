package game.tests;

import Classes.*;
import enums.Action;
import enums.Outcome;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 12/4/16.
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
        Dependencies.blackJackGame.close();
    }


    @Test
    public void deck_is_shuffled_and_each_player_is_dealt_to_in_order() throws OutOfCardsError {
        when(mockedGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();

        mockedGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        InOrder inOrder = inOrder(mockedDeck, mockedHumanPlayer, mockedBotPlayer, mockedHumanPlayer, mockedBotPlayer);
        inOrder.verify(mockedDeck).shuffle(any());
        inOrder.verify(mockedHumanPlayer).addCard(any());
        inOrder.verify(mockedBotPlayer).addCard(any());
        inOrder.verify(mockedHumanPlayer).addCard(any());
        inOrder.verify(mockedBotPlayer).addCard(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void each_player_starts_with_two_cards() throws OutOfCardsError {
        when(mockedGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();

        mockedGame.initGame(mockedHumanPlayer, mockedBotPlayer, mockedDeck);

        verify(mockedBotPlayer, times(2)).addCard(any());
        verify(mockedHumanPlayer, times((2))).addCard(any());
    }

    @Test
    public void human_is_dealt_to_until_stay() throws OutOfCardsError {
        when(mockedGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);

        for (int i = 0; i < 3; i++) {
            mockedGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedHumanPlayer, times((2))).addCard(any());
    }

    @Test
    public void human_is_dealt_to_until_bust() throws OutOfCardsError {
        when(mockedGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Busted);

        for (int i = 0; i < 4; i++) {
            mockedGame.dealToHuman(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedHumanPlayer, times((3))).addCard(any());
    }

    @Test
    public void outcome_is_bot_when_human_is_busted() {
        when(mockedGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Busted, Action.Stay)).thenCallRealMethod();
        Outcome outcome;

        outcome = mockedGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Busted, Action.Stay);

        assertEquals(outcome, Outcome.Bot);
    }

    @Test
    public void outcome_is_human_when_bot_is_busted_and_human_is_not() {
        when(mockedGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Stay, Action.Busted)).thenCallRealMethod();
        Outcome outcome;

        outcome = mockedGame.getOutcome(mockedHumanPlayer, mockedBotPlayer, Action.Stay, Action.Busted);

        assertEquals(outcome, Outcome.Human);
    }

    @Test
    public void outcome_is_displayed() {
        when(mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human)).thenCallRealMethod();
        String humanWinner = "The winner is Human!";

        mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human);

        verify(mockedConsole).writeToConsole(humanWinner);
    }

    @Test
    public void dealer_cards_are_displayed_when_gane_is_over_with_none_hidden() {
        when(mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human)).thenCallRealMethod();

        mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human);

        verify(mockedBotPlayer).getVisibleHand(false);
    }

    @Test
    public void human_cards_are_displayed_when_game_is_over() {
        when(mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human)).thenCallRealMethod();

        mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.Human);

        verify(mockedHumanPlayer).getVisibleHand(false);
    }

    @Test
    public void each_players_score_is_displayed_when_the_game_is_over() {
        when(mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.None)).thenCallRealMethod();
        String gameWinner = "The winner is None!";
        String humanScore = "\nYour Hand: (0) ";
        String dealerScore = "\nDealer Hand: (0) ";
        when(mockedScore.getScore(any())).thenReturn(0);
        when(mockedHumanPlayer.getVisibleHand(false)).thenReturn("");
        when(mockedBotPlayer.getVisibleHand(anyBoolean())).thenReturn("");

        mockedGame.displayGameResults(mockedHumanPlayer, mockedBotPlayer, Outcome.None);

        verify(mockedConsole).writeToConsole(gameWinner);
        verify(mockedConsole).writeToConsole(humanScore);
        verify(mockedConsole).writeToConsole(dealerScore);
    }

    @Test
    public void bot_is_dealt_to_until_stay() throws OutOfCardsError {
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Stay);
        when(mockedGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();

        for (int i = 0; i < 3; i++) {
            mockedGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedBotPlayer, times((2))).addCard(any());
    }

    @Test
    public void bot_is_dealt_to_until_bust() throws OutOfCardsError {
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Hit).thenReturn(Action.Busted);
        when(mockedGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck)).thenCallRealMethod();

        for (int i = 0; i < 4; i++) {
            mockedGame.dealToBot(mockedHumanPlayer, mockedBotPlayer, mockedDeck);
        }

        verify(mockedBotPlayer, times((3))).addCard(any());
    }

    @Test
    public void is_played_in_the_correct_order() throws OutOfCardsError {
        when(mockedHumanPlayer.nextAction(any())).thenReturn(Action.Stay);
        when(mockedBotPlayer.nextAction(any())).thenReturn(Action.Stay);
        when(mockedHumanPlayer.getHand()).thenReturn(mockedHumanHand);
        when(mockedBotPlayer.getHand()).thenReturn(mockedBotHand);
        when(mockedGame.play()).thenCallRealMethod();

        mockedGame.play();

        InOrder inOrder = inOrder(mockedGame);
        inOrder.verify(mockedGame).initGame(any(), any(), any());
        inOrder.verify(mockedGame).dealToHuman(any(), any(), any());
        inOrder.verify(mockedGame).dealToBot(any(), any(), any());
        inOrder.verify(mockedGame).getOutcome(any(), any(), any(), any());
        inOrder.verify(mockedGame).displayGameResults(any(), any(), any());
        inOrder.verifyNoMoreInteractions();
    }

}
