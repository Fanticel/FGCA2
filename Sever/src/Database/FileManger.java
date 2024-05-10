package Database;

import Model.Event;
import Model.Match;
import Model.User;

import java.util.ArrayList;

public interface FileManger {
  void saveEventToFile(Event event);
  void saveParticipantToFile(String eventTitle, String username);
  void saveMatchToFile(String eventTitle, Match match, String score);
  void updateEvent(Event event);
  void updateParticipant(String eventTitle,User user);
  ArrayList<Event> getEventsFromFile();
  void saveUserToFile(User user);
  ArrayList<User> getUsersFromFile();
}
