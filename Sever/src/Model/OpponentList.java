package Model;

import Mediator.ServerMaster;

import java.util.ArrayList;
import java.util.Map;

public class OpponentList {
  private ArrayList<Opponent> opponents;
  private ServerMaster serverMaster;
  public OpponentList(ServerMaster serverMaster){ //should we make the list be save to DB? or do we think that it should not be held in memory that long?
    this.serverMaster = serverMaster;
    opponents = new ArrayList<>();
  }
  public OpponentList(ServerMaster serverMaster, ArrayList<Opponent> opponentArrayList){ // for use if we want to store them to DB
    this.serverMaster = serverMaster;
    opponents = opponentArrayList;
  }
  public void addUserToList(User user, int minusOffset, int plusOffset){
    Opponent opponent = new Opponent(user, user.getBRP() - minusOffset, user.getBRP() + plusOffset);
    boolean found = false;
    for (Opponent o : opponents) {
      System.out.println("_________\n"+opponent+"\n"+o);
      if (opponent.compareToAnotherOpponent(o) && o.compareToAnotherOpponent(opponent)){
        serverMaster.useredAnswer(o.getOpponent(), "A match has been found with player: " + opponent.getOpponent().getDisplayName(), "Opponent");
        serverMaster.useredAnswer(opponent.getOpponent(), "A match has been found with player: " + o.getOpponent().getDisplayName(), "Opponent");
        opponents.remove(o);
        found = true;
        break;
      }
    }
    if (!found){
      serverMaster.useredAnswer(user, "You have been added to the waiting list.", "Notification");
      opponents.add(opponent);
    }
  }
}
