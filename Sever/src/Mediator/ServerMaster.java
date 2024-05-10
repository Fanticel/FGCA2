package Mediator;

import Misc.NamedPropertyChangeSubject;
import Model.User;
import utility.UseredPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerMaster implements UseredPropertyChangeSubject {
  private Map<User, PropertyChangeListener> listenerMap;

  public ServerMaster() {
    listenerMap = new HashMap<>();
  }
  public void useredBroadcast(ArrayList<User> users, String message, String userName){
    for (User u:users){
      if (listenerMap.get(u)!=null){
        privateAnswer(listenerMap.get(u), message, userName);
      }
      else {

      }
    }
  }
  public void broadcast(String message, String userName) {
    for (PropertyChangeListener l : listenerMap.values()) {
      l.propertyChange(new PropertyChangeEvent(this, "mes", userName, message));
    }
  }

  public void privateAnswer(PropertyChangeListener oL, String message,
      String userName) {
    oL.propertyChange(new PropertyChangeEvent(this, "mes", userName, message));
  }
  public void useredAnswer(User user, String message, String userName){
    privateAnswer(listenerMap.get(user), message, userName);
  }
  @Override public void addListener(User user,
      PropertyChangeListener listener) {
    listenerMap.put(user, listener);
  }

  @Override public void removeListener(User user,
      PropertyChangeListener listener) {
    listenerMap.remove(user, listener);
  }
}
