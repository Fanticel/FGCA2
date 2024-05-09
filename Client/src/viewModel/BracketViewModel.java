package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.Match;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BracketViewModel
{
  private ObservableList<SimpleStringProperty> labels;
  private ObservableList<SimpleBooleanProperty> visibleButtons;
  private EventListModel model;
  private ViewState viewState;

  public BracketViewModel(EventListModel model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    labels = FXCollections.observableArrayList();
    visibleButtons = FXCollections.observableArrayList();
    Event event = model.getEvent(viewState.getTittle());
    for (Match match : event.getMatches())
    {
      String[] scores = match.getScore().split("-");
      if (match.getPlayers().get(0) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(0).getUsername()));
        labels.add(new SimpleStringProperty(scores[0]));
      }
      if (match.getPlayers().get(1) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(1).getUsername()));
        labels.add(new SimpleStringProperty(scores[1]));
      }
      if (match.getPlayers().get(0) != null && match.getPlayers().get(1) != null){
        visibleButtons.add(new SimpleBooleanProperty(true));
      }
      else {
        visibleButtons.add(new SimpleBooleanProperty(false));
      }
    }

  }

  public ObservableList<SimpleStringProperty> getLabels()
  {
    return labels;
  }

  public ObservableList<SimpleBooleanProperty> getVisibleButtons()
  {
    return visibleButtons;
  }

  public void reset(){
    labels.clear();
    visibleButtons.clear();
    Event event = model.getEvent(viewState.getTittle());
    for (Match match : event.getMatches())
    {
      String[] scores = match.getScore().split("-");
      if (match.getPlayers().get(0) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(0).getUsername()));
        labels.add(new SimpleStringProperty(scores[0]));
      }
      if (match.getPlayers().get(1) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(1).getUsername()));
        labels.add(new SimpleStringProperty(scores[1]));
      }
      if (match.getPlayers().get(0) != null && match.getPlayers().get(1) != null){
        visibleButtons.add(new SimpleBooleanProperty(true));
      }
      else {
        visibleButtons.add(new SimpleBooleanProperty(false));
      }
    }
  }
}
