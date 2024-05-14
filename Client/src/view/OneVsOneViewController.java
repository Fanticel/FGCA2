package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import viewModel.ViewModelFactory;
import javafx.scene.control.Button;

public class OneVsOneViewController implements ViewController
{
  private ViewHandler viewHandler;
  private Region root;

  @FXML private Button backButton;
  @FXML private ChoiceBox<String> gameButton;
  @FXML private ChoiceBox<String> skillButton;
  @FXML private Button searchButton;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    this.root = root;

    gameButton.setItems(FXCollections.observableArrayList("Tekken 8",
        "Super Smash Bros.Ultimate", "Street Fighter 6", "Mortal Kombat 1",
        "Guilty Gear Strive"));

    skillButton.setItems(
        FXCollections.observableArrayList("Lower", "Same", "Higher", "Any"));
    backButton.setOnAction(event -> viewHandler.openView(""));
    searchButton.setOnAction(event -> viewHandler.openView("MatchInfoView.fxml"));

  }

  @Override public void reset()
  {
    gameButton.getSelectionModel().clearSelection();
    skillButton.getSelectionModel().clearSelection();
  }

  @Override public Region getRoot()
  {
    return root;
  }
}

