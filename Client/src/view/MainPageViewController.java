package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import viewModel.MainPageViewModel;
import viewModel.ViewModelFactory;

public class MainPageViewController implements ViewController{
  private ViewHandler viewHandler;
  private MainPageViewModel mainPageViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    this.mainPageViewModel = viewModelFactory.getMainPageViewModel();
    this.root = root;
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
}
