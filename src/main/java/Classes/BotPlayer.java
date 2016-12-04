package Classes;

import Interfaces.PlayerI;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class BotPlayer implements PlayerI {
    private Hand botHand = Dependencies.bothand.make();
    private int stayScore = 17;

    @Override
    public Action nextAction(Hand otherHand) {
        Score botScore = Dependencies.score.make();
        Operations blackJackOps = Dependencies.operations.make();
        if (blackJackOps.isPlayerBusted(this)) {
            return Action.Busted;
        }

        if (botScore.getScore(botHand) < stayScore) {
            return Action.Hit;
        }
        return Action.Stay;
    }

    @Override
    public void addCard(Deck deck) throws OutOfCardsError {
        botHand.addCard(deck.dealCard());
    }

    @Override
    public Hand getHand() {
        return botHand;
    }

    @Override
    public String getVisibleHand(boolean hideBottom) {
        return botHand.visibleHand(hideBottom);
    }
}
