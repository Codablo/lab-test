package Interfaces;

import Classes.Hand;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 * - Make a Player interface or abstract class
 * - `Action nextAction(Hand otherHand)` - Returns `Hit`, `Stay`, or `Busted` (based on known cards of the other player's hand)
 * - `Hand getHand();` - Returns the player's hand
 */
public interface PlayerI {
    public Action nextAction(Hand otherHand);

    public Hand getHand();

}
