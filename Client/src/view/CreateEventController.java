package view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import utility.IntStringConverter;
import viewModel.CreateEventViewModel;
import viewModel.ViewModelFactory;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CreateEventController implements ViewController {

  @FXML
  private ChoiceBox<String> gameChoiceBox;

  @FXML
  private TextField maxBRPField;

  @FXML
  private ChoiceBox<String> maxPlrChoiceBox;

  @FXML
  private TextField minBRPField;

  @FXML
  private DatePicker startDatePicker;

  @FXML
  private ChoiceBox<String> startHourChoiceBox;

  @FXML
  private TextField titleField;

  @FXML
  private Label errorLabel;
  private ViewHandler viewHandler;
  private CreateEventViewModel createEventViewModel;
  private Region root;

  UnaryOperator<TextFormatter.Change> filter = change -> {
    String text = change.getText();

    if (text.matches("\\d?")) { // this is the important line
      return change;
    }

    return null;
  };

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    createEventViewModel = viewModelFactory.getCreateEventViewModel();
    this.root = root;

    titleField.textProperty().bindBidirectional(createEventViewModel.getTitleProperty());
    gameChoiceBox.getItems().add("Tekken");
    gameChoiceBox.getItems().add("Mortal Kombat");
    gameChoiceBox.getItems().add("2XKO");
    gameChoiceBox.valueProperty().bindBidirectional(createEventViewModel.getGameProperty());
    startDatePicker.valueProperty().bindBidirectional(createEventViewModel.getDateProperty());
    for (int i = 0; i < 24; i++){
      startHourChoiceBox.getItems().add(String.valueOf(i));
    }
    startHourChoiceBox.valueProperty().bindBidirectional(createEventViewModel.getStartingHourProperty());
    maxPlrChoiceBox.getItems().add("8");
    maxPlrChoiceBox.getItems().add("16");
    maxPlrChoiceBox.getItems().add("32");
    maxPlrChoiceBox.getItems().add("64");
    maxPlrChoiceBox.valueProperty().bindBidirectional(
        createEventViewModel.getMaxPlayersProperty());

    minBRPField.setTextFormatter(new TextFormatter<String>(filter));
    Bindings.bindBidirectional(minBRPField.textProperty(),createEventViewModel.getMinBRPProperty(),
        new IntStringConverter());
    maxBRPField.setTextFormatter(new TextFormatter<String>(filter));
    Bindings.bindBidirectional(maxBRPField.textProperty(),createEventViewModel.getMaxBRPProperty(),
        new IntStringConverter());
    errorLabel.textProperty().bind(createEventViewModel.getErrorProperty());
  }

  @Override public void reset()
  {
    createEventViewModel.reset();
  }

  @Override public Region getRoot()
  {
    return root;
  }
  @FXML void confirm() {
    if (createEventViewModel.confirm()){
      viewHandler.openView("EventList");
    }
  }
  @FXML void back() {
    viewHandler.openView("EventList");
  }
  @FXML void clear() {
   reset();
  }


}


