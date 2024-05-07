package Mediator;

import Model.Event;

import java.util.ArrayList;

public class EventListPackage
{
  private ArrayList<EventInformationPackage> events;

  public EventListPackage(ArrayList<EventInformationPackage> events)
  {
    this.events = events;
  }
  public EventListPackage(){
    events = new ArrayList<>();
  }
  public void addEnMasse(ArrayList<Event> eventList){
    for (Event e : eventList) {
      events.add(new EventInformationPackage(e));
    }
  }

  public ArrayList<EventInformationPackage> getEvents()
  {
    return events;
  }
}
