package Interfaces;

import Classes.Card;

import java.util.Set;

/**
 * Created by mikehollibaugh on 11/15/16.
 * - `String visibleHand(boolean hideBottom)` - Returns a string that represents current hand. The option indicates
 * that the first card should be obscured.
 * - `Set<Card> getCards()` - Returns cards as a set
 * - `void addCard(Card)`
 * - `int size()` - returns the number of cards in the hand
 */
public interface HandI {

    public String visibleHand(boolean hideBottom);

    public void addCard(Card card);

    public int size();

    public Set<Card> getCards();
}
