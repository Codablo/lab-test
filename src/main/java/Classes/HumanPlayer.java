package Classes;

import Interfaces.PlayerI;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class HumanPlayer implements PlayerI {
    Hand humanHand = Dependencies.hand.make();
    Prompt humanPrompt = Dependencies.prompt.make();

    @Override
    public Action nextAction(Hand otherHand) {
        String actions = "BOT Player Hand: " + otherHand.visibleHand(true) + ", press h to Hit or s to Stay:";
        String validActions = "[" + Action.Stay.text + Action.Hit.text + "]";
        String userResponse = humanPrompt.prompt(actions, validActions, Action.Stay.text);

        if (userResponse.equals(Action.Stay.text)) {
            return Action.Stay;
        } else {
            return Action.Hit;
        }
    }

    @Override
    public Hand getHand() {
        return humanHand;
    }
}
