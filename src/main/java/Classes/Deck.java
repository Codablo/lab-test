package Classes;

import Interfaces.DeckI;
import enums.Rank;
import enums.Suite;

import java.util.*;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public class Deck implements DeckI {

    static final int cardsPerDeck = 52;
    int cardsRemaining;
    //LinkedHashMap<Card, Card> cards = new LinkedHashMap<>();
    List<Card> theCards = new ArrayList<Card>();

    public Deck() {
        cardsRemaining = 0;
        /* add all cards in suite/rank order */
        for (Rank rnk : Rank.values()) {
            for (Suite suite : Suite.values()) {
                Card theCard = new Card(suite, rnk);
                theCards.add(theCard);
                cardsRemaining = cardsRemaining + 1;
            }
        }
    }

    public int length() {
        return (cardsRemaining);
    }

    @Override
    public void shuffle(Random theRand) {

        for (int j = 0; j < 150; j++) {
            int randomInt1 = theRand.nextInt(cardsPerDeck - 1);
            int randomInt2 = theRand.nextInt(cardsPerDeck - 1);
            Card saveCard = theCards.get(randomInt1);
            theCards.set(randomInt1, theCards.get(randomInt2));
            theCards.set(randomInt2, saveCard);
        }
    }

    @Override
    public Card dealCard() throws OutOfCardsError {
        if (cardsRemaining == 0) throw new OutOfCardsError();
        cardsRemaining = cardsRemaining - 1;
        Card theCard = theCards.get(0);
        theCards.remove(0);
        return theCard;
    }

    @Override
    public int hashCode() {

        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String deckString = "";
        for (int i = 0; i < theCards.size(); i++) {
            deckString = deckString + theCards.get(i).toString();
        }
        return deckString;
    }
}
