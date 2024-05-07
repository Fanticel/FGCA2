package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.EventDescriptionViewModel;
import viewModel.ViewModelFactory;

public class EventDescriptionViewController implements ViewController{
  @FXML private Label lblEventTitle;
  @FXML private TextField txtGame;
  @FXML private TextField txtTimeUntilStart;
  @FXML private TextField txtNumberOfPlayers;
  @FXML private TextField txtSkillLevel;
  private ViewHandler viewHandler;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    eventDescriptionViewModel = viewModelFactory.getEventDetailsViewModel();
    this.root = root;
    lblEventTitle.textProperty().bind(eventDescriptionViewModel.getTittleProperty());
    txtGame.textProperty().bind(eventDescriptionViewModel.getGameProperty());
    txtTimeUntilStart.textProperty().bind(eventDescriptionViewModel.getDateProperty());
    txtNumberOfPlayers.textProperty().bind(eventDescriptionViewModel.getParticipantsProperty());
    txtSkillLevel.textProperty().bind(eventDescriptionViewModel.getBRPRangeProperty());
  }

  @Override public void reset() {
    eventDescriptionViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML void pressBackButton(){
    viewHandler.openView("EventList");
  }
}
