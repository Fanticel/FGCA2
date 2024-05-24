package viewModel;

import Model.EventListModel;
import Model.Match;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OpponentFoundViewModel implements PropertyChangeListener,
    NamedPropertyChangeSubject
{
  private StringProperty playerOneNameProperty;
  private StringProperty playerOneScoreProperty;
  private StringProperty playerTwoNameProperty;
  private StringProperty playerTwoScoreProperty;
  private String chosenPlayer;
  private StringProperty errorProperty;
  private StringProperty verifyProperty;
  private User playerOne;
  private User playerTwo;
  private EventListModel model;
  private PropertyChangeListener listener;
  public OpponentFoundViewModel(EventListModel model){
    this.model = model;
    model.addListener("MatchSaved", this);
    verifyProperty = new SimpleStringProperty("Verify the winner");
    errorProperty = new SimpleStringProperty("");
    playerOneNameProperty = new SimpleStringProperty(ViewState.getInstance().getMatchPlayers()[0]);
    playerTwoNameProperty = new SimpleStringProperty(ViewState.getInstance().getMatchPlayers()[1]);
    playerOneScoreProperty = new SimpleStringProperty("");
    playerTwoScoreProperty = new SimpleStringProperty("");
    listener = null;
  }

  public void reset(){
    playerOneNameProperty.set(ViewState.getInstance().getMatchPlayers()[0]);
    playerTwoNameProperty.set(ViewState.getInstance().getMatchPlayers()[1]);
    playerOneScoreProperty.set("");
    playerTwoScoreProperty.set("");
  }
  public StringProperty getPlayerOneNameProperty() {
    return playerOneNameProperty;
  }
  public StringProperty getPlayerOneScoreProperty() {
    return playerOneScoreProperty;
  }
  public StringProperty getPlayerTwoNameProperty() {
    return playerTwoNameProperty;
  }
  public StringProperty getPlayerTwoScoreProperty() {
    return playerTwoScoreProperty;
  }
  public StringProperty getErrorProperty() {
    return errorProperty;
  }
  public StringProperty getVerifyProperty() {
    return verifyProperty;
  }
  public boolean pressSubmit(){
    errorProperty.set("");
    if (Integer.parseInt(playerOneScoreProperty.get()) > Integer.parseInt(playerTwoScoreProperty.get())){
      if (chosenPlayer.equals(playerOneNameProperty.get())){
        //model.voteOnOutcome(ViewState.getInstance().getTittle(), playerOne.getUsername(), playerTwo.getUsername(), Integer.parseInt(playerOneScoreProperty.get()), Integer.parseInt(playerTwoScoreProperty.get()));
          model.addPlayerMatch(null, ViewState.getInstance().getMatchPlayers()[2], playerOneScoreProperty.get() + "-" + playerTwoScoreProperty.get());
        return true;
      }
      else {
        errorProperty.set("Selected winner does not match up with the score");
        return false;
      }
    }
    if (Integer.parseInt(playerOneScoreProperty.get()) < Integer.parseInt(playerTwoScoreProperty.get())){
      if (chosenPlayer.equals(playerTwoNameProperty.get())){
        //model.voteOnOutcome(ViewState.getInstance().getTittle(), playerOne.getUsername(), playerTwo.getUsername(), Integer.parseInt(playerOneScoreProperty.get()), Integer.parseInt(playerTwoScoreProperty.get()));
        model.addPlayerMatch(null, ViewState.getInstance().getMatchPlayers()[2], playerOneScoreProperty.get() + "-" + playerTwoScoreProperty.get());
        return true;
      }
      else {
        errorProperty.set("Selected winner does not match up with the score");
        return false;
      }
    }
    return true;
  }
  public void pressPlayerOne(){
    verifyProperty.set("You have selected " + playerOneNameProperty.get() + " as the winner");
    chosenPlayer = playerOneNameProperty.get();
  }
  public void pressPlayerTwo(){
    verifyProperty.set("You have selected " + playerTwoNameProperty.get() + " as the winner");
    chosenPlayer = playerTwoNameProperty.get();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("MatchSaved")) {
      listener.propertyChange(new PropertyChangeEvent(this, "Saved", "", ""));
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
