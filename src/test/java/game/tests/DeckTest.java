package game.tests;

import Classes.Card;
import Classes.Deck;
import Classes.Dependencies;
import Classes.OutOfCardsError;
import enums.Rank;
import enums.Suite;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public class DeckTest {
    final int cardsInDeck = 52;
    final int seedForTest = 100;

    @Test
    public void has_fifty_two_cards() {
        Deck deck = new Deck();

        assertEquals(cardsInDeck, deck.length());
    }

    @Test
    public void has_one_card_of_each_suite_and_rank() throws OutOfCardsError

    {
        Boolean cardMissing = false;
        Deck deck = new Deck();
        List<Card> theDeck = new ArrayList<Card>();
        theDeck = getDeck(deck);

        for (Suite suite : Suite.values()) {
            for (Rank rnk : Rank.values()) {
                Card theCard = new Card(suite, rnk);
                Boolean cardFound = false;
                for (int i = 0; i < theDeck.size(); i++) {
                    Card theDeckCard = theDeck.get(i);
                    if (theDeckCard.equals(theCard)) cardFound = true;
                }
                if (!cardFound) cardMissing = true;
            }
        }

        assertFalse(cardMissing);
    }

    @Test
    public void has_one_less_card_after_deal() throws OutOfCardsError {
        Deck deck = new Deck();
        int length1 = deck.length();

        deck.dealCard();

        assert (length1 == deck.length() + 1);
    }

    @Test
    public void is_in_a_new_order_after_shuffle() throws OutOfCardsError {
        /* put a pristine deck into an array */
        Deck deck = new Deck();
        Random random = Dependencies.random.make(Integer.toString(seedForTest));

        deck.shuffle(new Random(seedForTest));

        assertEquals(deck.dealCard(), new Card(Suite.Clubs, Rank.Ace));
        assertEquals(deck.dealCard(), new Card(Suite.Diamonds, Rank.Two));
        assertEquals(deck.dealCard(), new Card(Suite.Hearts, Rank.Two));
        assertEquals(deck.dealCard(), new Card(Suite.Clubs, Rank.Three));
    }

    @Test
    public void throws_out_of_cards_error() {
        Deck theDeck = new Deck();
        Boolean hasError = false;

        for (int i = 0; i < cardsInDeck + 1; i++) {
            try {
                theDeck.dealCard();
            } catch (OutOfCardsError outOfCardsError) {
                hasError = true;
            }
        }
        assert (hasError);
    }

    @Test
    public void contains_same_cards_after_shuffle() throws OutOfCardsError {
        Deck myDeck = new Deck();

        List<Card> theDeck = new ArrayList<Card>();
        theDeck = getDeck(myDeck);
        Set initSet = new HashSet(theDeck);

        myDeck = new Deck();
        List<Card> theDeck2 = new ArrayList<Card>();
        theDeck2 = getDeck(myDeck);
        Set shuffledSet = new HashSet(theDeck2);

        assertEquals(initSet,shuffledSet);
    }

    //helper method to get a copy of the deck//
    public List<Card> getDeck(Deck theDeck) throws OutOfCardsError {
        List<Card> copyDeck = new ArrayList<Card>();

        for (int i = 0; i < cardsInDeck ; i++) {
            copyDeck.add(theDeck.dealCard());
        }
        return (copyDeck);
    }

}
