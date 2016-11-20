package Interfaces;

import Classes.OutOfCardsError;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public interface BlackJackGameI {
    public void play()  throws OutOfCardsError;
}
