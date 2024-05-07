package mediator;

import Model.Event;

import java.util.ArrayList;

public class EventListPackage
{
  private String type;
  private ArrayList<Event> events;

  public EventListPackage(String type, ArrayList<Event> events)
  {
    this.type = type;
    this.events = events;
  }

  public EventListPackage(String type)
  {
    this.type = type;
    events = new ArrayList<>();
  }

  public String getType()
  {
    return type;
  }

  public ArrayList<Event> getEvents()
  {
    return events;
  }

}
