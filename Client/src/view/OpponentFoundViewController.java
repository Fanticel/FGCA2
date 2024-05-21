package view;

import Model.Match;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.OpponentFoundViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

public class OpponentFoundViewController implements ViewController,
    PropertyChangeListener
{
  @FXML private SubScene rightSubScene;
  @FXML
  private Button btnPlayerOne;

  @FXML
  private Button btnPlayerTwo;

  @FXML
  private Label errorLabel;

  @FXML
  private Label playerOneName;

  @FXML
  private TextField playerOneScore;

  @FXML
  private Label playerTwoName;

  @FXML
  private TextField playerTwoScore;

  @FXML
  private Label verifyLabel;

  private ViewHandler viewHandler;
  private OpponentFoundViewModel opponentFoundViewModel;
  private Region root;
  private ViewModelFactory viewModelFactory;
  private ReportScoreViewController reportScoreViewController;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    opponentFoundViewModel = viewModelFactory.getOpponentFoundViewModel();
    opponentFoundViewModel.addListener("Saved", this);
    this.root = root;
    playerOneScore.textProperty().bindBidirectional(opponentFoundViewModel.getPlayerOneScoreProperty());
    playerTwoScore.textProperty().bindBidirectional(opponentFoundViewModel.getPlayerTwoScoreProperty());
    playerOneName.textProperty().bind(opponentFoundViewModel.getPlayerOneNameProperty());
    playerTwoName.textProperty().bind(opponentFoundViewModel.getPlayerTwoNameProperty());
    btnPlayerOne.textProperty().bind(opponentFoundViewModel.getPlayerOneNameProperty());
    btnPlayerTwo.textProperty().bind(opponentFoundViewModel.getPlayerTwoNameProperty());
    errorLabel.textProperty().bind(opponentFoundViewModel.getErrorProperty());
    verifyLabel.textProperty().bind(opponentFoundViewModel.getVerifyProperty());
    //rightSubScene | remember to implement the chat view as well
  }

  @Override public void reset() {
    opponentFoundViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML void pressSubmit(){
    if (opponentFoundViewModel.pressSubmit()){
      viewHandler.closePopupView();
    }
  }
  @FXML void pressPlayerOne(){
    opponentFoundViewModel.pressPlayerOne();
  }
  @FXML void pressPlayerTwo(){
    opponentFoundViewModel.pressPlayerTwo();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("Saved")){
      Platform.runLater(()->{
        viewHandler.closePopupView();
      });
    }
  }
}
