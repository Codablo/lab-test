package game.tests;

import Classes.Card;
import Classes.Hand;
import enums.Rank;
import enums.Suite;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by mikehollibaugh on 11/15/16.
 */
public class HandTest {
    public final Card card1 = new Card(Suite.Clubs, Rank.Eight);
    public final Card card2 = new Card(Suite.Hearts, Rank.Three);
    public final Card card3 = new Card(Suite.Diamonds, Rank.Seven);

    @Test
    public void has_size_zero_before_adding_cards() {
        Hand newHand = new Hand();

        assertEquals(newHand.size(), 0);
    }

    @Test
    public void has_correct_size_after_adding_cards() {
        Hand newHand = new Hand();

        newHand.addCard(card1);
        assertEquals(newHand.size(), 1);
        newHand.addCard(card2);
        assertEquals(newHand.size(), 2);
        newHand.addCard(card3);

        assertEquals(newHand.size(), 3);
    }

    @Test
    public void has_the_same_cards_that_where_added() {
        HashSet<Card> theCards = new HashSet<Card>();

        theCards.add(card1);
        theCards.add(card2);
        theCards.add(card3);
        Hand newHand = new Hand();
        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);

        assertEquals(theCards, newHand.getCards());
    }

    @Test
    public void obscures_first_card_when_hidebottom_is_true() {
        HashSet<Card> theCards = new HashSet<Card>();
        Hand newHand = new Hand();
        String expectedHand = card2.toString() + card3.toString();

        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);
        String thevisHand = newHand.visibleHand(true);
        assertEquals(thevisHand, expectedHand);
    }

    @Test
    public void does_not_obscure_first_card_when_hidebottom_is_false() {
        HashSet<Card> theCards = new HashSet<Card>();
        Hand newHand = new Hand();
        String expectedHand = card1.toString() + card2.toString() + card3.toString();

        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);
        String thevisHand = newHand.visibleHand(false);
        assertEquals(thevisHand, expectedHand);
    }
}
