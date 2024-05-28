package Model;

import Mediator.ServerMaster;
import Model.GameInformation.Character;
import Model.GameInformation.Game;
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
  public void removeParticipant(String eventTittle, User user);
  public void addPlayerMatch(User playerOne, User playerTwo, String score);
  public void registerUser(User user);
  public void addOpponent(User user, int minusOffset, int plusOffset, String game);
  public void removeOpponent(User user);
  String voteOnOutcome(User user, String title, String usernameOne, String usernameTwo, int playerOneScore, int playerTwoScore);
  Match getMatchByParticipants(String title, String usernameOne, String usernameTwo);
  void acceptOpponent(User user, User enemyUser);
  void declineOpponent(User user, User enemyUser);
  void removeFromChat(String name, User user) throws Exception;
  void addToChat(String name, User user) throws Exception;
  void newChat(String chatName, ServerMaster serverMaster) throws Exception;
  void writeToChat(String name, String message, User User) throws Exception;
  String getChatLogByName(String name);
  Chat getChatByName(String name);
  String makeUserIntoModerator(String username);
  String makeModeratorIntoUser(String username);
  ArrayList<Game> getAllGames();
  ArrayList<Character> getAllCharMovesFromGame(String gameName);
  ArrayList<String> getAllGameNames();
  Game getGameByName(String gameName);
}
