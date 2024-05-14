package Model;

import Mediator.ServerMaster;

import java.util.ArrayList;

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
  public synchronized void addUserToList(User user, int minusOffset, int plusOffset){
    Opponent opponent = new Opponent(user, user.getBRP() - minusOffset, user.getBRP() + plusOffset);
    boolean found = false;
    boolean stop = false;
    for (Opponent o : opponents) {
      if (o.getUser().equals(user)){
        serverMaster.useredAnswer(user, "You are already searching for an opponent!_;_true", "Notification");
        stop = true;
        break;
      }
      if (opponent.compareToAnotherOpponent(o) && o.compareToAnotherOpponent(opponent)){
        serverMaster.useredAnswer(o.getUser(), "A match has been found with player: " + opponent.getUser().getDisplayName(), "Opponent");
        serverMaster.useredAnswer(opponent.getUser(), "A match has been found with player: " + o.getUser().getDisplayName(), "Opponent");
        opponents.remove(o);
        found = true;
        break;
      }
    }
    if (!stop)
      if (!found) {
        serverMaster.useredAnswer(user,
            "You have been added to the waiting list.", "Notification");
        opponents.add(opponent);
      }
  }
  public synchronized void removeUserFromList(User user){
    for (Opponent o:opponents){
      if (o.getUser().equals(user)){
        opponents.remove(o);
        break;
      }
    }
  }
}
