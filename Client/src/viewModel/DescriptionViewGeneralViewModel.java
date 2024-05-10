package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DescriptionViewGeneralViewModel implements PropertyChangeListener {
  private StringProperty errorProperty;
  private StringProperty tittleProperty;
  private StringProperty organizerProperty;
  private StringProperty statusProperty;
  private StringProperty gameProperty;
  private StringProperty dateProperty;
  private StringProperty BRPRangeProperty;
  private StringProperty participantsNumberProperty;//property for how many participant there are and can join
  private StringProperty participantsListProperty;
  private BooleanProperty disabledProperty;
  private ObservableList<SimplePlayerViewModel> list;
  private EventListModel model;

  public DescriptionViewGeneralViewModel(EventListModel model,
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
    disabledProperty = new SimpleBooleanProperty(true);
    list = FXCollections.observableArrayList();
    reset();
  }

  public void reset()
  {
    Event event = model.getEvent(ViewState.getInstance().getTittle());
    errorProperty.set("");
    tittleProperty.set(event.getTittle());
    //    organizerProperty.set(event.getOrganizer().getUsername());
    statusProperty.set(event.getStatus());
    gameProperty.set(event.getGame());
    dateProperty.set(event.getStartingHour() + ", " + event.getStartDate());
    BRPRangeProperty.set(
        event.getMinBRP() + " - " + event.getMaxBRP());
    participantsNumberProperty.set(event.getParticipants().size() + "/"
        + event.getMaxParticipants());
    if (event.getParticipants().isEmpty())
    {
      //participantsListProperty.set("No participants yet.");
    }else {
      for (User participant : event.getParticipants())
      {
        list.add(new SimplePlayerViewModel(participant, model));
      }
    }
    if (event.getStatus().equals("CheckIn")){
      disabledProperty.set(false);
    }else {
      disabledProperty.set(true);
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

  public StringProperty getParticipantsProperty()
  {
    return participantsNumberProperty;
  }

  public ObservableList<SimplePlayerViewModel> getList()
  {
    return list;
  }

  public BooleanProperty isDisabledProperty()
  {
    return disabledProperty;
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
  public void checkIn(){
    model.checkIn(getTittleProperty().get());
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
