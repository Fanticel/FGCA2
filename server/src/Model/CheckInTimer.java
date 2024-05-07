package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CheckInTimer implements Runnable, NamedPropertyChangeSubject
{
  private int timerSeconds;
  private boolean active;
  private PropertyChangeSupport property;

  public CheckInTimer()
  {
    this.timerSeconds = 10;
    active = true;
    property = new PropertyChangeSupport(this);
  }

  @Override public void run()
  {
    while (active){
      try
      {
        Thread.sleep(timerSeconds*1000);
      }
      catch (InterruptedException e)
      {
        //
      }
      property.firePropertyChange("CheckIn", null, timerSeconds);
    }
  }

  @Override public void addListener(String propertyName, PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName, PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(propertyName, listener);
  }

  public void setActive(boolean active)
  {
    this.active = active;
  }
}