package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import viewModel.CreateEventViewModel;
import viewModel.ViewModelFactory;

public class CreateEventController implements ViewController {

  @FXML private ChoiceBox choiceBox;
  private ViewHandler viewHandler;
  private CreateEventViewModel createEventViewModel;
  private Region root;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    createEventViewModel = viewModelFactory.getCreateEventViewModel();
    this.root = root;
    choiceBox.getItems().add("Tekken");
    choiceBox.getItems().add("Mortal Kombat");
    choiceBox.getItems().add("2XKO");
  }

  @Override public void reset()
  {


  }

  @Override public Region getRoot()
  {
    return root;
  }
}


