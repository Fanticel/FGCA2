package Model;

import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MachVoteTimer implements Runnable, NamedPropertyChangeSubject {
  private int timerSeconds;
  private boolean active;
  private PropertyChangeSupport property;

  public MachVoteTimer(int timerSeconds) {
    this.timerSeconds = timerSeconds;
    active = false;
    property = new PropertyChangeSupport(this);
  }

  @Override public void run() {
    while (timerSeconds > 0) {
      while (active) {
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {
        }
        timerSeconds--;
        System.out.println(timerSeconds);
      }
      this.setActive(false);
    }
    property.firePropertyChange("OutOfTime", null, 0);
    PropertyChangeListener[] listeners = property.getPropertyChangeListeners();
    for (int i = 0; i < listeners.length; i++) {
      removeListener("Time", listeners[i]);
      removeListener("OutOfTime", listeners[i]);
    }
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener listener) {
    property.addPropertyChangeListener(propertyName, listener);
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener) {
    property.removePropertyChangeListener(propertyName, listener);
  }
}
