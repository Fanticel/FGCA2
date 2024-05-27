package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import mediator.CharacterInfoPackage;
import utility.SimpleMovesProperties;
import viewModel.MoveInfoViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

public class MoveInfoController implements ViewController{
  @FXML private TableColumn<SimpleMovesProperties, String> moveColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> damageColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> guardColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> smashColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> startupColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> activeColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> recoveryColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> onBlockColumn;
  @FXML private TableColumn<SimpleMovesProperties, String> invuColumn;
  @FXML private Label mainLabel;
  @FXML private Label overviewLabel;
  @FXML private Label prosLabel;
  @FXML private Label consLabel;
  @FXML private TableView moveTable;
  private ViewHandler viewHandler;
  private MoveInfoViewModel moveInfoViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    moveInfoViewModel = viewModelFactory.getMoveInfoViewModel();
    this.root = root;
    mainLabel.textProperty().bind(moveInfoViewModel.getMainProperty());
    overviewLabel.textProperty().bind(moveInfoViewModel.getOverviewProperty());
    prosLabel.textProperty().bind(moveInfoViewModel.getProsProperty());
    consLabel.textProperty().bind(moveInfoViewModel.getConsProperty());
    moveColumn.setCellValueFactory(cellData -> cellData.getValue().getMove());
    damageColumn.setCellValueFactory(cellData -> cellData.getValue().getDamage());
    guardColumn.setCellValueFactory(cellData -> cellData.getValue().getGuard());
    smashColumn.setCellValueFactory(cellData -> cellData.getValue().getSmash());
    startupColumn.setCellValueFactory(cellData -> cellData.getValue().getStartup());
    activeColumn.setCellValueFactory(cellData -> cellData.getValue().getActive());
    recoveryColumn.setCellValueFactory(cellData -> cellData.getValue().getRecovery());
    onBlockColumn.setCellValueFactory(cellData -> cellData.getValue().getOnBlock());
    invuColumn.setCellValueFactory(cellData -> cellData.getValue().getInvu());
    moveTable.setItems(moveInfoViewModel.getMoveList());
    reset();
  }

  @Override public void reset() {
    moveInfoViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML public void onBackBtnPress(){
    viewHandler.openView("CharacterInfo");
  }
}
