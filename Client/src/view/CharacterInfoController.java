package view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import viewModel.CharacterInfoViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CharacterInfoController implements ViewController,
    PropertyChangeListener {
  @FXML Label mainLabel;
  @FXML Label descLabel;
  @FXML HBox characterBox;
  private ViewHandler viewHandler;
  private CharacterInfoViewModel characterInfoViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    characterInfoViewModel = viewModelFactory.getCharacterInfoViewModel();
    characterInfoViewModel.addListener(null, this);
    this.root = root;
    mainLabel.textProperty().bind(characterInfoViewModel.getMainProperty());
    descLabel.textProperty().bind(characterInfoViewModel.getDescProperty());
    characterBox.setAlignment(Pos.CENTER);
    characterBox.spacingProperty().set(10);
    reset();
  }

  @Override public void reset() {
    characterInfoViewModel.reset();
    characterBox.getChildren().clear();
    characterBox.getChildren().addAll(characterInfoViewModel.generateCharacterButtons());
  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML private void onBackBtnPress(){
    viewHandler.openView("GameInfo");
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    viewHandler.openView("MoveInfo");
  }
}
