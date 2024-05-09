package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EventDescriptionViewModel implements PropertyChangeListener
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

  private EventListModel model;

  public EventDescriptionViewModel(EventListModel model,
      ViewState viewState)
  {
    this.model = model;
    this.model.addListener("EventChange",this);
//    this.viewState = viewState;
    errorProperty = new SimpleStringProperty();
    tittleProperty = new SimpleStringProperty();
    organizerProperty = new SimpleStringProperty();
    statusProperty = new SimpleStringProperty();
    gameProperty = new SimpleStringProperty();
    dateProperty = new SimpleStringProperty();
    BRPRangeProperty = new SimpleStringProperty();
    participantsNumberProperty = new SimpleStringProperty();
    participantsListProperty = new SimpleStringProperty();
    reset();
  }

  public void reset()
  {
    System.out.println("\n\n_________\n\n"+ViewState.getInstance().getTittle());
    Event event = model.getEvent(ViewState.getInstance().getTittle());
    System.out.println(ViewState.getInstance().getTittle());
    tittleProperty.set(event.getTittle());
    statusProperty.set(event.getStatus());
    gameProperty.set(event.getGame());
    dateProperty.set(event.getStartingHour() + ", " + event.getStartDate());
    BRPRangeProperty.set(
        event.getMinBRP() + " - " + event.getMaxBRP());
    participantsNumberProperty.set(event.getParticipants().size() + "/"
        + event.getMaxParticipants());
    if (event.getParticipants().isEmpty())
    {
      participantsListProperty.set("No participants yet.");
    }else {
      String string = "";
      for (User participant : event.getParticipants())
      {
        string += participant.getDisplayName() + System.lineSeparator();
      }
      participantsListProperty.set(string);
    }
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
    if (evt.getPropertyName().equals(tittleProperty.get())){
      Event event = (Event) evt.getNewValue();
      statusProperty.set(event.getStatus());
      participantsNumberProperty.set(event.getParticipants().size() + "/"
          + event.getMaxParticipants());
      if (event.getParticipants().isEmpty())
      {
        participantsListProperty.set("No participants yet.");
      }else {
        String string = "";
        for (User participant : event.getParticipants())
        {
          string += participant.getDisplayName() + System.lineSeparator();
        }
        participantsListProperty.set(string);
      }
    }
  }
}
