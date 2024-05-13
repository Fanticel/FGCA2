package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import viewModel.DescriptionViewGeneralViewModel;
import viewModel.SimplePlayerViewModel;
import viewModel.ViewModelFactory;

public class DescriptionViewGeneralController implements ViewController{
  @FXML
  private Button btnCheckIn;

  @FXML
  private Button btnQuit;

  @FXML
  private TableColumn<SimplePlayerViewModel, String> colName;

  @FXML
  private TableColumn<SimplePlayerViewModel, String> colSkillLevel;

  @FXML
  private Label lblCheckInStatus;

  @FXML
  private Label lblTimeLeft;

  @FXML
  private TableView<SimplePlayerViewModel> tblPlayers;

  @FXML
  private TextField txtGame;

  @FXML
  private TextField txtNumberOfPlayers;

  @FXML
  private TextField txtSkillLevel;

  @FXML
  private TextField txtTimeUntilStart;


  @FXML
  private VBox vboxDetails;
  private ViewHandler viewHandler;
  private DescriptionViewGeneralViewModel descriptionViewGeneralViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    descriptionViewGeneralViewModel = viewModelFactory.getDescriptionViewGeneralViewModel();
    this.root = root;
    txtGame.textProperty().bind(descriptionViewGeneralViewModel.getGameProperty());
    txtTimeUntilStart.textProperty().bind(descriptionViewGeneralViewModel.getDateProperty());
    txtNumberOfPlayers.textProperty().bind(descriptionViewGeneralViewModel.getParticipantsProperty());
    txtSkillLevel.textProperty().bind(descriptionViewGeneralViewModel.getBRPRangeProperty());
    btnCheckIn.disableProperty().bind(descriptionViewGeneralViewModel.isDisabledProperty());
    colName.setCellValueFactory(
        cellData -> cellData.getValue().getPlayerDisplayName());
    colSkillLevel.setCellValueFactory(
        cellData -> cellData.getValue().getSkillLevel());
    tblPlayers.setItems(descriptionViewGeneralViewModel.getList());

  }

  @Override public void reset() {
    tblPlayers.getItems().clear();
    descriptionViewGeneralViewModel.reset();
  }
  @FXML void pressCheckInButton() {
    descriptionViewGeneralViewModel.checkIn();
  }

  @FXML public void onQuit(){
    descriptionViewGeneralViewModel.quitEvent();
  }

  @Override public Region getRoot() {
    return root;
  }
}
