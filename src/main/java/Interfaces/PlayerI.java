package Interfaces;

import Classes.Deck;
import Classes.Hand;
import Classes.OutOfCardsError;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public interface PlayerI {
    public Action nextAction(Hand otherHand);

    public Hand getHand();

    public void addCard(Deck deck) throws OutOfCardsError;

}
