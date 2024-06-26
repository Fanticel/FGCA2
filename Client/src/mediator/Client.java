package mediator;

import Model.*;
import Model.GameInformation.Game;
import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements EventListModel {
  private Socket socket;
  private Gson gson;
  private BufferedReader in;
  private PrintWriter out;
  private boolean waiting;
  private String answer;

  //don't forget packages
  private EventListModel model;
  private PropertyChangeSupport property;
  private Thread reader;

  public Client(Socket socket, EventListModel model) throws IOException {
    this.socket = socket;
    this.model = model;
    gson = new Gson();
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    reader = new Thread(new ClientReader(this, in));
    reader.start();
    out = new PrintWriter(socket.getOutputStream(), true);
    //not sure if it is right
    property = new PropertyChangeSupport(model);
  }

  public synchronized void receiveMessage(String message) {
    answer = message;
    notify();
  }

  @Override public User getUser() {
    out.println("getUser");
    return model.getUser();
  }

  @Override public synchronized ArrayList<Event> getAllEvents() {
    out.println("getEventList");
    try {
      wait();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    return gson.fromJson(answer, EventListPackage.class).getEvents();
  }

  @Override public synchronized Event getEvent(String title) {
    out.println("getEvent;" + title);
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return gson.fromJson(answer, EventInformationPackage.class)
        .convertToEvent();
  }

  @Override synchronized public String addEvent(String title, String game,
      int minBRP, int maxBRP, int maxParticipants, String date,
      int startingHour) {
    out.println("addEvent;" + gson.toJson(
        new Event(title, game, minBRP, maxBRP, maxParticipants, date,
            startingHour, null)));
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return answer;
  }

  @Override public void addParticipant(String eventTittle) {
    out.println("signUpToEvent;" + eventTittle);
  }

  @Override public void checkIn(String eventTittle) {
    out.println("confirmParticipation;" + eventTittle);
  }

  @Override public void removeParticipant(String eventTittle, User user) {
    out.println(
        "removeParticipant;" + eventTittle + ";" + user.getDisplayName());
  }

  @Override public void addPlayerMatch(String playerOneUsername,
      String playerTwoUsername, String score) {
    out.println(
        "addPlayerMatch;" + playerOneUsername + ";" + playerTwoUsername + ";"
            + score);
  }

  @Override public void setMatchScore(String eventTittle, Match match,
      String score) {
    out.println("setMatchScore;" + eventTittle + ";" + match + ";" + score);
  }

  @Override public ArrayList<Event> getEventsByGame(String game) {
    ArrayList<Event> allEvents = getAllEvents();
    ArrayList<Event> eventsByGame = new ArrayList<>();

    for (Event allEvent : allEvents) {
      if (allEvent.getGame().toUpperCase().contains(game.toUpperCase())) {
        eventsByGame.add(allEvent);
      }
    }
    return eventsByGame;
  }

  @Override public ArrayList<Event> getEventsBySkillLevel(String skillLevel) {
    ArrayList<Event> allEvents = getAllEvents();
    ArrayList<Event> eventsBySkillLevel = new ArrayList<>();

    for (Event event : allEvents) {
      int eventMinBRP = event.getMinBRP();
      int eventMaxBRP = event.getMaxBRP();

      if ("Beginner (0-999)".equals(skillLevel) && eventMaxBRP >= 0
          && eventMinBRP <= 999) {
        eventsBySkillLevel.add(event);
      }
      else if ("Semi-pro (1000-1999)".equals(skillLevel) && eventMaxBRP >= 1000
          && eventMinBRP <= 1999) {
        eventsBySkillLevel.add(event);
      }
      else if ("Advanced (2000-2999)".equals(skillLevel) && eventMaxBRP >= 2000
          && eventMinBRP <= 2999) {
        eventsBySkillLevel.add(event);
      }
      else if ("Expert (3000-3999)".equals(skillLevel) && eventMaxBRP >= 3000
          && eventMinBRP <= 3999) {
        eventsBySkillLevel.add(event);
      }
      else if ("Master (4000+)".equals(skillLevel) && eventMinBRP >= 4000) {
        eventsBySkillLevel.add(event);
      }
    }
    return eventsBySkillLevel;
  }

  @Override public ArrayList<Event> getEventsByStatus(String status) {
    ArrayList<Event> allEvents = getAllEvents();
    ArrayList<Event> eventsByStatus = new ArrayList<>();

    for (Event allEvent : allEvents) {
      if (status.equals(allEvent.getStatus())) {
        eventsByStatus.add(allEvent);
      }
    }
    return eventsByStatus;
  }

  @Override public synchronized String login(String username, String password) {
    out.println("log;" + username + ";" + password);
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return answer;
  }

  @Override public void showLocalNotification(String message, boolean error) {
  }

  @Override public synchronized String register(String username, String display,
      String password) {
    out.println("register;" + username + ";" + display + ";" + password);
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return answer;
  }

  @Override public boolean isModerator() {
    return false;
  }

  @Override public void voteOnOutcome(String title, String usernameOne,
      String usernameTwo, int playerOneScore, int playerTwoScore) {
    out.println("VOTE;" + title + ";" + usernameOne + ";" + usernameTwo + ";"
        + playerOneScore + ";" + playerTwoScore);
  }

  public synchronized void receivedChat(String message, String chatName) {
    property.firePropertyChange(
        new PropertyChangeEvent(this, chatName, null, message));
  }

  public synchronized void receivedNotification(String message, boolean error) {
    property.firePropertyChange(
        new PropertyChangeEvent(this, "Notification", error, message));
  }

  public synchronized void eventChange(String message) {
    property.firePropertyChange(
        new PropertyChangeEvent(this, "EventChange", null, message));
  }

  public synchronized void opponentFound(String opponentDisplayName,
      int opponentBRP, String opponentUserName) {
    Object[] opponentData = {opponentDisplayName, opponentBRP};
    property.firePropertyChange(
        new PropertyChangeEvent(this, "OpponentFound", opponentData,
            opponentUserName));
  }

  public synchronized void opponentRefused() {
    property.firePropertyChange(
        new PropertyChangeEvent(this, "OpponentRefused", null, ""));
  }

  public synchronized void opponentAccepted(String nameOne, String nameTwo) {
    property.firePropertyChange(
        new PropertyChangeEvent(this, "OpponentAccepted", nameOne, nameTwo));
  }

  public synchronized void matchSaved() {
    property.firePropertyChange(
        new PropertyChangeEvent(this, "MatchSaved", null, ""));
  }

  public void receiveOpponent() {

  }

  @Override public void addOpponent(String skillLevel, String gameTitle) {
    out.println("addOpponent;" + skillLevel + ";" + gameTitle);
  }

  @Override public void declineOpponent(String opponentUsername) {
    out.println("Decline;" + opponentUsername);
  }

  @Override public void acceptOpponent(String opponentUsername) {
    out.println("Accept;" + opponentUsername);
  }

  @Override public void removeOpponent() {
    out.println("RemoveOpponent");
  }

  @Override public void removeFromChat(String name) throws Exception {
    out.println("REMOVEFROMCHAT;" + name);
  }

  @Override public void addToChat(String name) throws Exception {
    out.println("ADDTOCHAT;" + name);
  }

  @Override public void writeToChat(String name, String message)
      throws Exception {
    out.println("WRITETOCHAT;" + name + ";" + message);
  }

  @Override public synchronized String getChatLogByName(String name) {
    out.println("GETCHATLOG;" + name);
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return answer;
  }

  @Override public synchronized CharacterInfoPackage getAllCharMovesFromGame(
      String gameName) {
    out.println("GETMOVESBYGAME;" + gameName);
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return gson.fromJson(answer, CharacterInfoPackage.class);
  }

  @Override public synchronized ArrayList<String> getAllGameNames() {
    out.println("GETGAMES");
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    ArrayList<String> ans = new ArrayList<>();
    ans = gson.fromJson(answer, ans.getClass());
    return ans;
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener) {
    property.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener) {
    property.removePropertyChangeListener(listener);
  }
}
