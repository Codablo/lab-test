package game.tests;

import enums.Action;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class ActionTest {
    @Test
    public void action_returns_string_value() {
        assertTrue(Action.Stay.text.equals("s"));
        assertTrue(Action.Hit.text.equals("h"));
    }
}
