package mediator;

import Model.Match;
import Model.User;

import java.util.ArrayList;

public class MatchInformationPackage
{
  private String score;
  private ArrayList<User> players;
  public MatchInformationPackage(String score, User playerOne, User playerTwo)
  {
    this.score = score;
    this.players = new ArrayList<>(2);
    players.add(playerOne);
    players.add(playerTwo);
  }

  public String getScore()
  {
    return score;
  }

  public ArrayList<User> getPlayers()
  {
    return players;
  }
  public Match convertToMatch(){
    return new Match(players.get(0), players.get(1), score);
  }

}
