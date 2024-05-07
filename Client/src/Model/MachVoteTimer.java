package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MachVoteTimer implements Runnable, NamedPropertyChangeSubject
{
  private int timerSeconds;
  private boolean active;
  private PropertyChangeSupport property;

  public MachVoteTimer(int timerSeconds)
  {
    this.timerSeconds = timerSeconds;
    active = true;
    property = new PropertyChangeSupport(this);
  }

  @Override public void run()
  {
    LocalTime time = LocalTime.ofSecondOfDay(timerSeconds);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    while (active){
      while (timerSeconds >= 0){
        property.firePropertyChange("Time", timerSeconds, time.format(timeFormatter));
        try
        {
          Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
          //
        }
        time = time.minusSeconds(1);
        timerSeconds--;
      }
    }
    property.firePropertyChange("OutOfTIme", null, 0);
    PropertyChangeListener[] listeners = property.getPropertyChangeListeners();
    for (int i=0; i<listeners.length; i++)
    {
      removeListener("Time", listeners[i]);
      removeListener("OutOfTIme", listeners[i]);
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
}
