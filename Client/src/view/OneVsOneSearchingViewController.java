package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import viewModel.OneVsOneSearchingViewModel;
import viewModel.ViewModelFactory;

public class OneVsOneSearchingViewController implements ViewController{
  @FXML private Button declineBtn;
  @FXML private Button acceptBtn;
  @FXML private Button cancelBtn;
  @FXML private Label opponentNameLabel;
  @FXML private Label BRPLabel;
  @FXML private Label searchLabel;
  private ViewHandler viewHandler;
  private OneVsOneSearchingViewModel oneVsOneSearchingViewModel;
  private Region root;
  private boolean searching;
  private Thread updateSearch = new Thread(()->{
    while (searching){
      Platform.runLater(()->{
        searchLabel.textProperty().set("Searching.");
      });
      safeSleep(1);
      Platform.runLater(()->{
        searchLabel.textProperty().set("Searching..");
      });
      safeSleep(1);
      Platform.runLater(()->{
        searchLabel.textProperty().set("Searching...");
      });
      safeSleep(1);
    }
  });
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    searching = true;
    this.viewHandler = viewHandler;
    oneVsOneSearchingViewModel = viewModelFactory.oneVsOneSearchingViewModel();
    this.root = root;
    updateSearch.start();
  }

  @Override public void reset() {
    oneVsOneSearchingViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
  private void safeSleep(int sec){
    try {
      Thread.sleep(sec*1000);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
