package Database;

import Model.Event;
import Model.GameInformation.Game;
import Model.Match;
import Model.Moderator;
import Model.User;

import java.util.ArrayList;

public interface FileManger {
  void saveEventToFile(Event event);
  void saveParticipantToFile(String eventTitle, String username);
  void saveMatchToFile(String eventTitle, Match match, String score);
  void saveMatchToFile(String eventTitle, Match match, int position);
  void savePlayerMatchToFile(Match match);
  void updateEvent(Event event);
  void updateParticipant(String eventTitle,User user);
  ArrayList<Event> getEventsFromFile();
  void saveUserToFile(User user);
  ArrayList<User> getUsersFromFile();
  ArrayList<Match> getPlayerMatchesFromFile();
  void removeParticipant (String eventTitle, User user);
  void updateUserToModerator(User user);
  void updateModeratorToUser(User user);
  ArrayList<Game> loadAllMovesCharacterGames();
}
