package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.DescriptionViewGeneralViewModel;
import viewModel.ViewModelFactory;

public class DescriptionViewGeneralController implements ViewController{
  @FXML private TextField txtGame;
  @FXML private TextField txtTimeUntilStart;
  @FXML private TextField txtNumberOfPlayers;
  @FXML private TextField txtSkillLevel;
  private ViewHandler viewHandler;
  private DescriptionViewGeneralViewModel descriptionViewGeneralViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    System.out.println("descview initialiased");
    this.viewHandler = viewHandler;
    descriptionViewGeneralViewModel = viewModelFactory.getDescriptionViewGeneralViewModel();
    this.root = root;
    txtGame.textProperty().bind(descriptionViewGeneralViewModel.getGameProperty());
    txtTimeUntilStart.textProperty().bind(descriptionViewGeneralViewModel.getDateProperty());
    txtNumberOfPlayers.textProperty().bind(descriptionViewGeneralViewModel.getParticipantsProperty());
    txtSkillLevel.textProperty().bind(descriptionViewGeneralViewModel.getBRPRangeProperty());
  }

  @Override public void reset() {
    descriptionViewGeneralViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
}
