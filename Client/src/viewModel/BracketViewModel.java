package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.Match;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BracketViewModel
{
  private ObservableList<SimpleStringProperty> labels;
  private ObservableList<SimpleBooleanProperty> visibleButtons;
  private EventListModel model;
  private ViewState viewState;
  private ArrayList<Match> matches;

  public BracketViewModel(EventListModel model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;
    matches = new ArrayList<>();
    labels = FXCollections.observableArrayList();
    visibleButtons = FXCollections.observableArrayList();
    Event event = model.getEvent(viewState.getTittle());
    matches = event.getMatches();
    for (Match match : matches)
    {
      System.out.println(match);
      String[] scores = match.getScore().split("-");
      if (match.getPlayers().get(0) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(0).getDisplayName()));
        labels.add(new SimpleStringProperty(scores[0]));
      }
      if (match.getPlayers().get(1) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(1).getDisplayName()));
        labels.add(new SimpleStringProperty(scores[1]));
      }
      if (match.getPlayers().get(0) != null && match.getPlayers().get(1) != null && event.getStatus().equals("In progress")){
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
        labels.add(new SimpleStringProperty(match.getPlayers().get(0).getDisplayName()));
        labels.add(new SimpleStringProperty(scores[0]));
      }
      if (match.getPlayers().get(1) == null){
        labels.add(new SimpleStringProperty());
        labels.add(new SimpleStringProperty(""));
      }
      else {
        labels.add(new SimpleStringProperty(match.getPlayers().get(1).getDisplayName()));
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
  public boolean startVote(int matchIndex){
    Match currentMatch = matches.get(matchIndex);
    if (model.getUser().getUsername().equals(currentMatch.getPlayers().get(0).getUsername()) ||
        model.getUser().getUsername().equals(currentMatch.getPlayers().get(1).getUsername()) ||
        model.isModerator()){
      viewState.setMatch(currentMatch);
      return true;
    }
    model.showLocalNotification("You cannot vote in a match you didn't participate in.", true);
    return false;
//    viewState.setVoteInfo();
  }
}
