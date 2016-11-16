package Classes;

import enums.Rank;
import enums.Suite;

/**
 * Created by mikehollibaugh on 11/5/16.
 */
public class Card {
    public final Suite suite;
    public final Rank rank;


    public Card(Suite suite, Rank rank) {
        this.suite = suite;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Classes.Card{" +
                "suite=" + suite +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suite != card.suite) return false;
        return rank == card.rank;

    }

    @Override
    public int hashCode() {
        int result = suite != null ? suite.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }
}
