package viewModel;

import Model.Event;
import Model.EventListModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class EventListViewModel implements PropertyChangeListener
{
  private EventListModel model;

  private ObservableList<SimpleEventViewModel> list;
  private ObjectProperty<SimpleEventViewModel> selectedEventProperty;
  private StringProperty errorProperty;
  private ViewModelState viewModelState;

  public EventListViewModel(EventListModel model, ViewModelState viewModelState)
  {
    this.model = model;
    this.model.addListener("Event", this);
    this.viewModelState = viewModelState;
    selectedEventProperty = new SimpleObjectProperty<>();
    errorProperty = new SimpleStringProperty();
    list = FXCollections.observableArrayList();
    /*loadFromModel();*/
  }

  public void clear()
  {
    errorProperty.set(null);
    // Maybe: loadFromModel()
  }

 /* private void loadFromModel()
  {
    list.clear();
    events = model.getAllEvents();
    for (int i = 0; i < events.size(); i++)
    {
      list.add(new SimpleEventViewModel(events.get(i)));
    }
  }*/

  public boolean seeDetails()
  {
    if (selectedEventProperty.get() != null)
    {
      viewModelState
          .setEventTittle(selectedEventProperty.get().getTittleProperty().get());
      return true;
    }
    else
    {
      errorProperty.set("No selection");
      return false;
    }
  }

  public ObservableList<SimpleEventViewModel> getAll()
  {
    System.out.println(list);
    return list;
  }

  public ArrayList<Event> getAllEvents(){
    return model.getAllEvents();
  }

  public void setSelected(SimpleEventViewModel eventVm)
  {
    selectedEventProperty.set(eventVm);
  }

  public StringProperty getErrorProperty()
  {
    return errorProperty;
  }

  private void removeSimpleEvent(String tittle)
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (list.get(i).getTittleProperty().get().equals(tittle))
      {
        list.remove(i);
        break;
      }
    }
  }

  public ArrayList<Event> getEventsByGame(String game) {
    return model.getEventsByGame(game);
  }

  public ArrayList<Event> getEventsBySkillLevel(String skillLevel) {
    return model.getEventsBySkillLevel(skillLevel);
  }

  public ArrayList<Event> getEventsByStatus(String status) {
    return model.getEventsByStatus(status);
  }

//  private void updateList(ArrayList<Event> events) {
//    list.clear();
//    for (Event event : events) {
//      list.add(new SimpleEventViewModel(event));
//    }
//  }

  private void addSimpleEvent(Event event)
  {
    //needs rework to work without localDate
   /* for (int i = 0; i < list.size(); i++)
    {
      Event ev = model.getEvent(list.get(i).getTittleProperty().get());
      if (event.getStartDate().isBefore(ev.getStartDate()))
      {
        list.add(i, new SimpleEventViewModel(event));
        return;
      }
    }*/
    /*list.add(new SimpleEventViewModel(event));*/
  }

  @Override public void propertyChange(PropertyChangeEvent event)
  {
    // Alternative: call loadFromModel to load all
    Platform.runLater(() -> {
      switch (event.getPropertyName())
      {
        case "Add":
          addSimpleEvent((Event) event.getNewValue());
          break;
        case "Remove":
          removeSimpleEvent((String) event.getOldValue());
          break;
      }
    });
  }
}
