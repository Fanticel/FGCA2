package Mediator;

import Model.Event;

public class EventPackage
{
  private String type;
  private String number;
  private String error;
  private Event event;
  public EventPackage(String type, Event event, String number)
  {
    this.type = type;
    this.number = number;
    this.event = event;
    error = null;
  }

  public EventPackage(String type, String error)
  {
    this.type = type;
    this.error = error;
    number = null;
    event = null;
  }

  public String getType()
  {
    return type;
  }

  public String getNumber()
  {
    return number;
  }

  public String getError()
  {
    return error;
  }

  public Event getEvent()
  {
    return event;
  }
  public boolean equals(Object obj){
    if (obj == null || getClass() != obj.getClass()){
      return false;
    }
    EventPackage other = (EventPackage) obj;
    return type.equals(other.type) && number.equals(other.number) && event.equals(other.event) && error.equals(other.error);
  }
}
