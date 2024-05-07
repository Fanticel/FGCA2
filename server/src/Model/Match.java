package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Match implements PropertyChangeListener, NamedPropertyChangeSubject
{
  private String score;
  private ArrayList<User> players;
  private MachVoteTimer timer;
  private PropertyChangeSupport property;

  public Match(User playerOne, User playerTwo)
  {
    this.score = "";
    this.players = new ArrayList<>(2);
    players.add(playerOne);
    players.add(playerTwo);
    timer = new MachVoteTimer(60);
    this.timer.addListener("Time", this);
    this.timer.addListener("OutOfTIme", this);
    //Thread t = new Thread(timer);
    //t.start();
    property = new PropertyChangeSupport(this);
  }
  public Match(User playerOne, User playerTwo, String score)
  {
    this.players = new ArrayList<>(2);
    players.add(playerOne);
    players.add(playerTwo);
    timer = new MachVoteTimer(60);
    this.timer.addListener("Time", this);
    this.timer.addListener("OutOfTIme", this);
    Thread t = new Thread(timer);
    t.start();
    property = new PropertyChangeSupport(this);
    this.score = score;
    System.out.println(this);
  }

  public String getScore()
  {
    return score;
  }

  public void setMatchScore(String score)
  {
    this.score = score;
    property.firePropertyChange("Score",null, score);
  }

  public ArrayList<User> getPlayers()
  {
    return players;
  }

  public void setPlayer(int index, User player)
  {
    players.set(index, player);
  }
  public void activateMatchTimer(){
    if (timer.isActive()){
      timer.setActive(true);
      Thread t = new Thread(timer);
      t.setDaemon(true);
      t.start();
    }
  }
  public boolean equals(Object obj){
    if (obj == null || getClass() != obj.getClass()){
      return false;
    }
    Match other = (Match) obj;
    return players.get(0) == other.players.get(0) && players.get(1) == other.players.get(1);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    property.firePropertyChange(evt);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    if (propertyName == null)
    {
      property.addPropertyChangeListener(listener);
    }
    else
    {
      property.addPropertyChangeListener(propertyName, listener);
    }
    /*else if (propertyName.equals("Time"))
    {
      timer.addListener(propertyName, listener);
    }
    else
    {
      property.addPropertyChangeListener(propertyName, listener);
    }*/
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    if (propertyName == null)
    {
      property.removePropertyChangeListener(listener);
    }
    else
    {
      property.removePropertyChangeListener(propertyName, listener);
    }
    property.removePropertyChangeListener(propertyName, listener);
  }

  @Override public String toString() {
    return "\n"+ players.get(0) + " vs " + players.get(1) + ", score: " + score;
  }
}
