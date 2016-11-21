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
    public final Card eightOfClubs = new Card(Suite.Clubs, Rank.Eight);
    public final Card threeOfHearts = new Card(Suite.Hearts, Rank.Three);
    public final Card sevenOfDiamonds = new Card(Suite.Diamonds, Rank.Seven);

    @Test
    public void has_size_zero_before_adding_cards() {
        Hand newHand = Dependencies.hand.make();

        assertEquals(newHand.size(), 0);
    }

    @Test
    public void has_correct_size_after_adding_cards() {
        Hand newHand = Dependencies.hand.make();

        newHand.addCard(eightOfClubs);
        assertEquals(newHand.size(), 1);
        newHand.addCard(threeOfHearts);
        assertEquals(newHand.size(), 2);
        newHand.addCard(sevenOfDiamonds);

        assertEquals(newHand.size(), 3);
    }

    @Test
    public void has_the_same_cards_that_were_added() {
        HashSet<Card> cards = new HashSet<Card>();

        cards.add(eightOfClubs);
        cards.add(threeOfHearts);
        cards.add(sevenOfDiamonds);
        Hand newHand = Dependencies.hand.make();
        newHand.addCard(eightOfClubs);
        newHand.addCard(threeOfHearts);
        newHand.addCard(sevenOfDiamonds);

        assertEquals(cards, newHand.getCards());
    }

    @Test
    public void obscures_first_card_when_hiding_bottom() {
        Hand newHand = Dependencies.hand.make();
        String expectedHand = "xxx," + threeOfHearts.toString() + "," + sevenOfDiamonds.toString();

        newHand.addCard(eightOfClubs);
        newHand.addCard(threeOfHearts);
        newHand.addCard(sevenOfDiamonds);
        String visibleHand = newHand.visibleHand(true);

        assertEquals(visibleHand, expectedHand);
    }

    @Test
    public void does_not_obscure_first_card_when_not_hidingbottom() {
        Hand newHand = Dependencies.hand.make();
        String expectedHand = eightOfClubs.toString() + "," + threeOfHearts.toString() + "," + sevenOfDiamonds.toString();

        newHand.addCard(eightOfClubs);
        newHand.addCard(threeOfHearts);
        newHand.addCard(sevenOfDiamonds);
        String visibleHand = newHand.visibleHand(false);

        assertEquals(visibleHand, expectedHand);
    }
}
