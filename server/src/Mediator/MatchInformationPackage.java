package Mediator;

import Model.MachVoteTimer;
import Model.Match;
import Model.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
  public MatchInformationPackage(Match match){
    this(match.getScore(), match.getPlayers().get(0), match.getPlayers().get(1));
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
    return new Match(players.get(0), players.get(1), getScore());
  }

}
