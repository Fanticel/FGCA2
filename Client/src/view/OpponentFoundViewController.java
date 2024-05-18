package view;

import Model.Match;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.layout.Region;
import viewModel.OpponentFoundViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

import java.io.IOException;
import java.net.URL;

public class OpponentFoundViewController implements ViewController{
  @FXML private SubScene rightSubScene;
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
    this.root = root;
    //rightSubScene | remember to implement the chat view as well
  }

  @Override public void reset() {

  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML public void pressPlayerOne(){

  }
  @FXML public void pressPlayerTwo(){

  }
  @FXML public void pressSubmit(){

  }
}
