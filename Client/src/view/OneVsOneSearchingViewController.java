package view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import utility.IntStringConverter;
import viewModel.OneVsOneSearchingViewModel;
import viewModel.ViewModelFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OneVsOneSearchingViewController implements ViewController,
    PropertyChangeListener
{
  @FXML private Button declineBtn;
  @FXML private Button acceptBtn;
  @FXML private Button cancelBtn;
  @FXML private Label opponentNameLabel;
  @FXML private Label BRPLabel;
  @FXML private Label searchLabel;
  @FXML
  private HBox BRPHBox;
  @FXML
  private HBox nameHBox;
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
    oneVsOneSearchingViewModel.addListener("Opponent", this);
    this.root = root;
    Bindings.bindBidirectional(BRPLabel.textProperty(),oneVsOneSearchingViewModel.getBRPProperty(),
        new IntStringConverter());
    BRPHBox.visibleProperty().bind(oneVsOneSearchingViewModel.isOpponentFoundProperty());
    opponentNameLabel.textProperty().bind(oneVsOneSearchingViewModel.getOpponentNameProperty());
    nameHBox.visibleProperty().bind(oneVsOneSearchingViewModel.isOpponentFoundProperty());
    searchLabel.visibleProperty().bind(oneVsOneSearchingViewModel.isVisibleSearchProperty());
    cancelBtn.visibleProperty().bind(oneVsOneSearchingViewModel.isVisibleSearchProperty());
    declineBtn.visibleProperty().bind(oneVsOneSearchingViewModel.isOpponentFoundProperty());
    acceptBtn.visibleProperty().bind(oneVsOneSearchingViewModel.isOpponentFoundProperty());
    updateSearch.setDaemon(true);
    updateSearch.start();
  }

  @Override public void reset() {
    searching = true;
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
  @FXML public void btnCancelPress(){
    oneVsOneSearchingViewModel.cancel();
    viewHandler.closePopupView();
  }
  @FXML public void btnDeclinePress(){
    oneVsOneSearchingViewModel.decline();
    viewHandler.closePopupView();
  }
  @FXML public void btnAcceptPress(){
    oneVsOneSearchingViewModel.accept();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("Refused")){
      Platform.runLater(()->{
        viewHandler.closePopupView();
      });
    }
    else if (evt.getPropertyName().equals("Accepted")){
      Platform.runLater(()->{
        viewHandler.closePopupView();
        viewHandler.openPopupView("OpponentFound");
      });
    }
  }
}
