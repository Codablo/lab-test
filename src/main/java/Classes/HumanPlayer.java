package Classes;

import Interfaces.PlayerI;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class HumanPlayer implements PlayerI {
    Hand humanHand = Dependencies.hand.make();
    ConsoleIO consoleIO = Dependencies.console.make();
    @Override
    public Action nextAction(Hand otherHand) {
        return null;
    }

    @Override
    public Hand getHand() {
        return null;
    }
}
