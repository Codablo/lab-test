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
        Deck deck = Dependencies.deck.make();

        assertEquals(cardsInDeck, deck.length());
    }

    @Test
    public void has_one_card_of_each_suite_and_rank() throws OutOfCardsError

    {
        Boolean cardMissing = false;
        Deck deck = Dependencies.deck.make();
        List<Card> compDeck = new ArrayList<Card>();
        compDeck = getDeck(deck);

        for (Suite suite : Suite.values()) {
            for (Rank rnk : Rank.values()) {
                Card theCard = new Card(suite, rnk);
                Boolean cardFound = false;
                for (int i = 0; i < compDeck.size(); i++) {
                    Card theDeckCard = compDeck.get(i);
                    if (theDeckCard.equals(theCard)) cardFound = true;
                }
                if (!cardFound) cardMissing = true;
            }
        }

        assertFalse(cardMissing);
    }

    @Test
    public void has_one_less_card_after_deal() throws OutOfCardsError {
        Deck deck = Dependencies.deck.make();
        int length1 = deck.length();

        deck.dealCard();

        assert (length1 == deck.length() + 1);
    }

    @Test
    public void is_in_a_new_order_after_shuffle() throws OutOfCardsError {
        /* put a pristine deck into an array */
        Deck deck = Dependencies.deck.make();
        Random random = Dependencies.random.make(Integer.toString(seedForTest));

        deck.shuffle(random);

        assertEquals(deck.dealCard(), new Card(Suite.Clubs, Rank.Ace));
        assertEquals(deck.dealCard(), new Card(Suite.Diamonds, Rank.Two));
        assertEquals(deck.dealCard(), new Card(Suite.Hearts, Rank.Two));
        assertEquals(deck.dealCard(), new Card(Suite.Clubs, Rank.Three));
    }

    @Test
    public void throws_out_of_cards_error() {
        Deck deck = Dependencies.deck.make();
        Boolean hasError = false;

        for (int i = 0; i < cardsInDeck + 1; i++) {
            try {
                deck.dealCard();
            } catch (OutOfCardsError outOfCardsError) {
                hasError = true;
            }
        }
        assert (hasError);
    }

    @Test
    public void contains_same_cards_after_shuffle() throws OutOfCardsError {
        Deck myDeck = Dependencies.deck.make();

        List<Card> deck = new ArrayList<Card>();
        deck = getDeck(myDeck);
        Set initSet = new HashSet(deck);

        myDeck = Dependencies.deck.make();
        List<Card> compDeck = new ArrayList<Card>();
        compDeck = getDeck(myDeck);
        Set shuffledSet = new HashSet(compDeck);

        assertEquals(initSet, shuffledSet);
    }

    //helper method to get a copy of the deck//
    public List<Card> getDeck(Deck deck) throws OutOfCardsError {
        List<Card> copyDeck = new ArrayList<Card>();

        for (int i = 0; i < cardsInDeck; i++) {
            copyDeck.add(deck.dealCard());
        }
        return (copyDeck);
    }

}
