package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventDescriptionViewModel implements PropertyChangeListener,
    NamedPropertyChangeSubject
{
//  private ViewState viewState;
  private StringProperty errorProperty;
  private StringProperty tittleProperty;
  private StringProperty organizerProperty;
  private StringProperty statusProperty;
  private StringProperty gameProperty;
  private StringProperty dateProperty;
  private StringProperty BRPRangeProperty;
  private StringProperty participantsNumberProperty;//property for how many participant there are and can join
  private StringProperty participantsListProperty;
  private PropertyChangeListener listener;
  private EventListModel model;

  public EventDescriptionViewModel(EventListModel model,
      ViewState viewState)
  {
    this.model = model;
    this.model.addListener("EventChange",this);
//    this.viewState = viewState;
    errorProperty = new SimpleStringProperty();
    tittleProperty = new SimpleStringProperty();
    statusProperty = new SimpleStringProperty();
    //organizerProperty = new SimpleStringProperty();
    //gameProperty = new SimpleStringProperty();
    //dateProperty = new SimpleStringProperty();
    //BRPRangeProperty = new SimpleStringProperty();
    participantsNumberProperty = new SimpleStringProperty();
    //participantsListProperty = new SimpleStringProperty();*/
    listener = null;
    reset();
  }

  public void reset()
  {
    Event event = model.getEvent(ViewState.getInstance().getTittle());
    tittleProperty.set(event.getTittle());
    statusProperty.set(event.getStatus());
   //gameProperty.set(event.getGame());
    //dateProperty.set(event.getStartingHour() + ", " + event.getStartDate());
    //BRPRangeProperty.set(
     //   event.getMinBRP() + " - " + event.getMaxBRP());
    participantsNumberProperty.set(event.getParticipants().size() + "/"
        + event.getMaxParticipants());
    /*if (event.getParticipants().isEmpty())
    {
      participantsListProperty.set("No participants yet.");
    }else {
      String string = "";
      for (User participant : event.getParticipants())
      {
        string += participant.getDisplayName() + System.lineSeparator();
      }
      participantsListProperty.set(string);
    }*/
  }

  public StringProperty getErrorProperty()
  {
    return errorProperty;
  }

  public StringProperty getTittleProperty()
  {
    return tittleProperty;
  }

  public StringProperty getOrganizerProperty()
  {
    return organizerProperty;
  }

  public StringProperty getStatusProperty()
  {
    return statusProperty;
  }

  public StringProperty getGameProperty()
  {
    return gameProperty;
  }
  public StringProperty getDateProperty(){
    return dateProperty;
  }
  public StringProperty getBRPRangeProperty()
  {
    return BRPRangeProperty;
  }

  public String getParticipantsNumber()
  {
    return participantsNumberProperty.get();
  }

  public String getMaxParticipants()
  {
    String[] strings = getParticipantsNumber().split("/");
    return strings[1];
  }



  private void join()
  {
    try
    {
      model.addParticipant(tittleProperty.get());
    }
    catch (Exception e)
    {
      errorProperty.set(e.getMessage());
    }

  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("EventChange") && evt.getNewValue().equals(tittleProperty.get())){
      listener.propertyChange(new PropertyChangeEvent(this, "Change", "", ""));
    }
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener l)
  {
    listener = l;
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    listener = null;
  }
}
