package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class EventList implements PropertyChangeListener,
    NamedPropertyChangeSubject
{
  private ArrayList<Event> events;
  private PropertyChangeSupport property;

  public EventList()
  {
    events = new ArrayList<>();
    property = new PropertyChangeSupport(this);
  }

  public ArrayList<Event> getAllEvents(){
    return events;
  }
  public Event getEvent(String tittle){
    for (Event event:events)
    {
      if (tittle.equals(event.getTittle())){
        return event;
      }
    }
    return null;
  }
  public void addEvent(Event event){
    events.add(event);
    event.addListener("EventChange", this);
    event.addListener("CheckIn", this);
  }

  public ArrayList<Object> addParticipant(String eventTittle, User user){
    for (Event event:events)
    {
      if (eventTittle.equals(event.getTittle())){
        return event.addParticipant(user);
      }
    }
    ArrayList<Object> response = new ArrayList<>();
    response.add("Something went wrong, try joining again later.");
    response.add(true);
    return response;
  }
  public String checkIn(String eventTitle, User user)
  {
    for (Event event:events)
    {
      if (eventTitle.equals(event.getTittle())){
        return event.checkIn(user);
      }
    }
    return "Event not found_;_true";
  }
  public void activateMatchTimer(String eventTitle, Match match)
  {
    for (Event event: events){
      if (eventTitle.equals(event.getTittle())){
        event.activateMatchTimer(match);
      }
    }
  }
  public void removeParticipant(Event event, User user){
    event.removeParticipant(user);
  }
  public void removeParticipant(String eventTittle, User user){
    for (Event event:events)
    {
      if (eventTittle.equals(event.getTittle())){
        event.removeParticipant(user);
      }
    }
  }
  public void addMatch(Event event, User playerOne, User playerTwo){
    event.addMatch(playerOne, playerTwo);
  }
  public void addMatch(String eventTittle, User playerOne, User playerTwo){
    for (Event event:events)
    {
      if (eventTittle.equals(event.getTittle())){
        event.addMatch(playerOne, playerTwo);
      }
    }
  }
  public void setMatchScore(Event event, Match match, String score){
    event.setMatchScore(match, score);
  }
  public void setMatchScore(String eventTittle, Match match, String score){
    for (Event event:events)
    {
      if (eventTittle.equals(event.getTittle())){
        event.setMatchScore(match, score);
      }
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("CheckIn")){
      property.firePropertyChange("CheckIn",evt.getOldValue(), evt.getNewValue());
    }
    else {
      property.firePropertyChange(evt);
    }
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(propertyName, listener);
  }
}
