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

    List<Card> hand = new ArrayList<Card>();

    @Override
    public String visibleHand(boolean hideBottom) {
        String visibleHandString = "";

        for (int i = 0; i < hand.size(); i++) {
            Card theDeckCard = hand.get(i);
            if (!hideBottom && i == 0) {
                visibleHandString = visibleHandString + theDeckCard.toString();
            }
            if (hideBottom && i == 0) {
                visibleHandString = visibleHandString + "xxx";
            }
            if (i > 0) {
                visibleHandString = visibleHandString + "," + theDeckCard.toString();
            }
        }
        return visibleHandString;
    }

    @Override
    public void addCard(Card card) {
        hand.add(card);
    }

    @Override
    public int size() {
        return hand.size();
    }

    // TODO: desirable behavior: modifying the returned set is isolated from hand's storage
    @Override
    public Set<Card> getCards() {
        return new HashSet<Card>(hand);
    }
}
