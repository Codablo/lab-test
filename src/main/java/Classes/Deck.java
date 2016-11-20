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
    List<Card> cards = new ArrayList<Card>();

    public Deck() {
        cardsRemaining = 0;
        /* add all cards in suite/rank order */
        for (Rank rnk : Rank.values()) {
            for (Suite suite : Suite.values()) {
                Card card = new Card(suite, rnk);
                cards.add(card);
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
            Card saveCard = cards.get(randomInt1);
            cards.set(randomInt1, cards.get(randomInt2));
            cards.set(randomInt2, saveCard);
        }
    }

    @Override
    public Card dealCard() throws OutOfCardsError {
        if (cardsRemaining == 0) throw new OutOfCardsError();
        cardsRemaining = cardsRemaining - 1;
        Card theCard = cards.get(0);
        cards.remove(0);
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
        for (int i = 0; i < cards.size(); i++) {
            deckString = deckString + cards.get(i).toString();
        }
        return deckString;
    }
}
