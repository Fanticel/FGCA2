package Model;

import Mediator.ServerMaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OpponentList {
  private ArrayList<Opponent> opponents;
  private ArrayList<ArrayList<Object>> listOfPotentialMatches; //{[0]user1}, {[1]user2}, {[2]user1ReadyBool}, {[3]user2ReadyBool}
  private ServerMaster serverMaster;
  public OpponentList(ServerMaster serverMaster) {
    this.serverMaster = serverMaster;
    opponents = new ArrayList<>();
    listOfPotentialMatches = new ArrayList<>();
  }
  public synchronized void accept(User user, User enemyUser){
    for (ArrayList a : listOfPotentialMatches) {
      if (a.get(0).equals(user) && a.get(1).equals(enemyUser)){
        a.set(2, true);
        if ((boolean)a.get(3)){
          serverMaster.useredAnswer((User) a.get(0), ((User)a.get(1)).getDisplayName()+" has accepted the match!_;_false", "Notification");
          serverMaster.useredAnswer((User) a.get(1), ((User)a.get(0)).getDisplayName()+" has accepted the match!_;_false", "Notification");
          serverMaster.useredAnswer((User) a.get(0), ((User)a.get(0)).getDisplayName()+"_"+((User)a.get(1)).getDisplayName(), "OpponentFound");
          serverMaster.useredAnswer((User) a.get(1), ((User)a.get(0)).getDisplayName()+"_"+((User)a.get(1)).getDisplayName(), "OpponentFound");
          listOfPotentialMatches.remove(a);
          break;
        }
        else {
          serverMaster.useredAnswer(user, "Waiting for enemy response_;_false", "Notification");
        }
      }
      else if (a.get(1).equals(user) && a.get(0).equals(enemyUser)){
        a.set(3, true);
        if ((boolean)a.get(2)){
          serverMaster.useredAnswer((User) a.get(0), ((User)a.get(1)).getDisplayName()+" has accepted the match!_;_false", "Notification");
          serverMaster.useredAnswer((User) a.get(1), ((User)a.get(0)).getDisplayName()+" has accepted the match!_;_false", "Notification");
          serverMaster.useredAnswer((User) a.get(0), ((User)a.get(0)).getDisplayName()+"_"+((User)a.get(1)).getDisplayName(), "OpponentFound");
          serverMaster.useredAnswer((User) a.get(1), ((User)a.get(0)).getDisplayName()+"_"+((User)a.get(1)).getDisplayName(), "OpponentFound");
          listOfPotentialMatches.remove(a);
          break;
        }
        else {
          serverMaster.useredAnswer(user, "Waiting for enemy response_;_false", "Notification");
        }
      }
    }
  }
  public synchronized void decline(User user, User enemyUser){
    listOfPotentialMatches.removeIf(a -> (a.get(0).equals(user) && a.get(1).equals(enemyUser)) || (a.get(1).equals(user) && a.get(0).equals(enemyUser)));
    serverMaster.useredAnswer(user, "You have decided to cancel the match you pussy_;_true", "Notification");
    serverMaster.useredAnswer(enemyUser, user.getDisplayName()+" decided to cancel the match_;_true", "OpponentRefused");
//    for (ArrayList a:listOfPotentialMatches){
//      if ((a.get(0).equals(user) && a.get(1).equals(enemyUser)) || (a.get(1).equals(user) && a.get(0).equals(enemyUser))){
//        listOfPotentialMatches.remove(a);
//      }
//    }
  }
  public synchronized void addUserToList(User user, int minusOffset, int plusOffset, String game){
    Opponent opponent = new Opponent(user, user.getBRP() - minusOffset, user.getBRP() + plusOffset, game);
    boolean found = false;
    boolean stop = false;
    for (Opponent o : opponents) {
      if (o.getUser().equals(user) && o.getGame().equals(game)){
        serverMaster.useredAnswer(user, "You are already searching for an opponent!_;_true", "Notification");
        stop = true;
        break;
      }
      if (opponent.compareToAnotherOpponent(o) && o.compareToAnotherOpponent(opponent)){
        serverMaster.useredAnswer(o.getUser(), "A match has been found with player " + opponent.getUser().getDisplayName() + " for the game " + game + "_;_"+opponent.getUser().getDisplayName() + "_;_"+opponent.getUser().getBRP()+ "_;_"+opponent.getUser().getUsername(), "OpponentRequest");
        serverMaster.useredAnswer(opponent.getUser(), "A match has been found with player " + o.getUser().getDisplayName() + " for the game " + game+ "_;_"+o.getUser().getDisplayName() + "_;_"+o.getUser().getBRP()+ "_;_"+o.getUser().getUsername(), "OpponentRequest");
        opponents.removeIf(o2 -> o2.getUser().equals(o.getUser()));
        listOfPotentialMatches.add(new ArrayList<>(Arrays.asList(opponent.getUser(), o.getUser(), false, false)));
        found = true;
        break;
      }
    }
    if (!stop)
      if (!found) {
        opponents.add(opponent);
      }
    System.out.println(this);
  }
  public synchronized void removeUserFromList(User user){
    //        break;
    opponents.removeIf(o -> o.getUser().equals(user));
  }

  @Override public String toString() {
    String ans = "OpponentList[\n";
    for (Opponent o : opponents) {
      ans += "{"+o+"}\n";
    }
    ans +="]";
    return ans;
  }
}
