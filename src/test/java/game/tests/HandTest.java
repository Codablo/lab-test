package game.tests;

import Classes.Card;
import Classes.Dependencies;
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
        Hand newHand = Dependencies.hand.make();

        assertEquals(newHand.size(), 0);
    }

    @Test
    public void has_correct_size_after_adding_cards() {
        Hand newHand = Dependencies.hand.make();

        newHand.addCard(card1);
        assertEquals(newHand.size(), 1);
        newHand.addCard(card2);
        assertEquals(newHand.size(), 2);
        newHand.addCard(card3);

        assertEquals(newHand.size(), 3);
    }

    @Test
    public void has_the_same_cards_that_were_added() {
        HashSet<Card> cards = new HashSet<Card>();

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        Hand newHand = Dependencies.hand.make();
        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);

        assertEquals(cards, newHand.getCards());
    }

    @Test
    public void obscures_first_card_when_hiding_bottom() {
        Hand newHand = Dependencies.hand.make();
        String expectedHand = "xxx," + card2.toString() + "," + card3.toString();

        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);
        String visibleHand = newHand.visibleHand(true);

        assertEquals(visibleHand, expectedHand);
    }

    @Test
    public void does_not_obscure_first_card_when_not_hidingbottom() {
        Hand newHand = Dependencies.hand.make();
        String expectedHand = card1.toString() + "," + card2.toString() + "," + card3.toString();

        newHand.addCard(card1);
        newHand.addCard(card2);
        newHand.addCard(card3);
        String visibleHand = newHand.visibleHand(false);

        assertEquals(visibleHand, expectedHand);
    }
}
