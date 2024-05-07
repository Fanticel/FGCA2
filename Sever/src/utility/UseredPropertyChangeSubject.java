package utility;

import Model.User;

import java.beans.PropertyChangeListener;

public interface UseredPropertyChangeSubject {
  void addListener(User user, PropertyChangeListener listener);
  void removeListener(User user, PropertyChangeListener listener);
}
