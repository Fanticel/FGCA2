package viewModel;

import Model.EventListModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.NamedPropertyChangeSubject;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NotificationPopupViewModel implements PropertyChangeListener,
    NamedPropertyChangeSubject
{
  private StringProperty notification;
  private ObjectProperty<Paint> colorProperty;
  private ViewState viewState;
  private EventListModel model;
  private PropertyChangeListener listener;
  public NotificationPopupViewModel(EventListModel model, ViewState viewState)
  {
    this.model = model;
    model.addListener("notification",this);
    this.viewState = viewState;
    notification = new SimpleStringProperty("test");
    listener = null;
    Color green = new Color(0.051, 0.7882, 0.1098, 1.0);
    colorProperty = new SimpleObjectProperty<>(green);

    //previous version
    //notification = new SimpleStringProperty(viewModelState.getNotificationMessage());
    //notificationColor = new SimpleBooleanProperty(viewModelState.isNotificationColor());
  }

  public void reset(){
    notification.set("");
    Color green = new Color(0.051, 0.7882, 0.1098, 1.0);
    colorProperty.set(green);
  }

  public StringProperty getNotificationProperty()
  {
    return notification;
  }


  public ObjectProperty<Paint> getColorProperty()
  {
    return colorProperty;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    System.out.println(evt);
    if (evt.getPropertyName().equals("Notification")){
      listener.propertyChange(new PropertyChangeEvent(this, "", "", ""));
      Platform.runLater(()->{
        notification.set(evt.getNewValue().toString());
      });
      Color red = new Color(1.0, 0.0, 0.0, 1.0);
      Color green = new Color(0.051, 0.7882, 0.1098, 1.0);
      boolean color = (boolean) evt.getOldValue();
      if (color){
        colorProperty.set(red);
      }
      else {
        colorProperty.set(green);
      }
    }
  }
  @Override public void addListener(String propertyName,
      PropertyChangeListener l) {
    listener = l;
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener l) {
    if (listener.equals(l)){
      listener = null;
    }
  }
}
