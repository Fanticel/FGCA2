package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Match implements PropertyChangeListener, NamedPropertyChangeSubject
{
  private String score;
  private ArrayList<User> players;
  private MachVoteTimer timer;
  private PropertyChangeSupport property;
  private boolean hasVoted;
  Map<User, Boolean> hasVotedMap;
  private int playerOneScoreVote;
  private int playerTwoScoreVote;

  public Match(User playerOne, User playerTwo)
  {
    this(playerOne, playerTwo, " - ");
  }
  public Match(User playerOne, User playerTwo, String score)
  {
    this.players = new ArrayList<>(2);
    players.add(playerOne);
    players.add(playerTwo);
//    timer = new MachVoteTimer(60);
//    this.timer.addListener("Time", this);
//    this.timer.addListener("OutOfTIme", this);
//    Thread t = new Thread(timer);
//    t.start();
    property = new PropertyChangeSupport(this);
    this.score = score;
    hasVoted = false;
    hasVotedMap = new HashMap<>();
    hasVotedMap.put(players.get(0), false);
    hasVotedMap.put(players.get(1), false);
  }
  public synchronized String voteOnOutcome(User user,int playerOneScore, int playerTwoScore){
    if (!score.equals(" - ")){
      return "PN:The voting for this event is already over!_;_true";
    }
    if (hasVotedMap.get(user)){
      return "PN:You have already voted!_;_true";
    }
    if (!hasVoted){
      playerOneScoreVote = playerOneScore;
      playerTwoScoreVote = playerTwoScore;
      hasVoted = true;
      hasVotedMap.put(user, true);
      return "PN:Voted successfully!_;_false";
    }
    if (playerOneScore != playerOneScoreVote || playerTwoScore != playerTwoScoreVote){
      return "BMN:There was a discrepancy in voting!_;_true";
    }
    score = playerOneScoreVote + "-" + playerTwoScoreVote;
    return "BN:Match ended with score " + score + "_;_false";
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
  public boolean hasBothPlayers(String usernameOne, String usernameTwo){
    if (players.get(0) != null && players.get(1) != null){
      return (players.get(0).getUsername().equals(usernameOne) && players.get(1).getUsername().equals(usernameTwo) ||
          players.get(0).getUsername().equals(usernameTwo) && players.get(1).getUsername().equals(usernameOne));
    }
    return false;
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
  public int getPlayerOneScore(){
    return Integer.parseInt(score.split("-")[0]);
  }
  public int getPlayerTwoScore(){
    return Integer.parseInt(score.split("-")[1]);
  }

  @Override public String toString() {
    return "\n"+ players.get(0) + " vs " + players.get(1) + ", score: " + score;
  }
}
