package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Match
{
  private String score;
  private ArrayList<User> players;

  public Match(User playerOne, User playerTwo)
  {
    this.score = "0-0";
    this.players = new ArrayList<>(2);
    players.add(playerOne);
    players.add(playerTwo);
  }
  public Match(User playerOne, User playerTwo, String score){
    this.score = score;
    players = new ArrayList<>();
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

}
