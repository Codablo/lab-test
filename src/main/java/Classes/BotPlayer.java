package Classes;

import Interfaces.PlayerI;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class BotPlayer implements PlayerI {
    Hand botHand = Dependencies.hand.make();
    int stayScore = 17;

    @Override
    public Action nextAction(Hand otherHand) {
        Score botScore = Dependencies.score.make();
        if (botScore.getScore(botHand) < stayScore){
            return Action.Hit;
        }
        return Action.Stay;
    }

    @Override
    public Hand getHand() {
        return botHand;
    }
}
