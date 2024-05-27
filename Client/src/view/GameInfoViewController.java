package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import viewModel.GameInfoViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameInfoViewController implements ViewController,
    PropertyChangeListener {
  @FXML private VBox mainStage;
  private ViewHandler viewHandler;
  private GameInfoViewModel gameInfoViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    gameInfoViewModel = viewModelFactory.getGameInfoViewModel();
    gameInfoViewModel.addListener(null, this);
    this.root = root;
    mainStage.spacingProperty().set(10);
    mainStage.getChildren().addAll(gameInfoViewModel.generateGameButtons());
    reset();
  }

  @Override public void reset() {
    gameInfoViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    viewHandler.openView("CharacterInfo");
  }
  @FXML private void onBackBtnPress(){
    viewHandler.openView("");
  }
}
