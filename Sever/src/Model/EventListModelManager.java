package Model;

import Database.FileManger;
import Database.SQLFileManager;
import Mediator.EventInformationPackage;
import Mediator.ServerMaster;
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
  private ServerMaster serverMaster;
  private PropertyChangeSupport property;
  private FileManger fileManager;

  public EventListModelManager(ServerMaster serverMaster) {
    this.serverMaster = serverMaster;
    eventList = new EventList();
    this.property = new PropertyChangeSupport(this);
    eventList.addListener("EventChange", this);
    eventList.addListener("CheckIn", this);
    try {
      fileManager = new SQLFileManager();
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    initGetFromFile();
  }

  private void initGetFromFile() {
    for (Event e : fileManager.getEventsFromFile()) {
      eventList.addEvent(e);
    }
  }

  @Override public ArrayList<Event> getAllEvents() {
    return eventList.getAllEvents();
  }

  @Override public Event getEvent(String tittle) {
    return eventList.getEvent(tittle);
  }

  @Override public void addEvent(String tittle, String game, int minBRP,
      int maxBRP, int maxParticipants, String date, int startingHour,
      Moderator organizer) {
    Event event = new Event(tittle, game, minBRP, maxBRP, maxParticipants, date,
        startingHour, organizer);
    fileManager.saveEventToFile(event);
    eventList.addEvent(event);
  }

  @Override public void addEvent(Event event) {
    fileManager.saveEventToFile(event);
    eventList.addEvent(event);
  }

  @Override public void setMatchScore(String eventTittle, Match match,
      String score) {
    eventList.setMatchScore(eventTittle, match, score);
  }

  @Override public void setMatchScore(Event event, Match match, String score) {
    fileManager.saveMatchToFile(event.getTittle(), match, score);
    eventList.setMatchScore(event, match, score);
  }

  @Override public void addMatch(String eventTittle, User playerOne,
      User playerTwo) {
    eventList.addMatch(eventTittle, playerOne, playerTwo);
  }

  @Override public void activateMatchTimer(String eventTitle, Match match)
  {
    eventList.activateMatchTimer(eventTitle, match);
  }

  @Override public void addMatch(Event event, User playerOne, User playerTwo) {
    eventList.addMatch(event, playerOne, playerTwo);
  }

  @Override public void removeParticipant(String eventTittle, User user) {
    eventList.removeParticipant(eventTittle, user);
  }

  @Override public void removeParticipant(Event event, User user) {
    eventList.removeParticipant(event, user);
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
      return "Somethin went wrong_;_true";
    }
  }

  @Override public void registerUser(User user) {
    fileManager.saveUserToFile(user);
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
//      fileManager.updateEvent(getEvent(evt.getOldValue().toString()));
      serverMaster.useredBroadcast(((Event)evt.getNewValue()).getParticipants(),
          "Check in is now available for an event '" + evt.getOldValue()
              + "'_;_false", "Notification");
    }
  }
}
