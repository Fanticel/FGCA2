package Model;

import Database.FileManger;
import Database.SQLFileManager;
import Mediator.EventInformationPackage;
import Mediator.ServerMaster;
import Model.GameInformation.Character;
import Model.GameInformation.Game;
import Model.GameInformation.GameList;
import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventListModelManager
    implements EventListModel, NamedPropertyChangeSubject,
    PropertyChangeListener {
  private EventList eventList;
  private ChatList chatList;
  private GameList gameList;
  private ServerMaster serverMaster;
  private PropertyChangeSupport property;
  private FileManger fileManager;
  private OpponentList opponentList;
  private MatchList matchList;

  public EventListModelManager(ServerMaster serverMaster) {
    this.serverMaster = serverMaster;
    eventList = new EventList();
    chatList = new ChatList();
    this.property = new PropertyChangeSupport(this);
    eventList.addListener("EventChange", this);
    eventList.addListener("CheckIn", this);
    eventList.addListener("outOfTime", this);
    try {
      fileManager = new SQLFileManager();
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    opponentList = new OpponentList(serverMaster);
    matchList = new MatchList();
    gameList = new GameList(fileManager.loadAllMovesCharacterGames());
    initGetFromFile();
  }

  private void initGetFromFile() {
    for (Event e : fileManager.getEventsFromFile()) {
      eventList.addEvent(e);
    }
    for (Match match : fileManager.getPlayerMatchesFromFile()) {
      matchList.addPlayerMatch(match);
    }
  }

  @Override public ArrayList<Event> getAllEvents() {
    return eventList.getAllEvents();
  }

  @Override public Event getEvent(String tittle) {
    return eventList.getEvent(tittle);
  }

  @Override public void addEvent(Event event) {
    eventList.addEvent(event);
    fileManager.saveEventToFile(event);
  }


  @Override public void addPlayerMatch(User playerOne, User playerTwo, String score) {
    Match match = new Match(playerOne, playerTwo, score);
    matchList.addPlayerMatch(match);
    fileManager.savePlayerMatchToFile(match);
  }

  @Override public void removeParticipant(String eventTittle, User user) {
    eventList.removeParticipant(eventTittle, user);
    fileManager.removeParticipant(eventTittle, user);
  }

  @Override public ArrayList<Object> addParticipant(String eventTittle,
      User user) {
    ArrayList<Object> response = eventList.addParticipant(eventTittle, user);
    if (!(boolean)response.get(1)){
      fileManager.saveParticipantToFile(eventTittle, user.getUsername());
    }
//    serverMaster.broadcast("Test", "ParticipantChange");
    return response;
  }

  @Override public String checkIn(String eventTitle, User user) {
    try
    {
      String answer = eventList.checkIn(eventTitle, user);
      fileManager.updateParticipant(eventTitle, user);
      return answer;
    }catch (Exception e){
      return "Something went wrong_;_true";
    }
  }

  @Override public void registerUser(User user) {
    fileManager.saveUserToFile(user);
  }

  @Override public void addOpponent(User user, int minusOffset,
      int plusOffset, String game) {
    opponentList.addUserToList(user, minusOffset, plusOffset, game);
  }

  @Override public void removeOpponent(User user) {
    opponentList.removeUserFromList(user);
  }

  @Override public void acceptOpponent(User user, User enemyUser) {
    opponentList.accept(user, enemyUser);
  }

  @Override public void declineOpponent(User user, User enemyUser) {
    opponentList.decline(user, enemyUser);
  }

  @Override public String voteOnOutcome(User user, String title, String usernameOne,
      String usernameTwo, int playerOneScore, int playerTwoScore) {
    Match match = getMatchByParticipants(title, usernameOne, usernameTwo);
    String ans = match.voteOnOutcome(user, playerOneScore, playerTwoScore);
    match.addListener(null, this);
    if (ans.split(":")[0].equals("BN")){
      int localMatchSize = eventList.getEvent(title).getMatches().size() - 1;
      fileManager.saveMatchToFile(title, eventList.getEvent(title).getMatchByParticipants(usernameOne, usernameTwo), eventList.getEvent(title).getMatches().indexOf(getMatchByParticipants(title, usernameOne, usernameTwo))+1);
      getEvent(title).updateNextMatch(match);
      if (eventList.getEvent(title).getMatches().get(localMatchSize).equals(getMatchByParticipants(title, usernameOne, usernameTwo))){
        String score = eventList.getEvent(title).getMatches().get(localMatchSize).getScore();
        if (Integer.parseInt(score.split("-")[0]) > Integer.parseInt(score.split("-")[1])){
          serverMaster.useredBroadcast(eventList.getEvent(title).getParticipants(), "The event " + title + " was won by " + eventList.getEvent(title).getMatches().get(getAllEvents().size()).getPlayers().get(0).getDisplayName()+"_;_false", "Notification");
        }
        else {
          serverMaster.useredBroadcast(eventList.getEvent(title).getParticipants(), "The event " + title + " was won by " + eventList.getEvent(title).getMatches().get(getAllEvents().size()).getPlayers().get(1).getDisplayName()+"_;_false", "Notification");
        }
      }
    }
    return ans;
  }

  @Override public Match getMatchByParticipants(String title, String usernameOne,
      String usernameTwo) {
    return eventList.getEvent(title).getMatchByParticipants(usernameOne, usernameTwo);
  }

  @Override public void removeFromChat(String name, User user)
      throws Exception {
    chatList.removeFromChat(name, user);
  }

  @Override public void addToChat(String name, User user) throws Exception {
    chatList.addToChat(name, user);
  }

  @Override public void newChat(String chatName, ServerMaster serverMaster)
      throws Exception {
    chatList.newChat(chatName, serverMaster);
  }

  @Override public void writeToChat(String name, String message, User user)
      throws Exception {
    chatList.writeToChat(name, message, user);
  }

  @Override public String getChatLogByName(String name) {
    if (chatList.getChatLogByName(name) == null){
      try {
        newChat(name, serverMaster);
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return chatList.getChatLogByName(name);
  }

  @Override public String makeUserIntoModerator(String username) {
    try {
      User tempUser = UserListSingleton.getInstance().getUserList().getUserByUsername(username);
      if (tempUser == null){
        return username + " doesn't exist";
      }
      if (tempUser.isModerator()){
        return username + " is already a moderator";
      }
      UserListSingleton.getInstance().getUserList().removeUser(tempUser.getUsername());
      UserListSingleton.getInstance().getUserList().addUser(new Moderator(tempUser.getUsername(), tempUser.getDisplayName(), tempUser.getPassword(),
          tempUser.getBRP()));
      fileManager.updateUserToModerator(tempUser);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return username + " successfully promoted into a moderator!";
  }

  @Override public String makeModeratorIntoUser(String username) {
    try {
      User tempUser = UserListSingleton.getInstance().getUserList().getUserByUsername(username);
      if (tempUser == null){
        return username + " doesn't exist";
      }
      if (!tempUser.isModerator()){
        return username + " is already not a moderator";
      }
      UserListSingleton.getInstance().getUserList().removeUser(tempUser.getUsername());
      UserListSingleton.getInstance().getUserList().addUser(new User(tempUser.getUsername(), tempUser.getDisplayName(), tempUser.getPassword(),
          tempUser.getBRP()));
      fileManager.updateModeratorToUser(tempUser);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return username + " successfully demoted into a user!";
  }
  @Override public ArrayList<Character> getAllCharMovesFromGame(
      String gameName) {
    return gameList.getAllCharMovesFromGame(gameName);
  }

  @Override public ArrayList<String> getAllGameNames() {
    return gameList.getAllGameNames();
  }

  @Override public Game getGameByName(String gameName) {
    return gameList.getGameByName(gameName);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener) {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener) {
    property.removePropertyChangeListener(propertyName, listener);
  }


  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("CheckIn")) {
      serverMaster.useredBroadcast(((Event)evt.getNewValue()).getParticipants(),
          "Check in is now available for an event '" + evt.getOldValue()
              + "'_;_false", "Notification");
    }
    if (evt.getPropertyName().equals("outOfTime")){
      System.out.println("received");
      serverMaster.useredBroadcast(((Match) evt.getOldValue()).getPlayers(), "The time for voting has finished. Contact the moderator dum dum_;_true", "Notification");
      try
      {
        serverMaster.useredAnswer(UserListSingleton.getInstance().getUserList().getUserByUsername(((Event) evt.getNewValue()).getOrganizer().getUsername()), "Time ran out for submitting a score in the event " + ((Event) evt.getNewValue()).getTittle() + " in the match between " + ((Match) evt.getOldValue()).getPlayers().get(0).getDisplayName() + " and " + ((Match) evt.getOldValue()).getPlayers().get(1).getDisplayName() + "_;_true", "Notification");
      }
      catch (SQLException e)
      {
        throw new RuntimeException(e);
      }
    }
    if (evt.getPropertyName().equals("EventChange")){
      fileManager.updateEvent((Event) evt.getNewValue());
    }
  }
}
