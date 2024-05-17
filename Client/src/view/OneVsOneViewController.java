package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import viewModel.OneVsOneViewModel;
import viewModel.ViewModelFactory;
import javafx.scene.control.Button;

public class OneVsOneViewController implements ViewController
{
  private ViewHandler viewHandler;
  private Region root;
  private OneVsOneViewModel oneVsOneViewModel;

//  @FXML private Button backButton;
//  @FXML private Button searchButton;
  @FXML private ChoiceBox<String> gameButton;
  @FXML private ChoiceBox<String> skillButton;
  @FXML private Button searchButton;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    oneVsOneViewModel = viewModelFactory.getOneVsOneViewModel();


    gameButton.setItems(FXCollections.observableArrayList("Tekken 8",
        "Super Smash Bros.Ultimate", "Street Fighter 6", "Mortal Kombat 1",
        "Guilty Gear Strive"));
    gameButton.valueProperty().bindBidirectional(oneVsOneViewModel.getGameProperty());

    skillButton.setItems(
        FXCollections.observableArrayList("Lower", "Same", "Higher", "Any"));
    skillButton.valueProperty().bindBidirectional(oneVsOneViewModel.getSkillProperty());
//    backButton.setOnAction(event -> viewHandler.openView(""));
//    searchButton.setOnAction(event -> viewHandler.openView("MatchInfoView.fxml"));

    gameButton.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      updateSearchButtonDisable();
    });

    skillButton.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      updateSearchButtonDisable();
    });

    searchButton.disableProperty().bind(oneVsOneViewModel.disableSearchProperty());
  }

  private void updateSearchButtonDisable() {
    boolean disableGameButton = gameButton.getSelectionModel().isEmpty();
    boolean disableSkillButton = skillButton.getSelectionModel().isEmpty();
    oneVsOneViewModel.updateDisableSearch(disableGameButton, disableSkillButton);
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
  @FXML public void btnSearchPress(){
    oneVsOneViewModel.pressSearchButton();
    viewHandler.openPopupView("SearchingOpponent");
  }
  @FXML public void btnBackPress(){
    viewHandler.openView("");
  }
}

