package game.tests;

import Classes.Dependencies;
import Classes.Hand;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class HumanTest {

    protected Hand mockedHand = null;

    public Resettable withMockHand() {
        mockedHand = mock(Hand.class);
        return Dependencies.hand.override(() -> mockedHand);
    }

    @Before
    public void setup() {
        withMockHand();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.hand.close();
    }
}
