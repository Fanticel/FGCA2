package Model;

import Model.GameInformation.Game;
import mediator.CharacterInfoPackage;
import utility.NamedPropertyChangeSubject;

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
  void removeParticipant(String eventTittle, User user);
  void addPlayerMatch(String playerOneUsername, String playerTwoUsername, String score);
  void setMatchScore(String eventTittle, Match match, String score);
  ArrayList<Event> getEventsByGame(String game);
  ArrayList<Event> getEventsBySkillLevel(String skillLevel);
  ArrayList<Event> getEventsByStatus(String status);
  boolean isModerator();
  void showLocalNotification(String message, boolean error);
  void voteOnOutcome(String title, String usernameOne, String usernameTwo, int playerOneScore, int playerTwoScore);
  void addOpponent(String skillLevel, String gameTitle);
  void declineOpponent(String opponentUsername);
  void acceptOpponent(String opponentUsername);
  void removeOpponent();
  void removeFromChat(String name) throws Exception;
  void addToChat(String name) throws Exception;
  void writeToChat(String name, String message) throws Exception;
  String getChatLogByName(String name);
  CharacterInfoPackage getAllCharMovesFromGame(String gameName);
  ArrayList<String> getAllGameNames();
}
