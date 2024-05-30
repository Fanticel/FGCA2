package Model;

import Mediator.ServerMaster;
import Model.GameInformation.Character;
import Model.GameInformation.Game;
import utility.NamedPropertyChangeSubject;

import java.time.LocalDate;
import java.util.ArrayList;

public interface EventListModel extends NamedPropertyChangeSubject {
  ArrayList<Event> getAllEvents();
  Event getEvent(String tittle);
  void addEvent(Event event);
  ArrayList<Object> addParticipant(String eventTittle, User user);
  String checkIn(String eventTitle, User user);
  void removeParticipant(String eventTittle, User user);
  void addPlayerMatch(User playerOne, User playerTwo, String score);
  void registerUser(User user);
  void addOpponent(User user, int minusOffset, int plusOffset, String game);
  void removeOpponent(User user);
  String voteOnOutcome(User user, String title, String usernameOne,
      String usernameTwo, int playerOneScore, int playerTwoScore);
  Match getMatchByParticipants(String title, String usernameOne,
      String usernameTwo);
  void acceptOpponent(User user, User enemyUser);
  void declineOpponent(User user, User enemyUser);
  void removeFromChat(String name, User user) throws Exception;
  void addToChat(String name, User user) throws Exception;
  void newChat(String chatName, ServerMaster serverMaster) throws Exception;
  void writeToChat(String name, String message, User User) throws Exception;
  String getChatLogByName(String name);
  String makeUserIntoModerator(String username);
  String makeModeratorIntoUser(String username);
  ArrayList<Character> getAllCharMovesFromGame(String gameName);
  ArrayList<String> getAllGameNames();
  Game getGameByName(String gameName);
}
