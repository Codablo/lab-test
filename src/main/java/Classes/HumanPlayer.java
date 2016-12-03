package Classes;

import Interfaces.PlayerI;
import enums.Action;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public class HumanPlayer implements PlayerI {
    private Hand humanHand = Dependencies.humanhand.make();
    private Prompt humanPrompt = Dependencies.prompt.make();
    private Score score = Dependencies.score.make();

    @Override
    public Action nextAction(Hand otherHand) {
        Operations blackJackOps = Dependencies.operations.make();
        if (blackJackOps.isPlayerBusted(this)) {
            return Action.Busted;
        }
        String actions = "Your Hand: {" + score.getScore(humanHand) + "} " + humanHand.visibleHand(false) +
                "\nBOT Player Hand: " + otherHand.visibleHand(true) + ",\npress h to Hit or s to Stay:";
        String validActions = "[" + Action.Stay.text + Action.Hit.text + "]";
        String userResponse = humanPrompt.prompt(actions, validActions, Action.Stay.text);
        if (userResponse.equals(Action.Stay.text)) {
            return Action.Stay;
        } else {
            return Action.Hit;
        }
    }

    @Override
    public void addCard(Deck deck) throws OutOfCardsError {
        humanHand.addCard(deck.dealCard());
    }

    @Override
    public Hand getHand() {
        return humanHand;
    }
}
