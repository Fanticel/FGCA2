package viewModel;

import Model.EventListModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportScoreViewModel {
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
  public ReportScoreViewModel(EventListModel model){
    playerOne = ViewState.getInstance().getMatch().getPlayers().get(0);
    playerTwo = ViewState.getInstance().getMatch().getPlayers().get(1);
    this.model = model;
    verifyProperty = new SimpleStringProperty("Verify the winner");
    errorProperty = new SimpleStringProperty("");
    playerOneNameProperty = new SimpleStringProperty("SamplePlayer1");
    playerTwoNameProperty = new SimpleStringProperty("SamplePlayer2");
    playerOneScoreProperty = new SimpleStringProperty("");
    playerTwoScoreProperty = new SimpleStringProperty("");
    try{
      reset();
    }
    catch (Error e){
      System.err.println(e);
    }
  }
  public void reset(){
    playerOneNameProperty.set(playerOne.getDisplayName());
    playerTwoNameProperty.set(playerTwo.getDisplayName());
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
        model.voteOnOutcome(ViewState.getInstance().getTittle(), playerOne.getUsername(), playerTwo.getUsername(), Integer.parseInt(playerOneScoreProperty.get()), Integer.parseInt(playerTwoScoreProperty.get()));
        return true;
      }
      else {
        errorProperty.set("Selected winner does not match up with the score");
        return false;
      }
    }
    if (Integer.parseInt(playerOneScoreProperty.get()) < Integer.parseInt(playerTwoScoreProperty.get())){
      if (chosenPlayer.equals(playerTwoNameProperty.get())){
        model.voteOnOutcome(ViewState.getInstance().getTittle(), playerOne.getUsername(), playerTwo.getUsername(), Integer.parseInt(playerOneScoreProperty.get()), Integer.parseInt(playerTwoScoreProperty.get()));
        return true;
      }
      else {
        errorProperty.set("Selected winner does not match up with the score");
        return false;
      }
    }
    else {
      model.showLocalNotification("The score cannot be equal", true);
      return false;
    }
  }
  public void pressPlayerOne(){
    verifyProperty.set("You have selected " + playerOneNameProperty.get() + " as the winner");
    chosenPlayer = playerOneNameProperty.get();
  }
  public void pressPlayerTwo(){
    verifyProperty.set("You have selected " + playerTwoNameProperty.get() + " as the winner");
    chosenPlayer = playerTwoNameProperty.get();
  }
}
