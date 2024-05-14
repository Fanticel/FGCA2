package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.ReportScoreViewModel;
import viewModel.ViewModelFactory;

public class ReportScoreViewController implements ViewController{
  @FXML private TextField playerOneScore;
  @FXML private TextField playerTwoScore;
  @FXML private Label playerOneName;
  @FXML private Label playerTwoName;
  @FXML private Label errorLabel;
  @FXML private Label verifyLabel;
  @FXML private Button btnPlayerOne;
  @FXML private Button btnPlayerTwo;
  private ViewHandler viewHandler;
  private ReportScoreViewModel reportScoreViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    reportScoreViewModel = viewModelFactory.getReportScoreViewModel();
    this.root = root;
    playerOneScore.textProperty().bindBidirectional(reportScoreViewModel.getPlayerOneScoreProperty());
    playerTwoScore.textProperty().bindBidirectional(reportScoreViewModel.getPlayerTwoScoreProperty());
    playerOneName.textProperty().bind(reportScoreViewModel.getPlayerOneNameProperty());
    playerTwoName.textProperty().bind(reportScoreViewModel.getPlayerTwoNameProperty());
    btnPlayerOne.textProperty().bind(reportScoreViewModel.getPlayerOneNameProperty());
    btnPlayerTwo.textProperty().bind(reportScoreViewModel.getPlayerTwoNameProperty());
    errorLabel.textProperty().bind(reportScoreViewModel.getErrorProperty());
    verifyLabel.textProperty().bind(reportScoreViewModel.getVerifyProperty());
  }
  @FXML void pressSubmit(){
    if (reportScoreViewModel.pressSubmit()){
      viewHandler.closePopupView();
    }
  }
  @FXML void pressPlayerOne(){
    reportScoreViewModel.pressPlayerOne();
  }
  @FXML void pressPlayerTwo(){
    reportScoreViewModel.pressPlayerTwo();
  }
  @Override public void reset() {
    reportScoreViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
}
