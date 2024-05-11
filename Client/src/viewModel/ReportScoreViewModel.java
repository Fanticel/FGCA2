package viewModel;

import Model.EventListModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportScoreViewModel {
  private StringProperty playerOneNameProperty;
  private StringProperty playerOneScoreProperty;
  private StringProperty playerTwoNameProperty;
  private StringProperty playerTwoScoreProperty;
  private EventListModel model;
  public ReportScoreViewModel(EventListModel model){
    this.model = model;
    playerOneNameProperty = new SimpleStringProperty("SamplePlayer1");
    playerTwoNameProperty = new SimpleStringProperty("SamplePlayer2");
    playerOneScoreProperty = new SimpleStringProperty("");
    playerTwoScoreProperty = new SimpleStringProperty("");
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
  public void pressSubmit(){}
  public void pressPlayerOne(){}
  public void pressPlayerTwo(){}
}
