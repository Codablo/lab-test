package Applications;

import Classes.BlackJackGame;
import Classes.Dependencies;

/**
 * Created by mikehollibaugh on 11/19/16.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        BlackJackGame blackJackGame = Dependencies.blackJackGame.make();
        blackJackGame.play();
    }
}
