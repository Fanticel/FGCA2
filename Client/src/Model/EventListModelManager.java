package Model;

import mediator.Client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class EventListModelManager implements EventListModel,
    PropertyChangeListener
{
  private PropertyChangeSupport property;
  private Client client;
  private User user;

  public EventListModelManager(Socket socket) throws IOException {
    client = new Client(socket, this);
    property = new PropertyChangeSupport(this);
    //placeholder before user login
    user = new User("User1", "Number one" , "12345");
    client.addListener("", this);
  }

  public User getUser()
  {
    return user;
  }

  @Override public ArrayList<Event> getAllEvents()
  {
    return client.getAllEvents();
  }

  @Override public Event getEvent(String tittle)
  {
    return client.getEvent(tittle);
  }

  @Override public void addEvent(String tittle, String game, int minBRP,
      int maxBRP, int maxParticipants, String date, int startingHour, Moderator organizer)
  {
    client.addEvent(tittle, game, minBRP, maxBRP, maxParticipants, date, startingHour,
        organizer);

  }

  @Override public void setMatchScore(String eventTittle, Match match,
      String score)
  {
    client.setMatchScore(eventTittle, match, score);
  }

  @Override public void startVoting(Event eventTitle, Match match)
  {
    client.startVoting(eventTitle, match);
  }

  @Override public ArrayList<Event> getEventsByGame(String game)
  {
    return client.getEventsByGame(game);
  }

  @Override public ArrayList<Event> getEventsBySkillLevel(String skillLevel)
  {
    return client.getEventsBySkillLevel(skillLevel);
  }

  @Override public ArrayList<Event> getEventsByStatus(String status)
  {
    return client.getEventsByStatus(status);
  }

  @Override public void setMatchScore(Event event, Match match, String score)
  {
    client.setMatchScore(event, match, score);
  }

  @Override public void addMatch(String eventTittle, User playerOne,
      User playerTwo)
  {
    client.addMatch(eventTittle, playerOne, playerTwo);
  }

  @Override public void addMatch(Event event, User playerOne, User playerTwo)
  {
    client.addMatch(event, playerOne, playerTwo);
  }

  @Override public void removeParticipant(String eventTittle, User user)
  {
    client.removeParticipant(eventTittle, user);
  }

  @Override public void removeParticipant(Event event, User user)
  {
    client.removeParticipant(event, user);
  }

  @Override public void addParticipant(String eventTittle)
  {
    client.addParticipant(eventTittle);
  }

  @Override public void checkIn(String eventTittle, User user)
  {
    client.checkIn(eventTittle, user);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    property.firePropertyChange(evt);
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(listener);
  }
}
