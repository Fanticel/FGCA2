package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class EventList
    implements PropertyChangeListener, NamedPropertyChangeSubject
{
  private ArrayList<Event> events;
  private PropertyChangeSupport property;

  public EventList()
  {
    events = new ArrayList<>();
    property = new PropertyChangeSupport(this);
  }

  public ArrayList<Event> getAllEvents()
  {
    return events;
  }

  public Event getEvent(String tittle)
  {
    for (Event event : events)
    {
      if (tittle.equals(event.getTittle()))
      {
        return event;
      }
    }
    return null;
  }

  public void addEvent(Event newEvent)
  {
    for (Event event : events)
    {
      if (event.getTittle().equals(newEvent.getTittle()))
      {
        throw new IllegalArgumentException("Event title has to be unique");
      }
    }
    events.add(newEvent);
    newEvent.addListener("EventChange", this);
    newEvent.addListener("CheckIn", this);
    newEvent.addListener("outOfTime", this);
  }

  public ArrayList<Object> addParticipant(String eventTittle, User user)
  {
    for (Event event : events)
    {
      if (eventTittle.equals(event.getTittle()))
      {
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
    for (Event event : events)
    {
      if (eventTitle.equals(event.getTittle()))
      {
        return event.checkIn(user);
      }
    }
    return "Event not found_;_true";
  }

  public boolean removeParticipant(String eventTittle, User user)
  {
    for (Event event : events)
    {
      if (eventTittle.equals(event.getTittle()))
      {
        event.removeParticipant(user);
        return true;
      }
    }
    return false;
  }


  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("CheckIn"))
    {
      property.firePropertyChange("CheckIn", evt.getOldValue(),
          evt.getNewValue());
    }
    else
    {
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

  @Override public String toString()
  {
    String evString = "";
    for (Event ev : events)
    {
      evString += ev;
    }
    return evString;
  }
}
