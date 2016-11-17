package Classes;

import Interfaces.OperationsI;
import Interfaces.PlayerI;
import enums.Action;
import enums.Outcome;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public class Operations implements OperationsI {

    public void dealCardToPlayer(PlayerI thePlayer,Deck theDeck) throws OutOfCardsError {
        thePlayer.getHand().addCard(theDeck.dealCard());
    }

    public boolean isPlayerBusted(PlayerI thePlayer){
        Score theScore = Dependencies.score.make();
        return theScore.getScore(thePlayer.getHand()) > 21;
    }

    public void completeDealToPlayer(PlayerI player,PlayerI otherPlayer,Deck theDeck)throws OutOfCardsError {
        while(player.nextAction(otherPlayer.getHand()) != Action.Stay) {
            dealCardToPlayer(player,theDeck);
        }
    }

    public Outcome getWinner(HumanPlayer theHuman,BotPlayer theBot){
        Score theScore = Dependencies.score.make();
        int botScore = theScore.getScore(theBot.getHand());
        int humanScore = theScore.getScore(theHuman.getHand());
        return Outcome.Push;
    }

    public boolean handHasBlackJack(Hand theHand){
        Score theScore = Dependencies.score.make();
        if (theScore.getScore(theHand) != 21){return false;}
        if (theHand.size() != 2) {return false;}
        return (true);
    }

}
