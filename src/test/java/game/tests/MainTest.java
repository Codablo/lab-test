package game.tests;

import Applications.Server;
import Classes.*;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 12/4/16.
 */
public class MainTest {

    protected BlackJackGame mockedGame = null;

    public Resettable withMockGame() {
        mockedGame = mock(BlackJackGame.class);
        return Dependencies.blackJackGame.override(() -> mockedGame);
    }

    @Before
    public void setup() {
        withMockGame();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.blackJackGame.close();
    }

    @Test
    public void game_is_played() throws OutOfCardsError, IOException, java.lang.Exception {
        Server server = new Server();
        String[] args = {""};

        server.main(args);

        verify(mockedGame, times((1))).play();

    }
}
