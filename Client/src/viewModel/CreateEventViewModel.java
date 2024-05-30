package viewModel;

import Model.EventListModel;
import javafx.beans.property.*;
import javafx.scene.control.SingleSelectionModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateEventViewModel
{
  private EventListModel model;
  private StringProperty titleProperty;
  private ObjectProperty<String> gameProperty;
  private ObjectProperty<LocalDate> dateProperty;
  private ObjectProperty<String> startingHourProperty;
  private ObjectProperty<String> maxPlayersProperty;
  private IntegerProperty minBRPProperty;
  private IntegerProperty maxBRPProperty;
  private StringProperty errorProperty;

  public CreateEventViewModel(EventListModel model)
  {
    this.model = model;
    titleProperty = new SimpleStringProperty();
    gameProperty = new SimpleObjectProperty<>();
    dateProperty = new SimpleObjectProperty<>();
    startingHourProperty = new SimpleObjectProperty<>();
    maxPlayersProperty = new SimpleObjectProperty<>();
    minBRPProperty = new SimpleIntegerProperty(0);
    maxBRPProperty = new SimpleIntegerProperty(0);
    errorProperty = new SimpleStringProperty("");
  }

  public ObjectProperty<String> getGameProperty()
  {
    return gameProperty;
  }

  public StringProperty getTitleProperty()
  {
    return titleProperty;
  }

  public ObjectProperty<LocalDate> getDateProperty()
  {
    return dateProperty;
  }


  public ObjectProperty<String> getStartingHourProperty()
  {
    return startingHourProperty;
  }

  public ObjectProperty<String> getMaxPlayersProperty()
  {
    return maxPlayersProperty;
  }

  public IntegerProperty getMinBRPProperty()
  {
    return minBRPProperty;
  }

  public IntegerProperty getMaxBRPProperty()
  {
    return maxBRPProperty;
  }

  public StringProperty getErrorProperty()
  {
    return errorProperty;
  }

  public void reset(){
    titleProperty.set(null);
    gameProperty.set(null);
    dateProperty.set(null);
    startingHourProperty.set(null);
    maxPlayersProperty.set(null);
    minBRPProperty.set(0);
    maxBRPProperty.set(0);
    errorProperty.set("");
  }
  public boolean confirm(){
    String answer = "";
    LocalDate currentDate = LocalDate.now();
    if (titleProperty.isEmpty().get()){
      answer = "Title cannot be empty";
    }else if (gameProperty.isNull().get()){
      answer = "Game cannot be empty";
    }
    else if (dateProperty.isNull().get()){
      answer = "Date cannot be empty";
    }
    else if (dateProperty.get().isBefore(currentDate) || dateProperty.get().isEqual(currentDate))
    {
      answer = "Date cannot be today or before";
    }
    else if (maxPlayersProperty.isNull().get()){
      answer = "Maximum players cannot be empty";
    }
    else if (startingHourProperty.isNull().get()){
      answer = "Starting hour cannot be empty";
    }
    else if (minBRPProperty.get() == 0){
      answer = "Minimum BRP cannot be empty";
    }
    else if (maxBRPProperty.get() == 0){
      answer = "Maximum BRP cannot be empty";
    }
    else
    {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String date = dateProperty.get().format(formatter);
      answer = model.addEvent(titleProperty.get(),gameProperty.get(), minBRPProperty.get(), maxBRPProperty.get(), Integer.valueOf(maxPlayersProperty.get()), date, Integer.valueOf(startingHourProperty.get()));
    }
    if (answer.equals("pog")){
      return true;
    }
    else {
      errorProperty.set(answer);
    }
    return false;
  }
}
