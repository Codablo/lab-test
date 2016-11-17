package Classes;

import Interfaces.BlackJackGameI;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class BlackJackGame implements BlackJackGameI {
    Deck gameDeck = Dependencies.deck.make();
    HumanPlayer theHuman = Dependencies.humanPlayer.make();
    BotPlayer theBot = Dependencies.botPlayer.make();

    @Override
    public void play() {
        //shuffle deck
        gameDeck.shuffle(Dependencies.random.make(""));
        //deal to human
        //deal to bot
        //deal to human
        //deal to bot
        //deal to human until stay or bust
        //deal to bot until stay or bust
        //calculate winner
    }
}
