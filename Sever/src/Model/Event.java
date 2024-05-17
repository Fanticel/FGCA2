package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Event implements PropertyChangeListener, NamedPropertyChangeSubject
{
  private String tittle;
  private String game;
  private int minBRP;
  private int maxBRP;
  private String status;
  private int maxParticipants;
  private String startDate;
  private int startingHour;
  private Moderator Organizer;
  private ArrayList<Match> matches;
  private ArrayList<User> participants;
  private ArrayList<User> confirmedParticipants;
  private PropertyChangeSupport property;
  private CheckInTimer checkInTimer;
  private BracketGeneration bracketGeneration;

  public Event(String tittle, String game, int minBRP, int maxBRP,
      String status, int maxParticipants, String date, int startingHour,
      Moderator organizer)
  {
    if (tittle.replaceAll("[0-9]", "").isEmpty())
    {
      throw new IllegalArgumentException("Title cannot have just numbers");
    }
    else
    {
      this.tittle = tittle;
    }
    this.game = game;
    if (minBRP >= maxBRP)
    {
      throw new IllegalStateException(
          "minBRP cannot be higher or equal than maxBRP");
    }
    else
    {
      this.minBRP = minBRP;
      this.maxBRP = maxBRP;
    }
    this.status = status;
    this.maxParticipants = maxParticipants;
    LocalDate currentDate = LocalDate.now();
    LocalDate givenDate = LocalDate.parse(date);
    //    if (givenDate.isBefore(currentDate) || givenDate.isEqual(currentDate)){
    //      throw new IllegalArgumentException("Date cannot be today or before");
    //    }else {
    //      this.startDate = date;
    //    }
    this.startDate = date;
    this.startingHour = startingHour;
    Organizer = organizer;
    matches = new ArrayList<>();
    participants = new ArrayList<>(maxParticipants);
    confirmedParticipants = new ArrayList<>(maxParticipants);
    property = new PropertyChangeSupport(this);
    checkInTimer = new CheckInTimer();
    checkInTimer.addListener("CheckIn", this);
    Thread checkInThread = new Thread(checkInTimer);
    checkInThread.setDaemon(true);
    checkInThread.start();
    bracketGeneration = new BracketGenerator();
  }

  public Event(String tittle, String game, int minBRP, int maxBRP,
      int maxParticipants, String date, int startingHour, Moderator organizer)
  {
    this(tittle, game, minBRP, maxBRP, "created", maxParticipants, date,
        startingHour, organizer);
  }

  public String getTittle()
  {
    return tittle;
  }

  public String getGame()
  {
    return game;
  }

  public int getMinBRP()
  {
    return minBRP;
  }

  public int getMaxBRP()
  {
    return maxBRP;
  }

  public String getStatus()
  {
    return status;
  }

  public int getMaxParticipants()
  {
    return maxParticipants;
  }

  public String getStartDate()
  {
    return startDate;
  }

  public Moderator getOrganizer()
  {
    return Organizer;
  }

  public Match getMatchByParticipants(String usernameOne, String usernameTwo)
  {
    for (Match m : matches)
    {
      if (m.hasBothPlayers(usernameOne, usernameTwo))
      {
        return m;
      }
    }
    return null;
  }

  public ArrayList<Match> getMatches()
  {
    return matches;
  }

  public ArrayList<User> getParticipants()
  {
    return participants;
  }

  public ArrayList<Object> addParticipant(User user)
  {
    ArrayList<Object> response = new ArrayList<>();
    //    System.out.println(participants + "\n\n" + user);
    if (participants.contains(user))
    {
      response.add("You are already registered for this event!");
      response.add(true);
      return response;
    }
    if (participants.size() >= maxParticipants)
    {
      response.add("Maximum number of participants reached!");
      response.add(true);
      return response;
    }
    if (user.getBRP() > maxBRP || user.getBRP() < minBRP)
    {
      response.add("You do not meet the BRP requirements!");
      response.add(true);
      return response;
    }
    else
    {
      try
      {
        participants.add(user);
        response.add("Successfully joined");
        response.add(false);
        return response;
      }
      catch (Exception e)
      {
        response.add("Something went wrong, try joining again later.");
        response.add(true);
        return response;
      }
    }
  }

  public void removeParticipant(User user)
  {
    participants.remove(user);
  }

  public void addMatch(User playerOne, User playerTwo)
  {
    matches.add(new Match(playerOne, playerTwo));
  }

  public void addMatch(User playerOne, User playerTwo, String score)
  {
    matches.add(new Match(playerOne, playerTwo, score));
  }

  public int getStartingHour()
  {
    return startingHour;
  }

  public String checkIn(User user)
  {
    if (!participants.contains(user))
    {
      return "You didn't joined this event_;_true";
    }
    else if (confirmedParticipants.contains(user))
    {
      return "You are already checked in_;_true";
    }
    else
    {
      confirmedParticipants.add(user);
      return "Check in successful_;_false";
    }
  }
  public void updateNextMatch(Match currentMatch){
    String[] strings = currentMatch.getScore().split("-");
    int matchIndex = matches.indexOf(currentMatch);
    if (matchIndex < maxParticipants - 2){
      Match nextMatch = matches.get(nextMatch(maxParticipants, matchIndex));
      if (Integer.valueOf(strings[0]) > Integer.valueOf(strings[1])){
        nextMatch.setPlayer(matchIndex % 2, currentMatch.getPlayers().get(0));
        nextMatch.addPlayerToHashMap(currentMatch.getPlayers().get(0));
      }
      else {
        nextMatch.setPlayer(matchIndex % 2, currentMatch.getPlayers().get(1));
        nextMatch.addPlayerToHashMap(currentMatch.getPlayers().get(1));
      }
    }
    else {
      status = "Finished";
    }
  }

  public void activateMatchTimer(Match wantedMatch)
  {
    for (Match match : matches)
    {
      if (match.equals(wantedMatch))
      {
        match.activateMatchTimer();
      }
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("CheckIn"))
    {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDateTime currentDate = LocalDateTime.now();
      if (status.equals("In progress")){
        matches = bracketGeneration.generateBracketDB(confirmedParticipants, matches, maxParticipants);
        checkInTimer.setActive(false);
      }
      else if (formatter.format(currentDate).equals(startDate))
      {
        if (currentDate.getHour() == startingHour - 1 && !status.equals("CheckIn"))
        {
          status = "CheckIn";
          property.firePropertyChange("CheckIn", tittle, this);
        }
        if (currentDate.getHour() == startingHour)
        {
          if (confirmedParticipants.size() <= 1)
          {
            status = "Canceled";
          }
          else if (!status.equals("In progress")){
            status = "In progress";
            checkInTimer.setActive(false);
            participants = confirmedParticipants;
            matches = bracketGeneration.generateBracket(confirmedParticipants, matches, maxParticipants);
            property.firePropertyChange("EventChange", null, this);
          }
        }
      }
    }
    else
    {
      property.firePropertyChange(evt);
    }
  }

  //Generates matches for the whole tournament. Idea is that when the tournament starts we already
  //generate all necessary matches for the event.
  // The matches of the first round are generated with participants while the others are empty.
  //As the matches finish you pass the info to the server and fill in the so far empty matches according to the results.
  //This way we can just have the bracket view request the matches and read the info in ordered way from them.
 /* private void generateMatches()
  {
    int participantNumber = confirmedParticipants.size();
    System.out.println(confirmedParticipants);
    //generates the first round of matches with participants
    for (int i = 0; i < participantNumber; i += 2)
    {
      if (i + 1 != participantNumber)
      {
        matches.add(new Match(confirmedParticipants.get(i),
            confirmedParticipants.get(i + 1)));
      }
    }
    //generates empty matches for the following rounds and the last match of first round if participants are odd number
    while (matches.size() < maxParticipants - 1)
    {
      matches.add(new Match(null, null));
    }
    //If the number of players is odd the last match in the first round only has one player who is automatically assigned to the next round
    if (confirmedParticipants.size() % 2 != 0)
    {
      // matches.add(new Match(confirmedParticipants.get(confirmedParticipants.size()-1), null));
      User oddPlayer = confirmedParticipants.get(participantNumber - 1);
      matches.get((int) Math.ceil(participantNumber / 2))
          .setPlayer(0, oddPlayer);
      //gets index for last match of next round
      int MatchIndex = matches.indexOf(matches.get(participantNumber / 2 - 1));
      matches.get(nextMatch(maxParticipants, MatchIndex))
          .setPlayer(0, oddPlayer);
    }
  }*/

  //gets index of next round match according to the current match
  public int nextMatch(int maxParticipants, int currentMatch)
  {
    System.out.println(
        (maxParticipants / 2) + (int) Math.floor((double) currentMatch / 2));
    return (maxParticipants / 2) + (int) Math.floor((double) currentMatch / 2);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(propertyName, listener);
  }

  @Override public String toString()
  {
    return "\nEvent{" + "tittle='" + tittle + '\'' + ", game='" + game + '\''
        + ", minBRP=" + minBRP + ", maxBRP=" + maxBRP + ", status='" + status
        + '\'' + ", maxParticipants=" + maxParticipants + ", startDate='"
        + startDate + '\'' + ", startingHour=" + startingHour + ", Organizer="
        + Organizer + ", matches=" + matches + ", participants=" + participants
        + '}';
  }
}
