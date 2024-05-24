package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import viewModel.ChatViewModel;
import viewModel.RegisterViewModel;
import viewModel.ViewModelFactory;

public class ChatViewController implements ViewController{
  @FXML private Label chatNameLabel;
  @FXML private Label previousLogLabel;
  @FXML private TextField inputTextField;
  private ViewHandler viewHandler;
  private ChatViewModel chatViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    chatViewModel = viewModelFactory.getChatViewModel();
    this.root = root;
    chatNameLabel.textProperty().bind(chatViewModel.getChatName());
    previousLogLabel.textProperty().bind(chatViewModel.getPreviousLog());
    inputTextField.textProperty().bindBidirectional(chatViewModel.getInput());
  }

  @Override public void reset() {
    chatViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML private void onEnterPressed(){
    chatViewModel.enterPress();
  }
}
