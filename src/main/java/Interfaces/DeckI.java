package Interfaces;

import Classes.Card;
import Classes.OutOfCardsError;

import java.util.Random;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public interface DeckI {

    void shuffle(Random random);

    Card dealCard() throws OutOfCardsError;
}
