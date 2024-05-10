package Model;

import utility.NamedPropertyChangeSubject;

import java.time.LocalDate;
import java.util.ArrayList;

public interface EventListModel extends NamedPropertyChangeSubject
{
  String login(String username, String password);
  String register(String username,String display, String password);
  User getUser();
  ArrayList<Event> getAllEvents();
  Event getEvent(String tittle);
  String addEvent(String tittle, String game, int minBRP, int maxBRP,
      int maxParticipants, String date, int startingHour);
  void addParticipant(String eventTittle);
  void checkIn(String eventTittle);
  void removeParticipant(Event event, User user);
  void removeParticipant(String eventTittle, User user);
  void addMatch(Event event, User playerOne, User playerTwo);
  void addMatch(String eventTittle, User playerOne, User playerTwo);
  void setMatchScore(Event event, Match match, String score);
  void setMatchScore(String eventTittle, Match match, String score);
  void startVoting(Event eventTitle, Match match);
  ArrayList<Event> getEventsByGame(String game);
  ArrayList<Event> getEventsBySkillLevel(String skillLevel);
  ArrayList<Event> getEventsByStatus(String status);
  boolean isModerator();
}
