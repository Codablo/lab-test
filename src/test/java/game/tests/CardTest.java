package game.tests;

import enums.Rank;
import enums.Suite;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public class CardTest {

    @Test
    public void numbered_ranks_return_value() {
        assertTrue(Rank.Two.code == 2);
        assertTrue(Rank.Three.code == 3);
        assertTrue(Rank.Four.code == 4);
        assertTrue(Rank.Five.code == 5);
        assertTrue(Rank.Six.code == 6);
        assertTrue(Rank.Seven.code == 7);
        assertTrue(Rank.Eight.code == 8);
        assertTrue(Rank.Nine.code == 9);
    }

    @Test
    public void face_ranks_return_value_10() {
        assertTrue(Rank.Jack.code == 10);
        assertTrue(Rank.Queen.code == 10);
        assertTrue(Rank.King.code == 10);
    }

    @Test
    public void ace_returns_value_11() {
        assertTrue(Rank.Ace.code == 11);
    }

    @Test
    public void four_suites_are_defined() {
        assertTrue(Suite.values().length == 4);
    }
}
