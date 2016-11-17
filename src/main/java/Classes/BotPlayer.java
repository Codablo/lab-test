package Classes;

import Interfaces.PlayerI;
import enums.Action;
import enums.Rank;

import java.util.HashSet;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class BotPlayer implements PlayerI {
    Hand botHand = Dependencies.hand.make();

    @Override
    public Action nextAction(Hand otherHand) {

        return null;
    }

    @Override
    public Hand getHand() {
        return botHand;
    }
}
