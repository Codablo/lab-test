package Interfaces;

import Classes.Hand;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public interface PlayerI {
    public Action nextAction(Hand otherHand);

    public Hand getHand();

}
