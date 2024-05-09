package viewModel;

import Model.Event;
import Model.EventListModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleEventViewModel
{
  private StringProperty tittleProperty;
  private StringProperty gameProperty;
  private StringProperty BRPProperty;
  private StringProperty dateProperty;
  private EventListModel model;

  private ViewModelState viewModelState;

  public SimpleEventViewModel(Event event, EventListModel model)
  {

    this.model = model;
    tittleProperty = new SimpleStringProperty(event.getTittle());
    gameProperty = new SimpleStringProperty(event.getGame());
    BRPProperty = new SimpleStringProperty(event.getMinBRP() + " - " + event.getMaxBRP());
    dateProperty = new SimpleStringProperty(event.getStartDate().toString());
  }


  public StringProperty getTittleProperty()
  {
    return tittleProperty;
  }

  public StringProperty getGameProperty()
  {
    return gameProperty;
  }

  public StringProperty getBRPProperty()
  {
    return BRPProperty;
  }

  public StringProperty getDateProperty()
  {
    return dateProperty;
  }
  public void RegisterButtonPress(){
    model.addParticipant(tittleProperty.get());
  }
  public void setViewModel(String tittle){
    ViewState.getInstance().setTittle(tittle);
    System.out.println(ViewState.getInstance().getTittle());
  }
}
