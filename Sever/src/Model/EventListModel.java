package Model;

import utility.NamedPropertyChangeSubject;

import java.time.LocalDate;
import java.util.ArrayList;

public interface EventListModel extends NamedPropertyChangeSubject
{
  public ArrayList<Event> getAllEvents();
  public Event getEvent(String tittle);
  public void addEvent(String tittle, String game, int minBRP, int maxBRP,
      int maxParticipants, String date, int startingHour, Moderator organizer);
  public void addEvent(Event event);
  public ArrayList<Object> addParticipant(String eventTittle, User user);
  public String checkIn(String eventTitle, User user);
  public void removeParticipant(Event event, User user);
  public void removeParticipant(String eventTittle, User user);
  public void addMatch(Event event,User playerOne, User playerTwo);
  public void addMatch(String eventTittle, User playerOne, User playerTwo);
  public void activateMatchTimer(String eventTitle, Match match);
  public void registerUser(User user);
  public void addOpponent(User user, int minusOffset, int plusOffset, String game);
  public void removeOpponent(User user);
  String voteOnOutcome(User user, String title, String usernameOne, String usernameTwo, int playerOneScore, int playerTwoScore);
  Match getMatchByParticipants(String title, String usernameOne, String usernameTwo);
  void acceptOpponent(User user, User enemyUser);
  void declineOpponent(User user, User enemyUser);
}
