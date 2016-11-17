package Classes;

import Interfaces.HandI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mikehollibaugh on 11/15/16.
 * - `String visibleHand(boolean hideBottom)` - Returns a string that represents current hand. The option indicates
 * that the first card should be obscured.
 * - `Set<Card> getCards()` - Returns cards as a set
 * - `void addCard(Card)`
 * - `int size()` - returns the number of cards in the hand
 */
public class Hand implements HandI {

    List<Card> theHand = new ArrayList<Card>();

    @Override
    public String visibleHand(boolean hideBottom) {
        String visibleHandStr = "";

        for (int i = 0; i < theHand.size(); i++) {
            Card theDeckCard = theHand.get(i);
            if (!hideBottom && i == 0) {
                visibleHandStr = visibleHandStr + theDeckCard.toString();
            }
            if (i > 0) {
                visibleHandStr = visibleHandStr + theDeckCard.toString();
            }
        }
        return visibleHandStr;
    }

    @Override
    public void addCard(Card theCard) {
        theHand.add(theCard);
    }

    @Override
    public int size() {
        return theHand.size();
    }

    @Override
    public Set<Card> getCards() {
        return new HashSet<Card>(theHand);
    }
}
