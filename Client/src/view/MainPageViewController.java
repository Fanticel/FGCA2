package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import viewModel.MainPageViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

public class MainPageViewController implements ViewController{
  @FXML Label welcomeLabel;
  private ViewHandler viewHandler;
  private MainPageViewModel mainPageViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    this.mainPageViewModel = viewModelFactory.getMainPageViewModel();
    this.root = root;
    welcomeLabel.textProperty().bind(mainPageViewModel.getWelcomeProperty());
    welcomeLabel.setVisible(true);
  }

  @Override public void reset() {

  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML void btnTournamentPress(){
    viewHandler.openView("EventList");
  }
  @FXML void btnMatchPress(){
    viewHandler.openView("1v1");
  }
  @FXML void btnChatPress(){
    ViewState.getInstance().setDesiredChat("local");
    viewHandler.openView("Chat");
  }
  @FXML void btnGameInfoPress(){
    viewHandler.openView("GameInfo");
  }
}
