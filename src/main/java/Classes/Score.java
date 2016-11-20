package Classes;

import Interfaces.ScoreI;
import enums.Rank;

import java.util.HashSet;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class Score implements ScoreI {

    @Override
    public int getScore(Hand theHand) {
        HashSet<Card> theCards = new HashSet<>(theHand.getCards());
        int tempScore = 0;
        int acesFound = 0;
        for (Card card : theCards) {
            tempScore = tempScore + card.rank.code;
            if (card.rank == Rank.Ace) {
                acesFound++;
            }
        }
        while (acesFound > 0 && tempScore > 21) {
            tempScore = tempScore - 10;
            acesFound--;
        }
        return tempScore;
    }
}
